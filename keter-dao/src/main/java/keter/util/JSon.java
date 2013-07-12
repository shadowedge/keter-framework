package keter.util;

import java.lang.reflect.Field;
import java.util.HashMap;
////
public class JSon {
	@SuppressWarnings("rawtypes")
	public static String toJSON(Object obj) {
		HashMap<String, String> map = new HashMap<String, String>();
		Class c = obj.getClass();
		Field[] fields = c.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
		String name = fields[i].getName();
		try {
		fields[i].setAccessible(true);
		Object o = fields[i].get(obj);
		if (o instanceof Number) {
		map.put("\"" + name + "\"", o.toString());
		} else if (o instanceof String) {
		map.put("\"" + name + "\"", "\"" + o.toString() + "\"");
		}
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		}
		String s = map.toString();
		String str = s.replaceAll("\"=", "\":");
		return str;
	}
	
	public static String toJSON(Object[] objs) {
		String[] strs = new String[objs.length];
		for (int i = 0; i < objs.length; i++) {
		strs[i] = toJSON(objs[i]);
		}
		return toJSONArray(strs);
	}
	
	public static String toJSONArray(String[] strs) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (int i = 0; i < strs.length; i++) {
		sb.append("\"");
		sb.append(i);
		sb.append("\":");
		sb.append(strs[i]);
		sb.append(",");
		}
		sb.append("\"length\":");
		sb.append(strs.length);
		sb.append("}");
		return sb.toString();
	}



}
