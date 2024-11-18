package com.saran.tms.utils;

public class Utilities {
	public static Object nullFallback(Object expectedValue, Object fallbackValue) {
		return (expectedValue != null)? expectedValue : fallbackValue;
	}
	
	public static String toCamelCase(String value) {
		String splittedValues[] = value.split("-");
		int n = splittedValues.length;
		if(n < 2) {
			return value;
		}
		StringBuilder sb = new StringBuilder(splittedValues[0]);
		for(int i=1; i<n; i++) {
			sb.append(Character.toUpperCase(splittedValues[i].charAt(0)))
			  .append(splittedValues[i].substring(1));
		}
		return sb.toString();
	}
}
