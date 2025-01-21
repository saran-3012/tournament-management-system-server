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
	
	// converts nanoseconds to maximum possible time unit in string (maximum is days)
	
	public static String upliftTimeUnit(long nanoSeconds) {
		if(nanoSeconds < 1000000l) {
			return nanoSeconds + " ns";
		}
		if(nanoSeconds < 1000000l * 1000l) {
			return String.format("%.2f %s", ((double) nanoSeconds / 1000000l), "ms");
		}
		if(nanoSeconds < 1000000l * 1000l * 60l) {
			return String.format("%.2f %s", ((double) nanoSeconds / (1000000l * 1000l)), "s");
		}
		if(nanoSeconds < 1000000l * 1000l * 60l * 60l) {
			return String.format("%.2f %s", ((double) nanoSeconds / (1000000l * 1000l * 60l)), "mins");
		}
		if(nanoSeconds < 1000000l * 1000l * 60l * 60l * 24l) {
			return String.format("%.2f %s", ((double) nanoSeconds / (1000000l * 1000l * 60l * 60l)), "hours");
		}
		return String.format("%.2f %s", ((double) nanoSeconds / (1000000l * 1000l * 60l * 60l * 24l)), "days");
	}
	
	// converts bytes to maximum possible memory unit in string (maximum is tb)
	
	public static String convertByteUnit(long bytes) {
		
		if(bytes == -1) {
			return "NA";
		}
		if(bytes < 1024l) {
			return bytes + " bytes";
		}
		if(bytes < 1024l * 1024l) {
			return String.format("%.2f %s", ((double) bytes / 1024l), "kb");
		}
		if(bytes < (1024l * 1024l * 1024l)) {
			return String.format("%.2f %s", ((double) bytes / (1024l * 1024l)), "mb");
		}
		if(bytes < (1024l * 1024l * 1024l * 1024l)) {
			return String.format("%.2f %s", ((double) bytes / (1024l * 1024l * 1024l)), "gb");
		}
		return String.format("%.2f %s", ((double) bytes / (1024l * 1024l * 1024l * 1024l)), "tb");
	}
}
