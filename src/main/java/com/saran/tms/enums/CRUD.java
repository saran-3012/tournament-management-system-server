package com.saran.tms.enums;

public enum CRUD {
	CREATE("INSERT"),
	READ("SELECT"),
	UPDATE("UPDATE"),
	DELETE("DELETE");
	
	private String operation;
	
	private CRUD(String operation) {
		this.operation = operation;
	}
	
	public String getOperation() {
		return this.operation;
	}
}
