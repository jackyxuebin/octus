package security.model;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken{

	public JwtAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
		// TODO Auto-generated constructor stub
	}
	
	public JwtAuthenticationToken(String token){
		super(null,null);
		this.token = token;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
