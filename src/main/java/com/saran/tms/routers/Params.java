package com.saran.tms.routers;

import java.util.Map;

public class Params {
	private Map<String, String> params;
	
	public Params(Map<String, String> params) {
		this.params = params;
	}
	
	public Short getShort(String key) throws NumberFormatException {
		String value = params.get(key);
		return (value == null)? null : (short) Short.parseShort(value);
	}
	
	public Integer getInt(String key) throws NumberFormatException {
		String value = params.get(key);
		return (value == null)? null : (int) Integer.parseInt(value);
	}
	
	public Long getLong(String key) throws NumberFormatException {
		String value = params.get(key);
		return (value == null)? null : (long) Long.parseLong(value);
	}
	
	public String[] getStringArray(String key) {
		String value = params.get(key);
		return (value == null)? null : value.split(",");
	}
	
	public void put(String key, String value) {
		params.put(key, value);
	}
	
	public String get(String key) {
		return params.get(key);
	}

}
