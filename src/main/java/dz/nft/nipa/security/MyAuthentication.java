package dz.nft.nipa.security;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import dz.nft.nipa.dto.UserDto;
import lombok.Data;

@Data
public class MyAuthentication extends UsernamePasswordAuthenticationToken {
	
	private static final long serialVersionUID = 1L;
	private UserDto userDto;
	private String UserToken;
	
	public MyAuthentication(String userId, String userPw, List<GrantedAuthority> grantedAuthorityList, UserDto dto, String userToken) {
		super(userId, userPw, grantedAuthorityList);
		this.setUserDto(dto);
		this.setUserToken(userToken);;
	}
	
}
