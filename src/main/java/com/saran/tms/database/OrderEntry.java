package com.saran.tms.database;

import com.saran.tms.enums.SortOrder;
import com.saran.tms.enums.TableNames;

public class OrderEntry implements QueryEntry {
	protected TableNames tableName;
	protected String columnName;
	protected SortOrder sortOrder;
	
	public OrderEntry(String columnName) {
		this.columnName = columnName;
	}
	
	public OrderEntry(String columnName, SortOrder sortOrder) {
		this.columnName = columnName;
		this.sortOrder = sortOrder;
	}
	
	public OrderEntry(TableNames tableName, String columnName) {
		this.tableName = tableName;
		this.columnName = columnName;
	}
	
	public OrderEntry(TableNames tableName, String columnName, SortOrder sortOrder) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.sortOrder = sortOrder;
	}
	
	public String toQueryString() {
		StringBuilder queryStringBuilder = new StringBuilder();
		
		if(tableName != null) {
			queryStringBuilder.append(tableName.getTableName())
							  .append('.');
		}
		
		queryStringBuilder.append(columnName)
						  .append(' ');
		
		if(sortOrder != null) {
			queryStringBuilder.append(sortOrder.getSortOrder())
							  .append(' ');
		}
		
		return queryStringBuilder.toString();
	}
}
