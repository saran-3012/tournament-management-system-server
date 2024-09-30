package com.saran.tms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.saran.tms.adapters.MapModelParser;
import com.saran.tms.config.DataBaseConfig;
import com.saran.tms.db.DataBase;
import com.saran.tms.enums.Functions;
import com.saran.tms.enums.SortOrder;
import com.saran.tms.enums.TableNames;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.models.Model;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.GroupEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.OrderEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;
import com.saran.tms.postgresql.PostgresDataBase;

public class Dao {
	
	private String modelName;
	private String tableName;
	private TableNames tableNameEnum;
	
	public Dao(Class<?> type) {
		this.modelName = type.getSimpleName();
		this.tableName = DataBaseConfig.getTableName(modelName);
		this.tableNameEnum = TableNames.getEnumValue(tableName);
	}
	
	public Dao(String modelName) {
		this.modelName = modelName;
		this.tableName = DataBaseConfig.getTableName(modelName);
		this.tableNameEnum = TableNames.getEnumValue(tableName);
	}
	
	public int save(Model model) throws ResponseException {
		if(model == null) {
			throw new IllegalArgumentException("Model cannot be null");
		}
		DataBase pdb = new PostgresDataBase();
		int affectedRow = pdb.save(model);
		return affectedRow;
	}
	
	public int saveAll(List<Model> models) throws ResponseException {
		if(models == null || models.isEmpty()) {
			throw new IllegalArgumentException("No data provided for insertion");
		}
		DataBase pdb = new PostgresDataBase();
		int affectedRows = pdb.saveAll(models);
		
		return affectedRows;
	}
	
	public Model saveAndReturn(Model model, List<String> returnEntries) throws ResponseException {
		if(returnEntries == null || returnEntries.isEmpty()) {
			throw new IllegalArgumentException("Required return columns are not provided");
		}
		
		DataBase pdb = new PostgresDataBase();
		Map<String, Map<String, Object>> objMap = pdb.saveAndReturn(model, returnEntries);
		
		Model newModel = MapModelParser.convertToObject(modelName, objMap.get(tableName));
		
		return newModel;
	}
	
	public List<Model> saveAllAndReturn(List<Model> models, List<String> returnEntries) throws ResponseException {
		if(returnEntries == null || returnEntries.isEmpty()) {
			throw new IllegalArgumentException("Required return columns are not provided");
		}
		
		if(models == null || models.isEmpty()) {
			throw new NullPointerException("Models are not provided");
		}
		
		DataBase pdb = new PostgresDataBase();
		List<Map<String, Map<String, Object>>> objMapList = pdb.saveAllAndReturn(models, returnEntries);
		
		List<Model> newModels = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			newModels.add(MapModelParser.convertToObject(modelName, objMap.get(tableName)));
		}
		
