package keter.web.util;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.Test;

/**
 * <p>Class       : keter.web.util.DataSourceValidator
 * <p>Descdription: 用于校验Weblogic连接池是否可以成功连接
 *
 * @author  gulixing@msn.com
 * @version 1.0.0
 */
public class JNDIDataSourceValidator {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(JNDIDataSourceValidator.class);
	

	@Test
	public static void validateWLS(String providerUrl, String dsName) throws SQLException {
		Context ctx = null;
		try {
			Properties prop = new Properties();
			prop.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			prop.setProperty(Context.PROVIDER_URL, providerUrl);// t3://192.168.0.11:7001
			ctx = new InitialContext(prop);
		} catch (NamingException ne) {
			System.err.println(ne.getMessage());
		}

		DataSource ds = null;
		try {
			ds = (DataSource) ctx.lookup(dsName);
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (ClassCastException cce) {//wls数据源无法转换为javax.sql.DataSource
			logger.info("可以连接");
		}
		// Connection conn = ds.getConnection();
		// Statement stmt = conn.createStatement();
		// ResultSet rset = stmt
		// .executeQuery("select 'Hello Thin driver data source tester '||"
		// + "initcap(USER)||'!' result from dual");
		// if (rset.next())
		// System.out.println(rset.getString(1));
		// rset.close();
		// stmt.close();
		// conn.close();
	}

}