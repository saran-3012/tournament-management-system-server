package com.saran.tms.enums;

public enum JoinTypes {
	JOIN("JOIN"),
	LEFT_JOIN("LEFT JOIN"),
	RIGHT_JOIN("RIGHT JOIN"),
	OUTER_JOIN("FULL OUTER JOIN");
	
	private String joinType;
	
	private JoinTypes(String joinType) {
		this.joinType = joinType;
	}
	
	public String getJoinType() {
		return this.joinType;
	}
}
