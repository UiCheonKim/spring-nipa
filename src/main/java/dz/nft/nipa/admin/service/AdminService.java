package dz.nft.nipa.admin.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import dz.nft.nipa.dto.UserDto;

public interface AdminService extends UserDetailsService{
	
	// security 사용자 인증
	UserDetails loadUserByUsername(String userId);
	// 아이디 중복 체크
	public UserDto getUserDataById(String userId);
	// token 발급 Api에 id 전달 후 토큰 값 리턴
	String getUserTokenById(String userId);
	
	public void insertUserData(UserDto dto);
	public UserDto getUserDataByIdAndPw(String userId, String userPw);
	
	public void updateLogFailCntByUserNum(int userNum);
	public void updateUserLockByUserNum(int userNum);
	public void resetLogFailCntByUserNum(int userNum);
	
}
