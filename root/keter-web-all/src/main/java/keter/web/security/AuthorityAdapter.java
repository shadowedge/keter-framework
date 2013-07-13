package keter.web.security;

import keter.domain.Authority;

import org.springframework.security.core.GrantedAuthority;

/**
 * Adapts Authority model from pl.edu.agh.mea.domain package to make it usable
 * in Spring Security authentication mechanism.
 * 
 * @author Dawid Fatyga
 * 
 */
public class AuthorityAdapter implements GrantedAuthority, Comparable<AuthorityAdapter> {

	private static final long serialVersionUID = 1L;

	private final String role;

	public boolean equals(Object obj) {
		if (obj instanceof String) {
			return obj.equals(this.role);
		}
		if (obj instanceof GrantedAuthority) {
			GrantedAuthority attr = (GrantedAuthority) obj;
			return this.role.equals(attr.getAuthority());
		}
		return false;
	}

	public AuthorityAdapter(Authority authority) {
		this.role = authority.name();
	}
	
	public AuthorityAdapter(String role) {
		this.role = role;
	}

	@Override
	public int compareTo(AuthorityAdapter other) {
		return getAuthority().compareToIgnoreCase(other.getAuthority());
	}

	/**
	 * <p>
	 * Method ：getAuthority
	 * <p>
	 * Description : 功能描述
	 * 
	 * @return
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Override
	public String getAuthority() {
		return this.role;
	}

	public int hashCode() {
		return this.role.hashCode();
	}

	public String toString() {
		return this.role;
	}
}
