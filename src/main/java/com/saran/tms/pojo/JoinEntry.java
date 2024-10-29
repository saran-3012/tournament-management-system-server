package com.saran.tms.pojo;

import com.saran.tms.enums.JoinTypes;
import com.saran.tms.enums.TableNames;

public class JoinEntry {
	
	private static final int type = 0;
	
	private TableNames tableName1;
	private TableNames tableName2;
	private String columnName1;
	private String columnName2;
	private JoinTypes joinType;
	
	public JoinEntry() {}

	public JoinEntry(TableNames tableName1, TableNames tableName2, String columnName1, String columnName2, JoinTypes joinType) {
		this.tableName1 = tableName1;
		this.tableName2 = tableName2;
		this.columnName1 = columnName1;
		this.columnName2 = columnName2;
		this.joinType = joinType;
	}

	public TableNames getTableName1() {
		return tableName1;
	}

	public void setTableName1(TableNames tableName1) {
		this.tableName1 = tableName1;
	}

	public TableNames getTableName2() {
		return tableName2;
	}

	public void setTableName2(TableNames tableName2) {
		this.tableName2 = tableName2;
	}

	public String getColumnName1() {
		return columnName1;
	}

	public void setColumnName1(String columnName1) {
		this.columnName1 = columnName1;
	}

	public String getColumnName2() {
		return columnName2;
	}

	public void setColumnName2(String columnName2) {
		this.columnName2 = columnName2;
	}

	public JoinTypes getJoinType() {
		return joinType;
	}

	public void setJoinType(JoinTypes joinType) {
		this.joinType = joinType;
	}

	public int getType() {
		return type;
	}
	
}
