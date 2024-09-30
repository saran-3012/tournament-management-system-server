package com.saran.tms.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataBaseConfig {
	private static String url;
	private static String user;
	private static String password;
	private static String modelPackage;
	
	private final static Map<String, TableConfig> tableConfigMap = new HashMap<>(); 
	private final static Map<String, TableConfig> modelConfigMap = new HashMap<>();
	
	private static boolean isInitialized = false;
	
	public static void initializeConfig(String pUrl, String pUser, String pPassword, String pModelPackage, Map<String, TableConfig> pTableConfigMap, Map<String, TableConfig> pModelConfigMap) {
		if(isInitialized) return;
		
		url = pUrl;
		user = pUser;
		password = pPassword;
		modelPackage = pModelPackage;
		
		tableConfigMap.putAll(pTableConfigMap);
		modelConfigMap.putAll(pModelConfigMap);
		
		isInitialized = true;
	}
	
	public static String getUrl() {
		return url;
	}

	public static String getUser() {
		return user;
	}

	public static String getPassword() {
		return password;
	}
	
	public static String getModelPackage() {
		return modelPackage;
	}
	
	public static String getTableName(String modelName) {
		return modelConfigMap.get(modelName).getTableName();
	}
	
	public static String getModelName(String tableName) {
		return tableConfigMap.get(tableName).getModelName();
	}

	public static TableConfig getTableConfigByTableName(String tableName) {
		return tableConfigMap.get(tableName);
	}
	
	public static TableConfig getTableConfigByModelName(String modelName) {
		return modelConfigMap.get(modelName);
	}
	
	public static Set<String> getTableNames() {
		return tableConfigMap.keySet();
	}
	
	public static Set<String> getModelNames() {
		return modelConfigMap.keySet();
	}
	
	public static Set<Map.Entry<String, TableConfig>> getTableConfigEntries() {
		return tableConfigMap.entrySet();
	}
	
	public static Set<Map.Entry<String, TableConfig>> getModelConfigEntries() {
		return modelConfigMap.entrySet();
	}
	
}
