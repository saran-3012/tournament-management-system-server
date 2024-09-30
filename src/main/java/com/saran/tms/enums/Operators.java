package com.saran.tms.enums;

public enum Operators {
	AND("AND"),
	OR("OR"),
	NOT("NOT"),
	NULL("NULL"),
	IS("IS"),
	LIKE("LIKE"),
	ILIKE("ILIKE"),
	EQUAL("="),
	NOT_EQUAL("!="),
	LESS_THAN("<"),
	GREATER_THAN(">"),
	LESS_THAN_OR_EQUAL("<="),
	GREATER_THAN_OR_EQUAL(">="),
	IN("IN"),
	BETWEEN("BETWEEN"),
	EXISTS("EXISTS");
	
	private String operator;
	
	private Operators(String operator) {
		this.operator = operator;
	}
	
	public String getOperator() {
		return this.operator;
	}
}