		return newModels;
		
	}
	
	public Model findOne(List<String> requiredFields, List<ConditionEntry> requiredConditions) throws ResponseException {
		if(requiredFields == null || requiredFields.isEmpty() || requiredConditions == null || requiredConditions.isEmpty()) {
			throw new IllegalArgumentException("Required fields are not mentioned");
		}
		
		DataBase pdb = new PostgresDataBase();
		
		Map<String, Map<String, Object>> objMap = pdb.findOne(
				new TableColumnEntry(tableNameEnum, requiredFields), 
				new TableConditionEntry(tableNameEnum, requiredConditions)
			);
		
		
		Model model = MapModelParser.convertToObject(modelName, objMap.get(tableName));
		return model;
	}
	
	public List<Model> findAll(List<String> requiredFields, List<ConditionEntry> requiredConditions) throws ResponseException {
		if(requiredFields == null || requiredFields.isEmpty() || requiredConditions == null || requiredConditions.isEmpty()) {
			throw new IllegalArgumentException("Required fields are not mentioned");
		}

		DataBase pdb = new PostgresDataBase();
		List<Map<String, Map<String, Object>>> objMapList = pdb.findAll(
					new TableColumnEntry(tableNameEnum, requiredFields), 
					new TableConditionEntry(tableNameEnum, requiredConditions)
				);
		
		List<Model> models = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			models.add(MapModelParser.convertToObject(modelName, objMap.get(tableName)));
		}
		
		return models;
	}
	
	public List<Model> findAll(List<String> requiredFields, List<ConditionEntry> requiredConditions, Integer limit, Integer offset) throws ResponseException {
		if(requiredFields == null || requiredFields.isEmpty() || requiredConditions == null || requiredConditions.isEmpty()) {
			throw new IllegalArgumentException("Required fields are not mentioned");
		}

		DataBase pdb = new PostgresDataBase();
		List<Map<String, Map<String, Object>>> objMapList = pdb.findAll(
				new TableColumnEntry(tableNameEnum, requiredFields), 
				new TableConditionEntry(tableNameEnum, requiredConditions),
				limit,
				offset
			);
		
		List<Model> models = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			models.add(MapModelParser.convertToObject(modelName, objMap.get(tableName)));
		}
		
		return models;
	}
	
	public List<Model> findAll(List<String> requiredFields, List<ConditionEntry> requiredConditions,  Map<String, Functions> fieldFunctions, Integer limit, Integer offset) throws ResponseException {
		if(requiredFields == null || requiredFields.isEmpty() || requiredConditions == null || requiredConditions.isEmpty()) {
			throw new IllegalArgumentException("Required fields are not mentioned");
		}

		DataBase pdb = new PostgresDataBase();
		List<Map<String, Map<String, Object>>> objMapList = pdb.findAll(
				new TableColumnEntry(tableNameEnum, requiredFields), 
				new TableConditionEntry(tableNameEnum, requiredConditions),
				fieldFunctions,
				limit,
				offset
			);
		
		List<Model> models = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			models.add(MapModelParser.convertToObject(modelName, objMap.get(tableName)));
		}
		
		return models;
	}
	
	public List<Model> findAll(List<String> requiredFields, List<ConditionEntry> requiredContitions,  Map<String, Functions> fieldFunctions, List<String> groupFields, Integer limit, Integer offset) throws ResponseException {
		if(requiredFields == null || requiredContitions == null) {
			throw new IllegalArgumentException("Required fields are not mentioned");
		}
		
		if(groupFields == null) {
			throw new IllegalArgumentException("Group by cannot be null");
		}

		List<GroupEntry> groupEntries = new ArrayList<>();
		
		for(String groupField : groupFields) {
			groupEntries.add(new GroupEntry(tableNameEnum, groupField));
		}
		
		DataBase pdb = new PostgresDataBase();
		List<Map<String, Map<String, Object>>> objMapList = pdb.findAll(
				new TableColumnEntry(tableNameEnum, requiredFields), 
				new TableConditionEntry(tableNameEnum, requiredContitions),
				fieldFunctions,
				groupEntries,
				limit,
				offset
			);
		
		List<Model> models = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			models.add(MapModelParser.convertToObject(modelName, objMap.get(tableName)));
		}
		
		return models;
	} 
	
	public List<Model> findAll(List<String> requiredFields, List<ConditionEntry> requiredContitions,  Map<String, Functions> fieldFunctions, List<String> groupFields, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException {
		if(requiredFields == null || requiredContitions == null) {
			throw new IllegalArgumentException("Required fields are not mentioned");
		}
		
		if(groupFields == null) {
			throw new IllegalArgumentException("Group by cannot be null");
		}
		
		if(orderEntries == null) {
			throw new IllegalArgumentException("Order by cannot be null");
		}

		List<GroupEntry> groupEntries = new ArrayList<>();
		
		for(String groupField : groupFields) {
			groupEntries.add(new GroupEntry(tableNameEnum, groupField));
		}
		
		for(OrderEntry orderEntry : orderEntries) {
			orderEntry.setTableName(tableNameEnum);
			if(orderEntry.getSortOrder() == null) {
				orderEntry.setSortOrder(SortOrder.ASC);
			}
		}
		
		DataBase pdb = new PostgresDataBase();
		List<Map<String, Map<String, Object>>> objMapList = pdb.findAll(
				new TableColumnEntry(tableNameEnum, requiredFields), 
				new TableConditionEntry(tableNameEnum, requiredContitions),
				fieldFunctions,
				groupEntries,
				orderEntries,
				limit,
				offset
			);
		
		List<Model> models = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			models.add(MapModelParser.convertToObject(modelName, objMap.get(tableName)));
		}
		
		return models;
	}
	
	public List<Model> findAll(List<String> requiredFields, List<ConditionEntry> requiredConditions, List<String> groupFields, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException {
		if(requiredFields == null || requiredConditions == null) {
			throw new IllegalArgumentException("Required fields are not mentioned");
		}
		
		if(groupFields == null) {
			throw new IllegalArgumentException("Group by cannot be null");
		}
		
		if(orderEntries == null) {
			throw new IllegalArgumentException("Order by cannot be null");
		}

		List<GroupEntry> groupEntries = new ArrayList<>();
		
		for(String groupField : groupFields) {
			groupEntries.add(new GroupEntry(tableNameEnum, groupField));
		}
		
		for(OrderEntry orderEntry : orderEntries) {
			orderEntry.setTableName(tableNameEnum);
			if(orderEntry.getSortOrder() == null) {
				orderEntry.setSortOrder(SortOrder.ASC);
			}
		}
		
		DataBase pdb = new PostgresDataBase();
		List<Map<String, Map<String, Object>>> objMapList = pdb.findAll(
				new TableColumnEntry(tableNameEnum, requiredFields), 
				new TableConditionEntry(tableNameEnum, requiredConditions),
				groupEntries,
				orderEntries,
				limit,
				offset
			);
		
		List<Model> models = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			models.add(MapModelParser.convertToObject(modelName, objMap.get(tableName)));
		}
		
		return models;
	}
	
	public List<Model> findAll(List<String> requiredFields, List<ConditionEntry> requiredConditions, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException {
		if(requiredFields == null || requiredConditions == null) {
			throw new IllegalArgumentException("Required fields are not mentioned");
		}
		
		if(orderEntries == null) {
			throw new IllegalArgumentException("Order by cannot be null");
		}

		for(OrderEntry orderEntry : orderEntries) {
			orderEntry.setTableName(tableNameEnum);
			if(orderEntry.getSortOrder() == null) {
				orderEntry.setSortOrder(SortOrder.ASC);
			}
		}
		
		DataBase pdb = new PostgresDataBase();
		List<Map<String, Map<String, Object>>> objMapList = pdb.findAll(
				new TableColumnEntry(tableNameEnum, requiredFields), 
				new TableConditionEntry(tableNameEnum, requiredConditions),
				orderEntries,
				limit,
				offset
			);
		
		List<Model> models = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			models.add(MapModelParser.convertToObject(modelName, objMap.get(tableName)));
		}
		
		return models;
	}
	
	public int update(Model model, List<ConditionEntry> requiredConditions) throws ResponseException {
		if(requiredConditions == null || requiredConditions.isEmpty()) {
			throw new IllegalArgumentException("Update conditions cannot be null");
		}

		DataBase pdb = new PostgresDataBase();
		int affectedRows = pdb.update(model, new TableConditionEntry(tableNameEnum, requiredConditions));
		
		return affectedRows;
	}
	
	public List<Model> updateAndReturn(Model model, List<ConditionEntry> requiredConditions, List<String> returnEntries) throws ResponseException {
		if(requiredConditions == null || requiredConditions.isEmpty()) {
			throw new IllegalArgumentException("Update conditions cannot be null");
		}
		
		DataBase pdb = new PostgresDataBase();
		List<Map<String, Map<String, Object>>> objMapList = pdb.updateAndReturn(model, new TableConditionEntry(tableNameEnum, requiredConditions), returnEntries);
		
		List<Model> updatedModels = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			updatedModels.add(MapModelParser.convertToObject(modelName, objMap.get(tableName)));
		}
		
		return updatedModels;
	}
	
	public int delete(List<ConditionEntry> requiredConditions) throws ResponseException {
		if(requiredConditions == null || requiredConditions.isEmpty()) {
			throw new IllegalArgumentException("Delete conditions cannot be null");
		}
		
		DataBase pdb = new PostgresDataBase();
		int affectedRows = pdb.delete(new TableConditionEntry(tableNameEnum, requiredConditions));
		
		return affectedRows;
	}
	
	public List<Model> deleteAndReturn(List<ConditionEntry> requiredConditions, List<String> returnEntries) throws ResponseException {
		if(requiredConditions == null || requiredConditions.isEmpty()) {
			throw new IllegalArgumentException("Delete conditions cannot be null");
		}
		
		DataBase pdb = new PostgresDataBase();
		
		List<Map<String, Map<String, Object>>> objMapList = pdb.deleteAndReturn(new TableConditionEntry(tableNameEnum, requiredConditions), returnEntries);
		
		List<Model> deletedModels = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			deletedModels.add(MapModelParser.convertToObject(modelName, objMap.get(tableName)));
		}
		
		return deletedModels;
	}
	
	public List<Model> findOneWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions) throws ResponseException {
		if(requiredTableFields == null) {
			throw new IllegalArgumentException("Required fields not provided");
		}
		if(joinEntries == null) {
			throw new IllegalArgumentException("Join conditions not provided");
		}
		if(requiredTableConditions == null) {
			throw new IllegalArgumentException("Filter conditions not provided");
		}
		
		DataBase pdb = new PostgresDataBase();
		
		Map<String, Map<String, Object>> objMap = pdb.findOneWithJoin(requiredTableFields, joinEntries, requiredTableConditions);
		
		List<Model> models = new ArrayList<>();
		
		for(Map.Entry<String, Map<String, Object>>  objEntry : objMap.entrySet()) {
			String tableName = objEntry.getKey();
			Map<String, Object> tableMap = objEntry.getValue();
			String modelName = DataBaseConfig.getModelName(tableName);
			Model modelObject = MapModelParser.convertToObject(modelName, tableMap);
			
			models.add(modelObject);
		}
		
		return models;
	}
	
	public List<List<Model>> findAllWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions) throws ResponseException {
		if(requiredTableFields == null) {
			throw new IllegalArgumentException("Required fields not provided");
		}
		if(joinEntries == null) {
			throw new IllegalArgumentException("Join conditions not provided");
		}
		if(requiredTableConditions == null) {
			throw new IllegalArgumentException("Filter conditions not provided");
		}
		
		DataBase pdb = new PostgresDataBase();
		
		List<Map<String, Map<String, Object>>> objMapList = pdb.findAllWithJoin(requiredTableFields, joinEntries, requiredTableConditions);
		
		List<List<Model>> modelsList = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			List<Model> models = new ArrayList<>();
			
			for(Map.Entry<String, Map<String, Object>>  objEntry : objMap.entrySet()) {
				String tableName = objEntry.getKey();
				Map<String, Object> tableMap = objEntry.getValue();
				String modelName = DataBaseConfig.getModelName(tableName);
				Model modelObject = MapModelParser.convertToObject(modelName, tableMap);
				
				models.add(modelObject);
			}
			
			modelsList.add(models);
		}
		
		return modelsList;
	}
	
	public List<List<Model>> findAllWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, Integer limit, Integer offset) throws ResponseException {
		if(requiredTableFields == null) {
			throw new IllegalArgumentException("Required fields not provided");
		}
		if(joinEntries == null) {
			throw new IllegalArgumentException("Join conditions not provided");
		}
		if(requiredTableConditions == null) {
			throw new IllegalArgumentException("Filter conditions not provided");
		}
		
		DataBase pdb = new PostgresDataBase();
		
		List<Map<String, Map<String, Object>>> objMapList = pdb.findAllWithJoin(requiredTableFields, joinEntries, requiredTableConditions, limit, offset);
		
		List<List<Model>> modelsList = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			List<Model> models = new ArrayList<>();
			
			for(Map.Entry<String, Map<String, Object>>  objEntry : objMap.entrySet()) {
				String tableName = objEntry.getKey();
				Map<String, Object> tableMap = objEntry.getValue();
				String modelName = DataBaseConfig.getModelName(tableName);
				Model modelObject = MapModelParser.convertToObject(modelName, tableMap);
				
				models.add(modelObject);
			}
			
			modelsList.add(models);
		}
		
		return modelsList;
	}
	
	public List<List<Model>> findAllWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, Map<String, Functions> fieldFunctions, Integer limit, Integer offset) throws ResponseException {
		if(requiredTableFields == null) {
			throw new IllegalArgumentException("Required fields not provided");
		}
		if(joinEntries == null) {
			throw new IllegalArgumentException("Join conditions not provided");
		}
		if(requiredTableConditions == null) {
			throw new IllegalArgumentException("Filter conditions not provided");
		}
		if(fieldFunctions == null) {
			throw new IllegalArgumentException("Functions cannot be null");
		}
		
		DataBase pdb = new PostgresDataBase();
		
		List<Map<String, Map<String, Object>>> objMapList = pdb.findAllWithJoin(requiredTableFields, joinEntries, requiredTableConditions, fieldFunctions, limit, offset);
		
		List<List<Model>> modelsList = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			List<Model> models = new ArrayList<>();
			
			for(Map.Entry<String, Map<String, Object>>  objEntry : objMap.entrySet()) {
				String tableName = objEntry.getKey();
				Map<String, Object> tableMap = objEntry.getValue();
				String modelName = DataBaseConfig.getModelName(tableName);
				Model modelObject = MapModelParser.convertToObject(modelName, tableMap);
				
				models.add(modelObject);
			}
			
			modelsList.add(models);
		}
		
		return modelsList;
	}
	
	public List<List<Model>> findAllWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, Map<String, Functions> fieldFunctions, List<GroupEntry> groupEntries, Integer limit, Integer offset) throws ResponseException {
		if(requiredTableFields == null) {
			throw new IllegalArgumentException("Required fields not provided");
		}
		if(joinEntries == null) {
			throw new IllegalArgumentException("Join conditions not provided");
		}
		if(requiredTableConditions == null) {
			throw new IllegalArgumentException("Filter conditions not provided");
		}
		if(fieldFunctions == null) {
			throw new IllegalArgumentException("Functions cannot be null");
		}
		if(groupEntries == null) {
			throw new IllegalArgumentException("Group by cannot be null");
		}
		
		DataBase pdb = new PostgresDataBase();
		
		List<Map<String, Map<String, Object>>> objMapList = pdb.findAllWithJoin(requiredTableFields, joinEntries, requiredTableConditions, fieldFunctions, groupEntries, limit, offset);
		
		List<List<Model>> modelsList = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			List<Model> models = new ArrayList<>();
			
			for(Map.Entry<String, Map<String, Object>>  objEntry : objMap.entrySet()) {
				String tableName = objEntry.getKey();
				Map<String, Object> tableMap = objEntry.getValue();
				String modelName = DataBaseConfig.getModelName(tableName);
				Model modelObject = MapModelParser.convertToObject(modelName, tableMap);
				
				models.add(modelObject);
			}
			
			modelsList.add(models);
		}
		
		return modelsList;
	}
	
	public List<List<Model>> findAllWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, Map<String, Functions> fieldFunctions, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException {
		if(requiredTableFields == null) {
			throw new IllegalArgumentException("Required fields not provided");
		}
		if(joinEntries == null) {
			throw new IllegalArgumentException("Join conditions not provided");
		}
		if(requiredTableConditions == null) {
			throw new IllegalArgumentException("Filter conditions not provided");
		}
		if(fieldFunctions == null) {
			throw new IllegalArgumentException("Functions cannot be null");
		}
		if(groupEntries == null) {
			throw new IllegalArgumentException("Group by cannot be null");
		}
		if(orderEntries == null) {
			throw new IllegalArgumentException("Order by cannot be null");
		}
		
		DataBase pdb = new PostgresDataBase();
		
		List<Map<String, Map<String, Object>>> objMapList = pdb.findAllWithJoin(requiredTableFields, joinEntries, requiredTableConditions, fieldFunctions, groupEntries, orderEntries, limit, offset);
		
		List<List<Model>> modelsList = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			List<Model> models = new ArrayList<>();
			
			for(Map.Entry<String, Map<String, Object>>  objEntry : objMap.entrySet()) {
				String tableName = objEntry.getKey();
				Map<String, Object> tableMap = objEntry.getValue();
				String modelName = DataBaseConfig.getModelName(tableName);
				Model modelObject = MapModelParser.convertToObject(modelName, tableMap);
				
				models.add(modelObject);
			}
			
			modelsList.add(models);
		}
		
		return modelsList;
	}
	
	public List<List<Model>> findAllWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException {
		if(requiredTableFields == null) {
			throw new IllegalArgumentException("Required fields not provided");
		}
		if(joinEntries == null) {
			throw new IllegalArgumentException("Join conditions not provided");
		}
		if(requiredTableConditions == null) {
			throw new IllegalArgumentException("Filter conditions not provided");
		}
		if(groupEntries == null) {
			throw new IllegalArgumentException("Group by cannot be null");
		}
		if(orderEntries == null) {
			throw new IllegalArgumentException("Order by cannot be null");
		}
		
		DataBase pdb = new PostgresDataBase();
		
		List<Map<String, Map<String, Object>>> objMapList = pdb.findAllWithJoin(requiredTableFields, joinEntries, requiredTableConditions, groupEntries, orderEntries, limit, offset);
		
		List<List<Model>> modelsList = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			List<Model> models = new ArrayList<>();
			
			for(Map.Entry<String, Map<String, Object>>  objEntry : objMap.entrySet()) {
				String tableName = objEntry.getKey();
				Map<String, Object> tableMap = objEntry.getValue();
				String modelName = DataBaseConfig.getModelName(tableName);
				Model modelObject = MapModelParser.convertToObject(modelName, tableMap);
				
				models.add(modelObject);
			}
			
			modelsList.add(models);
		}
		
		return modelsList;
	}
	
	public List<List<Model>> findAllWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException {
		if(requiredTableFields == null) {
			throw new IllegalArgumentException("Required fields not provided");
		}
		if(joinEntries == null) {
			throw new IllegalArgumentException("Join conditions not provided");
		}
		if(requiredTableConditions == null) {
			throw new IllegalArgumentException("Filter conditions not provided");
		}
		if(orderEntries == null) {
			throw new IllegalArgumentException("Order by cannot be null");
		}
		
		DataBase pdb = new PostgresDataBase();
		
		List<Map<String, Map<String, Object>>> objMapList = pdb.findAllWithJoin(requiredTableFields, joinEntries, requiredTableConditions, orderEntries, limit, offset);
		
		List<List<Model>> modelsList = new ArrayList<>();
		
		for(Map<String, Map<String, Object>> objMap : objMapList) {
			List<Model> models = new ArrayList<>();
			
			for(Map.Entry<String, Map<String, Object>>  objEntry : objMap.entrySet()) {
				String tableName = objEntry.getKey();
				Map<String, Object> tableMap = objEntry.getValue();
				String modelName = DataBaseConfig.getModelName(tableName);
				Model modelObject = MapModelParser.convertToObject(modelName, tableMap);
				
				models.add(modelObject);
			}
			
			modelsList.add(models);
		}
		
		return modelsList;
	}
}