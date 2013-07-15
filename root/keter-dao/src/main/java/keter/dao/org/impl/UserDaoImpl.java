package keter.dao.org.impl;

import keter.dao.base.KeterAbstractDao;
import keter.dao.org.UserDao;
import keter.domain.User;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userDao")
public class UserDaoImpl extends KeterAbstractDao<User> implements UserDao {
	
	@Override
	public User findByAccount(String account){
		return getSingleResult(
				"from User u where u.account = :account",
				wrap("account"), wrap(account));
	}
}
