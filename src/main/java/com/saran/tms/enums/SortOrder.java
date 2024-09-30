package com.saran.tms.enums;

public enum SortOrder {
	ASC("ASC"),
	DESC("DESC");
	
	private String sortOrder;
	
	private SortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String getSortOrder() {
		return this.sortOrder;
	}
	
}
