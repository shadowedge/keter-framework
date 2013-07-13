package keter.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for reading various resources.
 * 
 * @author Maciej Hamiga
 *
 */
public class ResourceReader {
	
	public static Properties readPropertiesFromClasspath(String resourceName) throws IOException {
		InputStream inputStream = ResourceReader.class.getClassLoader().getResourceAsStream(resourceName);
		if (inputStream == null) {
			throw new IOException("Resource " + resourceName + " not found.");
		}
		Properties properties = new Properties();
		properties.load(inputStream);
		return properties;
	}

}
