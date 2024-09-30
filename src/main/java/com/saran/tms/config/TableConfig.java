package com.saran.tms.config;

import java.util.Map;
import java.util.Set;

public class TableConfig {
	private String tableName;
	private String modelName;
	private Map<String, ColumnConfig> columnMapping;
	private Map<String, ColumnConfig> fieldMapping;
	
	public TableConfig(String tableName, String modelName, Map<String, ColumnConfig> columnMapping,
			Map<String, ColumnConfig> fieldMapping) {
		this.tableName = tableName;
		this.modelName = modelName;
		this.columnMapping = columnMapping;
		this.fieldMapping = fieldMapping;
	}

	public String getTableName() {
		return tableName;
	}

	public String getModelName() {
		return modelName;
	}
	
	public String getColumnName(String fieldName) {
		return fieldMapping.get(fieldName).getColumnName();
	}
	
	public String getFieldName(String columnName) {
		return columnMapping.get(columnName).getFieldName();
	}

	public ColumnConfig getColumnConfigByColumnName(String columnName) {
		return columnMapping.get(columnName);
	}
	
	public ColumnConfig getColumnConfigByFieldName(String fieldName) {
		return fieldMapping.get(fieldName);
	}
	
	public Set<String> getColumnNames() {
		return columnMapping.keySet();
	}
	
	public Set<String> getFieldNames() {
		return fieldMapping.keySet();
	}
	
	public Set<Map.Entry<String, ColumnConfig>> getColumnConfigEntries() {
		return columnMapping.entrySet();
	}
	
	public Set<Map.Entry<String, ColumnConfig>> getFieldConfigEntries() {
		return fieldMapping.entrySet();
	}

	@Override
	public String toString() {
		return "TableConfig [tableName=" + tableName + ", modelName=" + modelName + ", columnMapping=" + columnMapping
				+ ", fieldMapping=" + fieldMapping + "]\n";
	}
	
}
