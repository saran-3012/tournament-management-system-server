package com.saran.tms.pojo;

import java.util.List;

import com.saran.tms.enums.TableNames;

public class TableConditionEntry {
	private TableNames tableName;
	private List<ConditionEntry> columnContitions;
	
	public TableConditionEntry() {}

	public TableConditionEntry(TableNames tableName, List<ConditionEntry> columnContitions) {
		this.tableName = tableName;
		this.columnContitions = columnContitions;
	}

	public TableNames getTableName() {
		return tableName;
	}

	public void setTableName(TableNames tableName) {
		this.tableName = tableName;
	}

	public List<ConditionEntry> getColumnContitions() {
		return columnContitions;
	}

	public void setColumnContitions(List<ConditionEntry> columnContitions) {
		this.columnContitions = columnContitions;
	}
	
}
