package keter.util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Allows to append URLs to system class loaded. Useful when using executable
 * jar files as entry points, which ignores the -classpath attribute.
 * 
 * @author Dawid Fatyga
 * 
 */
public class ClassLoaderAppender {
	static private Logger logger = LoggerFactory
			.getLogger(ClassLoaderAppender.class);

	public static void addFile(File file) throws Exception {
		addUrl(file.toURI().toURL());
	}

	/**
	 * Adds an URL to class loader.
	 * 
	 * @param url
	 *            url to add
	 * @throws Exception
	 *             unknown reflection exception
	 */
	public static void addUrl(URL url) throws Exception {
		try {
			URLClassLoader classLoader = (URLClassLoader) ClassLoader
					.getSystemClassLoader();
			Class<URLClassLoader> classLoaderClass = URLClassLoader.class;

			Method addUrlMethod = classLoaderClass.getDeclaredMethod("addURL",
					new Class[] { URL.class });
			addUrlMethod.setAccessible(true);
			addUrlMethod.invoke(classLoader, new Object[] { url });
		} catch(ClassCastException cce){
			logger.warn("Your class loader is not URLClassLoader. You cannot modify the classpath");
		}
	}

	/**
	 * Scans CLASSPATH variable and adds every entry to classpath.
	 * 
	 * @throws Exception
	 */
	public static void scanClasspathVariable() throws Exception {
		for (String path : System.getenv("CLASSPATH").split(File.pathSeparator)) {
			addFile(new File(path));
		}
	}
}
