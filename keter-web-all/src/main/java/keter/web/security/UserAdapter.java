package keter.web.security;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import keter.domain.Authority;
import keter.domain.User;
import keter.persistence.org.UserDao;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * Adapts User model from pl.edu.agh.mea.domain package to make it usable in
 * Spring Security authentication mechanism.
 * 将应用的User转换为Spring的User
 * @author Dawid Fatyga
 * 
 */
public class UserAdapter implements UserDetails {
	private static final long serialVersionUID = 1L;

	transient private User user;
	transient private UserDao userDao;

	public UserAdapter() {
	}

	public UserAdapter(User user, UserDao userDao) {
		this.user = user;
		this.userDao = userDao;
	}

	/**
	 * Converts collection of Authorities into collection of GrantedAuthorities
	 * usable in authentication mechanism.
	 * 
	 * @param authorities
	 *            collection of authorities
	 * @return collection of granted authorities
	 */
	private Collection<GrantedAuthority> toGrantedAuthorities(
			Collection<Authority> authorities) {

		Set<GrantedAuthority> grantedAuthorities = new TreeSet<GrantedAuthority>();
		for (Authority authority : authorities) {
			grantedAuthorities.add(new AuthorityAdapter(authority));
		}
		return grantedAuthorities;
	}

	public void setUser(User user){
		this.user = user;
	}

	/**
	 * Returns user delegate.
	 * 
	 * @return cached user instance
	 */
	public User getUser(){
		return getUser(false);
	}

	/**
	 * @param force
	 *            allows to fetch user from database (not from cache)
	 * @return user instance
	 */
	public User getUser(boolean force) {
		if (force) {
			if (user == null || userDao == null) {
				//FIXME:或许需要自定义异常
				throw new RuntimeException(new NullPointerException(
						"user or userDao is null"));
			}
			user = userDao.findById(user.getId());
		}
		return user;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return toGrantedAuthorities(user.getAuthorities());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getAccount();//系统采用“账号”作为登录验证
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * An alias for hasRole(), but accepts Authority enum.
	 */
	public boolean is(Authority role) {
		return is(role.toString());
	}

	/**
	 * An alias for hasRole()
	 */
	public boolean is(String name) {
		return hasRole(name);
	}

	/**
	 * Searches for grantedAuthority with given name, and returns true if
	 * exists.
	 * 
	 * @param name
	 *            name of a granted authority
	 * @return true if authority exists
	 */
	public boolean hasRole(String name) {
		return getAuthorities().contains(new AuthorityAdapter(name));
	}
}