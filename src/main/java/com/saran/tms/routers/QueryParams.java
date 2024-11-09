package com.saran.tms.routers;

import java.util.Map;

public class QueryParams {
	private Map<String, String[]> queryParams;
	
	public QueryParams(Map<String, String[]> queryParams) {
		this.queryParams = queryParams;
	}
	
	public String get(String key) {
		String values[] = queryParams.get(key);
		if(values == null || values.length == 0) {
			return null;
		}
		return values[0];
	}
	
	public Short getShort(String key) throws NumberFormatException {
		String value = this.get(key);
		return (value == null)? null : (short) Short.parseShort(value);
	}
	
	public Integer getInt(String key) throws NumberFormatException {
		String value = this.get(key);
		return (value == null)? null : (int) Integer.parseInt(value);
	}
	
	public Long getLong(String key) throws NumberFormatException {
		String value = this.get(key);
		return (value == null)? null : (long) Long.parseLong(value);
	}
	
	public Boolean getBoolean(String key) {
		String value = this.get(key);
		if(value == "1") {
			return true;
		}
		return (value == null)? null : Boolean.parseBoolean(value);
	}
	
	public String[] getAll(String key) {
		return queryParams.get(key);
	}
}
