package com.saran.tms.validators;

import java.util.List;
import java.util.Set;

import com.saran.tms.config.DataBaseConfig;
import com.saran.tms.enums.TableNames;

public class EntityValidator {
	public static boolean validateEntity(TableNames tableName, List<String> columnNames) {
		Set<String> validColumns = DataBaseConfig.getTableConfigByTableName(tableName.getTableName()).getColumnNames();
		for(String columnName : columnNames) {
			if(columnName != "*" && !validColumns.contains(columnName)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean validateEntity(TableNames tableName, String columnName) {
		return columnName == "*" || DataBaseConfig.getTableConfigByTableName(tableName.getTableName()).getColumnNames().contains(columnName);
	}
	
	public static boolean validateEntity(String tableName, String columnName) {
		return columnName == "*" || DataBaseConfig.getTableConfigByTableName(tableName).getColumnNames().contains(columnName);
	}
}
