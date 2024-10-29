package com.saran.tms.pojo;

import java.util.Objects;

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
	
	public String getGroupName() {
		return this.tableName.getTableName() + '.' + this.columnName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(columnName, tableName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupEntry other = (GroupEntry) obj;
		return Objects.equals(columnName, other.columnName) && tableName == other.tableName;
	}
	
}
