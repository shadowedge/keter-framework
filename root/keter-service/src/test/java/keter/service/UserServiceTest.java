package keter.service;
/**                                
 * Copyright ® 2013 东软集团股份有限公司
 * 版权所有。     
 */

import junit.framework.Assert;
import keter.KeterAbstractServiceTest;
import keter.domain.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * Class : test.persistence.HahaDaoTest
 * <p>
 * Descdription: 类功能描述
 * 
 * @author 顾力行-gulixing@msn.com
 * @version 1.0.0
 */
public class UserServiceTest extends KeterAbstractServiceTest {

	@Autowired
	private UserService service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test(){
		User user = new User();
		user.setAccount("a");
		user.setUsername("我");
		user.setPassword("1");
		service.add(user);
		Assert.assertEquals(1,service.all().size());
	}
	
}
