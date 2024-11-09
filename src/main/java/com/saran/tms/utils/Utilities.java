package com.saran.tms.utils;

public class Utilities {
	public static Object nullFallback(Object expectedValue, Object fallbackValue) {
		return (expectedValue != null)? expectedValue : fallbackValue;
	}
}
