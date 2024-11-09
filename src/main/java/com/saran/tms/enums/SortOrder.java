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
	
	public static SortOrder getSortOrder(String value) {
		if(value == null) {
			throw new NullPointerException("Sort order type cannot be null");
		}
		switch(value.toLowerCase()) {
			case "asc":
			case "0":
				return ASC;
			case "desc":
			case "1":
				return DESC;
			default:
				throw new IllegalArgumentException("Invalid sort order type");
		}
	}
	
	public static SortOrder getSortOrder(int value) {
		switch(value) {
		case 0:
			return ASC;
		case 1:
			return DESC;
		default:
			throw new IllegalArgumentException("Invalid sort order type");
		}
	}
	
}
