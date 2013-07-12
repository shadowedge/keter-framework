package keter.persistence.org;

import keter.domain.User;
import keter.persistence.base.KkDao;

public interface UserDao extends KkDao<User> {
	
	User findByAccount(String account);
}
