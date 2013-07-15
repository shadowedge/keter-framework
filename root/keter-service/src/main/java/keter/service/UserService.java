package keter.service;

import java.util.List;

import keter.dao.org.UserDao;
import keter.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	public void add(User user){
		logger.info("add user");
		userDao.saveOrUpdate(user);
	}
	
	public List<User> all(){
		logger.info("find all user");
		return userDao.findAll();
	}

}
