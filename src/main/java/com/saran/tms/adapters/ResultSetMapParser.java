package com.saran.tms.adapters;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSetMapParser {
	public static Map<String, Map<String, Object>> processRow(ResultSet rs) throws SQLException {
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		
		Map<String, Map<String, Object>> tableMap = new HashMap<>();
		if(rs.next()) {
			
			for(int i=1; i<=columnCount; i++) {
				
				String tableName = rsmd.getTableName(i);
				String columnName = rsmd.getColumnName(i);
				
				Map<String, Object> rowMap = tableMap.getOrDefault(tableName, new HashMap<>());
				rowMap.put(columnName, rs.getObject(i));
				
				tableMap.put(tableName, rowMap);
				
			}
			
		}
		return tableMap;
	}
	
	public static List<Map<String, Map<String, Object>>> processAllRows(ResultSet rs) throws SQLException {
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		
		List<Map<String, Map<String, Object>>> tableMapList = new ArrayList<>();
		
		while(rs.next()) {
			Map<String, Map<String, Object>> tableMap = new HashMap<>();
			for(int i=1; i<=columnCount; i++) {
				
				String tableName = rsmd.getTableName(i);
				String columnName = rsmd.getColumnName(i);
				
				Map<String, Object> rowMap = tableMap.getOrDefault(tableName, new HashMap<>());
				rowMap.put(columnName, rs.getObject(i));
				
				tableMap.put(tableName, rowMap);
				
			}
			tableMapList.add(tableMap);
		}
		
		return tableMapList;
	}
}
