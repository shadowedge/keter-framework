package keter.web.util;

import keter.dao.org.UserDao;
import keter.domain.Authority;
import keter.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class programatically populates the database at application startup. You
 * may probably wish to disable it in a production environment.
 * 
 * @author Daniel
 * 
 */
public class DbInit implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(DbInit.class);

	@Autowired
	private UserDao userDao;
	private boolean alreadyInitialized = false;

	@Transactional
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadyInitialized) {
			return;
		}
		if (userDao.findAll().size() > 0) {
			logger.info("系统已被初始化或用户数据已存在。");
			//测试分页 结论：hibernate可以为mysql添加“limit”语句
			userDao.findByAccount("haha");
			return;
		}
		User admin = new User();
		admin.setAccount("admin");
		admin.setUsername("管理员");
		admin.setPassword("1");
		admin.addAuthority(Authority.ADMIN);
		userDao.saveOrUpdate(admin);
		
		User user = new User();
		user.setAccount("user");
		user.setUsername("用户");
		user.setPassword("1");
		user.addAuthority(Authority.USER);
		userDao.saveOrUpdate(user);
		alreadyInitialized = true;
		logger.info("系统数据初始化完毕！");
	}
}