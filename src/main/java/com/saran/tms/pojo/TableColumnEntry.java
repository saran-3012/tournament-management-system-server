package com.saran.tms.pojo;

import java.util.List;

import com.saran.tms.enums.TableNames;

public class TableColumnEntry {
	private TableNames tableName;
	private List<String> columnNames;
	
	public TableColumnEntry() {}

	public TableColumnEntry(TableNames tableName, List<String> columnNames) {
		this.tableName = tableName;
		this.columnNames = columnNames;
	}

	public TableNames getTableName() {
		return tableName;
	}
	public void setTableName(TableNames tableName) {
		this.tableName = tableName;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}
	
}
