package keter.dao;

import java.util.Iterator;
import java.util.List;

import keter.HibernateUtil;
import keter.domain.Address;
import keter.domain.User;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserHTest {

	public static void main(String[] args) {

		// ########## unit 1 ：插入 ##############
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		User u = new User();
		u.setUsername("顾");
		u.setAccount("gu");
		u.setPassword("1");
		Address ads1 = new Address("根来彼方");
		Address ads2 = new Address("万事屋");
		u.addAddress(ads1);
		u.addAddress(ads2);
		System.out.println(" >>> Unit1：执行持久化  <<<");
		session.save(u);
		System.out.println("\n");
		tx.commit();
		session.close();
		
		// ################### unit 2：查询 #############
		Session secondSession = HibernateUtil.getSessionFactory().openSession();
//		Transaction secondTransaction = secondSession.beginTransaction();

		System.out.println(" >>> Unit2：全表查询  <<<");
		List users = secondSession.createQuery("from User u").list();
		for (Iterator iter = users.iterator(); iter.hasNext();) {
			User user = (User) iter.next();
			System.out.println("user total count is:"+user.getTotal());
			System.out.println("user total count is:"+user.getAddress().get(0).getStreet());	
			System.out.println("user total count is:"+user.getAddress().get(1).getStreet());	
			System.out.println("user last modified at:"+user.getLastModified());
		}

		System.out.println("\n");
//		secondTransaction.commit();
		secondSession.close();
	}
}
