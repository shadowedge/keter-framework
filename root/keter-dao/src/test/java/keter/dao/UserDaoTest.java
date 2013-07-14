package keter.dao;

import junit.framework.Assert;
import keter.KeterAbstractPersistenceTest;
import keter.dao.org.UserDao;
import keter.domain.Authority;
import keter.domain.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoTest extends KeterAbstractPersistenceTest {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);

	@Autowired
	private UserDao dao;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		logger.info("====开始测试====");
		User u = new User();
		u.setAccount("gu");
		u.setUsername("顾");
		u.setPassword("pwd");
		u.addAuthority(Authority.ADMIN);
		//持久化：实体
		u = dao.persistEntity(u);
		
		User u1 = new User();
		u1.setAccount("gu1");
		u1.setUsername("顾1");
		u1.setPassword("pwd");
		u1.addAuthority(Authority.USER);
		//持久化
		dao.persist(u1);
		
		// 查询：全部
		Assert.assertEquals(2, dao.findAll().size());
		// 查询：特定
		Assert.assertEquals("顾", dao.findById(u.getId()).getUsername());
		// 查询：特定
		Assert.assertTrue(dao.findById(u.getId()).getAuthorities().contains(Authority.ADMIN));
		// 查询：特定
		Assert.assertEquals("顾", dao.findByAccount("gu").getUsername());
		 
		

		//修改
		u.setUsername("杨");
		dao.merge(u);
		Assert.assertEquals("杨", dao.findById(u.getId()).getUsername());

		// 删除
		dao.delete(u.getId(),u1.getId());
		Assert.assertEquals(0, dao.findAll().size());
	}
	
//	main
}
