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
	private static String sysConfigPath;

	/**
	 *  合成配置文件所在的路径
	 */
	private static void initSystemPath() {
		classPath = decodePath(ClassLoader.getSystemResource("").toString());
		interceptorString = "/classes";
		sysConfigPath = classPath.substring(0, classPath.lastIndexOf(interceptorString)) + "/conf/";
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

	public static String[] getConfigPath() {
		initSystemPath();
		return new String[]{
				sysConfigPath + "dao-*.xml",
				sysConfigPath + "datasource-*.xml",
				sysConfigPath + "transaction-*.xml",
		};
	}

	/**
	 * <p>传入模块路径，结合系统路径生成配置路径</p>
	 *
	 * @param moudlePath
	 * @return
	 * @author: 顾力行 - gulixing@msn.com
	 * @date: Created on Feb 5, 2009 9:17:39 AM
	 */
	public static String[] getConfigPath(String[] moudlePath) {
		Validate.notNull(moudlePath, "模块目录不能为空！");
		Validate.notEmpty(moudlePath, "模块目录不能为空！");

		initSystemPath();
		//将自定义的配置文件路径跟系统路径结合
		String absolutePath[] = new String[moudlePath.length+getConfigPath().length];
		int index = 0;
		for (String path : moudlePath) {
			//如果路径没有正确结尾，则补充结尾符号"/"
			if(path.charAt(path.length()-1)!='/'){
				path +="/";
			}
			// 合成配置文件实际路径
			String moudleConfigPath = sysConfigPath + path;
			absolutePath[index] = moudleConfigPath + "*.xml";
			index++;
		}

		//将自定义的配置文件路径跟系统配置文件路径结合
		System.arraycopy(getConfigPath(), 0, absolutePath, moudlePath.length, getConfigPath().length);
//		System.out.println(absolutePath);
		return absolutePath;
	}

	public static String[] getConfigPath(String moudlePath) {
		return getConfigPath(new String[]{moudlePath});
	}

}
