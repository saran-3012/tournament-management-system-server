package com.saran.tms.pojo;

import com.saran.tms.enums.SortOrder;
import com.saran.tms.enums.TableNames;

public class OrderEntry {
	private TableNames tableName;
	private String columnName;
	private SortOrder sortOrder;
	
	public OrderEntry() {}
	
	public OrderEntry(String columnName) {
		this.columnName = columnName;
	}

	public OrderEntry(String columnName, SortOrder sortOrder) {
		this.columnName = columnName;
		this.sortOrder = sortOrder;
	}

	public OrderEntry(TableNames tableName, String columnName, SortOrder sortOrder) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.sortOrder = sortOrder;
	}
	

	public TableNames getTableName() {
		return tableName;
	}

	public void setTableName(TableNames tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
