package keter.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.commons.lang.Validate;

/**
 * <p>生成系统配置文件路径</p>
 *
 * @author 顾力行 - gulixing@msn.com
 * @version 1.0 Created on Feb 5, 2009 9:16:56 AM
 */
public class PathUtil {

	// 系统路径和平台的 Spring 配置路径(通常为固定值)
	// 位置由".classpath"配置文件中的[<classpathentry kind="output" path=""/>]属性决定
	private static String classPath;
	// 拦截字符串: 用于对系统路径进行有效的截取。
	private static String interceptorString;
	// 平台系统路径： 即所需的 Srping 公共配置文件所在路径
	private static String targetPath;

	/**
	 *  合成配置文件所在的路径
	 */
	private static void initSystemPath() {
		classPath = decodePath(ClassLoader.getSystemResource("").toString());
	}

	/**
	 * 如果磁盘路径有空格,"-"等特殊字符，则可能需要进行URL转码
	 * 否则无法保证得到正确的绝对路径
	 *
	 * @param path
	 * @return
	 */
	public static String decodePath(String path) {
		String pathDecodeded = "";
		try {
			pathDecodeded = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return pathDecodeded;
	}

	/**
	 * <p>Method ：getProjectRoot
	 * <p>Description : 得到maven工程的根目录
	 * @return 
	 * @author  gulixing@msn.com
	 * @version 1.0.0
	 */
	public static String getProjectRoot() {
		initSystemPath();
		interceptorString = "/target";//file:/E:/git/keter/keter-project/web/target/test-classes/
		targetPath = classPath.substring(0, classPath.lastIndexOf(interceptorString));
		return targetPath;
	}
	
	public static String[] getConfigPath() {
		initSystemPath();
		return new String[]{
				targetPath + "dao-*.xml",
				targetPath + "datasource-*.xml",
				targetPath + "transaction-*.xml",
		};
	}

}
