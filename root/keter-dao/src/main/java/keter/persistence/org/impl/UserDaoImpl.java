package keter.persistence.org.impl;

import keter.domain.User;
import keter.persistence.base.KeterAbstractDao;
import keter.persistence.org.UserDao;

import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends KeterAbstractDao<User> implements UserDao {

	@Override
	public User findByAccount(String account){
		return getSingleResult(
				"from User u where u.account = :account",
				wrap("account"), wrap(account));
	}
}
