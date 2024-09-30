package com.saran.tms.pojo;

import com.saran.tms.enums.TableNames;

public class GroupEntry {
	private TableNames tableName;
	private String columnName;
	
	public GroupEntry() {}

	public GroupEntry(TableNames tableName, String columnName) {
		this.tableName = tableName;
		this.columnName = columnName;
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
	
}
