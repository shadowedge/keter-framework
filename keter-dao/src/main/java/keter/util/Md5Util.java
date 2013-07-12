package keter.util;

import java.security.NoSuchAlgorithmException;

public class Md5Util {

	public static String getMD5(byte[] src) {
		StringBuffer sb = new StringBuffer();
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(src);
			for (byte b : md.digest())
				sb.append(Integer.toString(b >>> 4 & 0xF, 16)).append(
						Integer.toString(b & 0xF, 16));
		} catch (NoSuchAlgorithmException e) {
		}
		return sb.toString();
	}
	
	public static <T> T[] wrap(T... objects) {
		return objects;
	}
	
	public static void main(String[] args){
		System.out.println(getMD5("a".getBytes()));
	}

}
