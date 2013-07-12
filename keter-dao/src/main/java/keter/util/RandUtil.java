package keter.util;

import java.util.Random;

/**
 * Random utility methods.
 * 
 * @author Jerzy
 */
public class RandUtil {

	static private Random random = null;

	static public synchronized Random getInstance() {
		if (random == null) {
			random = new Random();
		}
		return random;
	}

	public static String randName(String name) {
		return name + "-" + getInstance().nextInt();
	}
}
