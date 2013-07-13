package keter.persistence.org;

import keter.domain.User;
import keter.persistence.base.KeterDao;

public interface UserDao extends KeterDao<User> {
	
	User findByAccount(String account);
}
