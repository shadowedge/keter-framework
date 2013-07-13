/**
 * 文件名：DBTestAssitant.java
 *
 * 创建人：顾力行 - gulixing@msn.com
 *
 * 创建时间：Apr 15, 2009 6:48:04 PM
 *
 * 版权所有：东软集团股份有限公司
 */
package keter.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>数据库直接操作工具</p>
 *
 * @author 顾力行 - gulixing@msn.com
 * @version 1.0 Created on Apr 15, 2009 6:48:04 PM
 */
public class DBUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger log = LoggerFactory.getLogger(DBUtil.class);

//	protected static Logger log = Logger.getRootLogger();
	
	
	private static Connection conn;
	private static Statement stmt;
	private static ResultSet rstr;
	
	private static Properties connProperties = IOUtil.getConfigProps("");
//	private static NameValuePair userInfo;
	private static String strConnectionURL = null;
	private static String dbype;
	
	/**
	 * 配置文件初始化：只做一次！
	 */
	static{
		 log.info(" init datasource context...");
//		 userInfo = new NameValuePair(
//				 connProperties.getProperty("UserName"),
//				 connProperties.getProperty("Password")
//				);
		 strConnectionURL = connProperties.getProperty("DataSourceURL");
		 dbype = connProperties.getProperty("TargetDatabase");
	};

	/**
	 * <p>执行查询</p>
	 *
	 * @param strConnectionURL
	 * @param userNamePwd
	 * @param sql
	 * @param type
	 * @return
	 * @author: 顾力行 - gulixing@msn.com
	 * @date: Created on Apr 27, 2009 10:44:07 AM
	 */
	public static ResultSet executeQuery(String sql){
		prepareDBDriver(dbype);
		prepareQuery(sql);
		return rstr;
	}

	public static void executeUpdate(String sql){
		prepareDBDriver(dbype);
		prepareUpdate(sql);
	}
	
	/**
	 * <p>Method ：prepare
	 * <p>Description : 构建数据库驱动
	 * 
	 * @author  顾力行-gulixing@msn.com
	 *<p>
	 *--------------------------------------------------------------<br>
	 * 修改履历：<br>
	 *        <li> 2010-8-30，gulixing@msn.com，创建方法；<br>
	 *--------------------------------------------------------------<br>
	 *</p>
	 */
	private static void prepareDBDriver(String dbType) {
		try {
			if(dbType.toLowerCase().equals("oracle")){
				Class.forName("oracle.jdbc.driver.OracleDriver");
			}
			else if(dbType.toLowerCase().equals("mysql")){
				Class.forName("org.gjt.mm.mysql.Driver");
			}
			else{
				log.error("配置错误：" + dbType + ", 不支持的数据库类型！");
			}
		} catch (ClassNotFoundException e) {
			log.error("加载数据库驱动异常！数据库类型：" + dbType + " ,异常信息： " + e.getMessage());
		}
	}

	/**
	 * <p>结果集准备</p>
	 *
	 * @param strConnectionURL
	 * @param userNamePwd
	 * @param sql
	 * @throws SQLException
	 * @author: 顾力行 - gulixing@msn.com
	 * @date: Created on Apr 20, 2009 4:55:34 PM
	 */
	private static void prepareQuery(String sql){
		try {
			conn = DriverManager.getConnection(strConnectionURL, connProperties.getProperty("UserName"), connProperties.getProperty("Password"));
			stmt = conn.createStatement();
			rstr = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			close();
		}
	}

	/**
	 * <p>结果集准备</p>
	 *
	 * @param strConnectionURL
	 * @param userNamePwd
	 * @param sql
	 * @throws SQLException
	 * @author: 顾力行 - gulixing@msn.com
	 * @date: Created on Apr 20, 2009 4:55:34 PM
	 */
	private static void prepareUpdate(String sql){
		try {
			conn = DriverManager.getConnection(strConnectionURL, connProperties.getProperty("UserName"), connProperties.getProperty("Password"));
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			close();
		}
	}

	/**
	 * <p>关闭连接 需要在结果集使用完成之后操作</p>
	 *
	 * @author: 顾力行 - gulixing@msn.com
	 * @date: Created on Apr 20, 2009 4:57:13 PM
	 */
	public static void close(){
		if(conn!=null && stmt!=null){
		try {
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	}

		public static void main(String args[]) throws SQLException{
			String sql = "select * from user;";
			ResultSet rstr = DBUtil.executeQuery(sql);
			while (rstr.next()) {
				String USR_HOST = rstr.getString(1);
				String USR_NAME = rstr.getString(2);
				System.out.println("round1: " + USR_NAME + " " +USR_HOST);
			}
			DBUtil.close();

			 sql = "select * from user;";
			 rstr = DBUtil.executeQuery(sql);
			 while (rstr.next()) {
				String USR_HOST = rstr.getString(1);
				String USR_NAME = rstr.getString(2);
				System.out.println("round2: " + USR_NAME + " " +USR_HOST);
			}
			DBUtil.close();

		}
}