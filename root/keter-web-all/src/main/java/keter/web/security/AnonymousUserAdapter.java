package keter.web.security;

import java.util.Collection;
import java.util.TreeSet;

import keter.domain.User;

import org.springframework.security.core.GrantedAuthority;

/**
 * Defines default empty user - anonymous user - returned when
 * no one is logged in the system.
 * 
 * @author Dawid Fatyga
 *
 */
public class AnonymousUserAdapter extends UserAdapter {
	private static final long serialVersionUID = 1153278300924661257L;

	/**
	 * Simply throws UnsupportedOperationException.
	 * 
	 * @throws UnsupportedOperationException
	 *             always
	 */
	@Override
	public User getUser(boolean force) {
		throw new UnsupportedOperationException(
				"anonymous user does not allow to getUser from database");
	}

	@Override
	public String getUsername() {
		return "Guest";
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return new TreeSet<GrantedAuthority>();
	}

}
