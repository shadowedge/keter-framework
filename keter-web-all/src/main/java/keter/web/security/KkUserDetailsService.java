package keter.web.security;


import keter.persistence.org.UserDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Provides UserDetails from database.
 * 
 * @author Dawid Fatyga
 */
@Service("kkUserService")
@Transactional
public class KkUserDetailsService implements UserDetailsService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(KkUserDetailsService.class);

	@Autowired
	private UserDao userDao;
	
	/**
	 * <p>Method ：loadUserByUsername
	 * <p>Description : 从数据库中读取用户，由SS判断用户密码是否正确
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 * @author  gulixing@msn.com
	 * @version 1.0.0
	 */
	public UserDetails loadUserByUsername(String account)
			throws UsernameNotFoundException, DataAccessException {
		try {
			UserAdapter user = new UserAdapter(
					userDao.findByAccount(account), userDao);
			if (user.getAuthorities().isEmpty()) {
				throw new AuthenticationCredentialsNotFoundException(String.format(
						"user '%s' has no authorities", account));
			}
			return user;
		} catch (Exception e) {
			//用户不存在
			throw new UsernameNotFoundException("username or password error", e);
		} 
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
