package com.saran.tms.database;


import java.util.Arrays;
import java.util.List;

import com.saran.tms.enums.Functions;
import com.saran.tms.enums.TableNames;

public class ColumnEntry implements QueryEntry {
	
	protected TableNames tableName;
	protected List<String> columnNames;
	protected Functions function;
	protected String aliasName;
	
	protected short type;
	
	public ColumnEntry(String columnName) {
		this.type = 0;
		
		this.columnNames = Arrays.asList(columnName);
	}
	
	public ColumnEntry(List<String> columnNames) {
		this.type = 0;
		
		this.columnNames = columnNames;
	}

	public ColumnEntry(TableNames tableName, String columnName) {
		this.type = 0;
		
		this.tableName = tableName;
		this.columnNames = Arrays.asList(columnName);
	}
	
	public ColumnEntry(TableNames tableName, List<String> columnNames) {
		this.type = 0;
		
		this.tableName = tableName;
		this.columnNames = columnNames;
	}
	
	public ColumnEntry(String columnName, String aliasName) {
		this.type = 1;
		
		this.columnNames = Arrays.asList(columnName);
		this.aliasName = aliasName;
	}
	
	public ColumnEntry(TableNames tableName, String columnName, String aliasName) {
		this.type = 1;
		
		this.tableName = tableName;
		this.columnNames = Arrays.asList(columnName);
		this.aliasName = aliasName;
	}
	
	public ColumnEntry(String columnName, Functions function) {
		this.type = 2;
		
		this.columnNames = Arrays.asList(columnName);
		this.function = function;
	}
	
	public ColumnEntry(TableNames tableName, String columnName, Functions function) {
		this.type = 2;
		
		this.tableName = tableName;
		this.columnNames = Arrays.asList(columnName);
		this.function = function;
	}
	
	public ColumnEntry(String columnName, Functions function, String aliasName) {
		this.type = 3;
		
		this.columnNames = Arrays.asList(columnName);
		this.function = function;
		this.aliasName = aliasName;
	}
	
	public ColumnEntry(TableNames tableName, String columnName, Functions function, String aliasName) {
		this.type = 3;
		
		this.tableName = tableName;
		this.columnNames = Arrays.asList(columnName);
		this.function = function;
		this.aliasName = aliasName;
	}
	
	protected String getTableColumnName(String columnName) {
		return (tableName == null)? tableName.getTableName() + '.' + columnName : columnName;
	}
	
	protected String joinColumnNames() {
		if(columnNames == null || columnNames.isEmpty()) {
			return "";
		}
		int n = columnNames.size();
		StringBuilder columnString = new StringBuilder(getTableColumnName(columnNames.get(0)));
		for(int i=1; i<n; i++) {
			columnString.append(',').append(' ').append(getTableColumnName(columnNames.get(i)));
		}
		return columnString.toString();
	}
	
	public String toQueryString() {
		StringBuilder queryStringBuilder = new StringBuilder();
		switch(type) {
			case 0:
				queryStringBuilder.append(joinColumnNames());
				break;
			case 1:
				queryStringBuilder.append(getTableColumnName(columnNames.get(0)))
								  .append(' ')
								  .append("AS")
								  .append(' ')
								  .append(aliasName)
								  .append(' ');
				break;
				
			case 2:
				queryStringBuilder.append(function.getFunction())
								  .append('(')
								  .append(getTableColumnName(columnNames.get(0)))
								  .append(')')
								  .append(' ');
				break;
				
			case 3:
				queryStringBuilder.append(function.getFunction())
								  .append('(')
								  .append(getTableColumnName(columnNames.get(0)))
								  .append(')')
								  .append(' ')
								  .append("AS")
								  .append(' ')
								  .append(aliasName)
								  .append(' ');
				break;
				
			default:;
		}
		
		return queryStringBuilder.toString();
	}
	
}
