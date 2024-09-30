package com.saran.tms.enums;

public enum Functions {
	COUNT("COUNT"),
	SUM("SUM"),
	MIN("MIN"),
	MAX("MAX"),
	AVG("AVG");
	
	private String function;
	
	private Functions(String function) {
		this.function = function;
	}
	
	public String getFunction() {
		return this.function;
	}
}
