package dz.nft.nipa.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserDto implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	private int userNum;
	private String userId;
	private String userPw;
	private String userRole;
	private int userLoginFailCnt;
	private String userLock;
	
	
	/* UserDetails 기본 상속 변수 */
	private Collection<? extends GrantedAuthority> authorities;
	private boolean isEnabled = true;
	private String username;
	private boolean isCredentialsNonExpired = true;
	private boolean isAccountNonExpired = true;
	private boolean isAccountNonLocked = true;
	
	@Override
	public String getPassword() {
		return null;
	}
	
}
