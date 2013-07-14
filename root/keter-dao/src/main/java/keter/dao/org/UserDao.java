package keter.dao.org;

import keter.dao.base.KeterDao;
import keter.domain.User;

public interface UserDao extends KeterDao<User> {
	
	User findByAccount(String account);
}
