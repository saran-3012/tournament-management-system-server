package com.saran.tms.database;

import com.saran.tms.enums.TableNames;

public class GroupEntry implements QueryEntry {
	protected TableNames tableName;
	protected String columnName;
	
	public GroupEntry(String columnName) {
		this.columnName = columnName;
	}
	
	public GroupEntry(TableNames tableName, String columnName) {
		this.tableName = tableName;
		this.columnName = columnName;
	}
	
	public String toQueryString() {
		return (tableName == null)? columnName : tableName.getTableName() + '.' + columnName;
	}
}
