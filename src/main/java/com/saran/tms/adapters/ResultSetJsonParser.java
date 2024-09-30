package com.saran.tms.adapters;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.json.JSONArray;
import org.json.JSONObject;

import com.saran.tms.config.DataBaseConfig;

public class ResultSetJsonParser {
	public static JSONObject parse(ResultSet rs) throws Exception {
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		
		rs.next();
		
		JSONObject json = new JSONObject();
		
		for(int i=1; i<=columnCount; i++) {

			String tableName = rsmd.getTableName(i);
			String columnName = rsmd.getColumnName(i);
			
			String fieldName = DataBaseConfig.getTableConfigByTableName(tableName).getFieldName(columnName);
			
			json.put(fieldName, rs.getObject(i));
			
		}

		return json;
		
	}
	
	public static JSONArray parseAll(ResultSet rs) throws Exception {
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		
		JSONArray jsonArray = new JSONArray();
		
		while(rs.next()) {
			JSONObject json = new JSONObject();
			
			for(int i=1; i<=columnCount; i++) {

				String tableName = rsmd.getTableName(i);
				String columnName = rsmd.getColumnName(i);
				
				String fieldName = DataBaseConfig.getTableConfigByTableName(tableName).getFieldName(columnName);
				
				json.put(fieldName, rs.getObject(i));
				
			}
			
			jsonArray.put(json);
		}
		
		return jsonArray;
		
	}
}


