package dz.nft.nipa.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import dz.nft.nipa.admin.service.AdminService;
import dz.nft.nipa.dto.UserDto;
import dz.nft.nipa.security.handlers.AuthSuccessHandler;
import dz.nft.nipa.utils.HashStringUtil;

@Component
public class AuthProvider implements AuthenticationProvider{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);
	
	@Autowired
	AdminService adminService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userId = authentication.getName();
		String beforeHashPw = authentication.getCredentials().toString();
		String userPw = new HashStringUtil().getHashString(beforeHashPw);
		
		// logger.info("authentication.getCredentials() : "+authentication.getCredentials());
		// logger.info("authentication.getCredentials()/afterHash : "+userPw);
		
		return authenticate(userId, userPw);
	}
	
	private Authentication authenticate(String userId, String userPw) throws AuthenticationException {
		
		List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
		
		if (
				userId.contains("<") 
				|| userId.contains(">")
				|| userId.contains("@")
				|| userId.contains("#")
				|| userId.contains("$")
				|| userId.contains("&")
				|| userId.contains("%")
				|| userId.contains("|")
				|| userId.contains("'")
				|| userId.contains(":")
				|| userId.contains("/")
				|| userId.toLowerCase().contains("xml")
				|| userId.toLowerCase().contains("script")
				|| userId.toLowerCase().contains("select")
				|| userId.toLowerCase().contains("delete")
				|| userId.toLowerCase().contains("from")
				|| userId.toLowerCase().contains("insert")
				|| userId.toLowerCase().contains("update")
				|| userId.toLowerCase().contains("drop")
			) {
			logger.info("잘못된 입력값 입력");
			throw new BadCredentialsException("ID 혹은 비밀번호를 확인해주세요");
			
		} else if(
				
				userPw.contains("<") 
				|| userPw.contains(">")
				|| userPw.contains("@")
				|| userPw.contains("#")
				|| userPw.contains("$")
				|| userPw.contains("&")
				|| userPw.contains("%")
				|| userPw.contains("|")
				|| userPw.contains("'")
				|| userPw.contains(":")
				|| userPw.contains("/")
				|| userPw.toLowerCase().contains("xml")
				|| userPw.toLowerCase().contains("script")
				|| userPw.toLowerCase().contains("select")
				|| userPw.toLowerCase().contains("delete")
				|| userPw.toLowerCase().contains("from")
				|| userPw.toLowerCase().contains("insert")
				|| userPw.toLowerCase().contains("update")
				|| userPw.toLowerCase().contains("drop")
			) {
			logger.info("잘못된 입력값 입력");
			throw new BadCredentialsException("ID 혹은 비밀번호를 확인해주세요");
		}
		
		
		UserDto dto = adminService.getUserDataById(userId);
		String userToken = null;
		
		
		if (dto == null) {
			// 아이디 없을 때
			logger.info("사용자 정보가 없습니다.");
			throw new UsernameNotFoundException("ID 혹은 비밀번호를 확인해주세요");
			
		} else if (dto.getUserLoginFailCnt() >= 5 || dto.getUserLock().equals("Y")) {
			// 로그인 실패 횟수 초과 or 계정 잠금
			logger.info("계정 잠김");
			throw new BadCredentialsException("로그인 허용 횟수를 초과하여 계정이 잠겼습니다.\\n관리자에게 문의하십시오.");
			
		} else if (dto != null && !dto.getUserPw().equals(userPw)) {
			// 비밀번호 잘못 입력
			
			if (dto.getUserLoginFailCnt() <= 5) {
				// 로그인 실패 횟수 증가 쿼리 호출
				adminService.updateLogFailCntByUserNum(dto.getUserNum());
				if (dto.getUserLoginFailCnt() == 4) {
					adminService.updateUserLockByUserNum(dto.getUserNum());
				}
			}
			
			logger.info("비밀번호가 틀렸습니다.");
			throw new BadCredentialsException("ID 혹은 비밀번호를 확인해주세요");
		}
		
		
		if (dto != null && dto.getUserPw().equals(userPw)) {
			// 로그인 성공 시 토큰 발행
			userToken = adminService.getUserTokenById(userId);
			// 로그인 성공 시 실패 횟수 초기화
			adminService.resetLogFailCntByUserNum(dto.getUserNum());
		}
		
		
		if (userToken == null) {
			// 토큰 발급 오류 - 블록체인
			logger.info("토큰을 발행하는데 실패하였습니다.");
			throw new BadCredentialsException("ID 혹은 비밀번호를 확인해주세요");
		}
		
		
		grantedAuthorityList.add(new SimpleGrantedAuthority(dto.getUserRole()));
		return new MyAuthentication(userId, userPw, grantedAuthorityList, dto, userToken);
		
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}
