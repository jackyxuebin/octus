package security.model;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticatedUser implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String username;
	private String password;
	private String token;
	private List<GrantedAuthority> authorityList;
	
	public AuthenticatedUser(long id, String username, String token, List<GrantedAuthority> authorityList){
		this.id=id;
		this.username=username;
		this.token=token;
		this.authorityList=authorityList;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorityList;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	public long getId() {
		return id;
	}
	public String getToken() {
		return token;
	}
	public List<GrantedAuthority> getAuthorityList() {
		return authorityList;
	}
	 
	
}
