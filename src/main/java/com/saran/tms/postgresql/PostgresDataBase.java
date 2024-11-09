package com.saran.tms.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import com.saran.tms.adapters.ModelMapParser;
import com.saran.tms.adapters.ResultSetMapParser;
import com.saran.tms.config.DataBaseConfig;
import com.saran.tms.connections.ConnectionPool;
import com.saran.tms.db.DataBase;
import com.saran.tms.enums.CRUD;
import com.saran.tms.enums.Functions;
import com.saran.tms.enums.Operators;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.enums.TableNames;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.models.Model;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.GroupEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.OrderEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;

public class PostgresDataBase implements DataBase {
	
	private static String handleSQLException(String sqlState) {
		 switch (sqlState) {
	     	case "23505":
	     		return "The provided field is duplicated for this entity";
	        case "23502":
	            return "The provided field cannot be Empty or Null value";
	        case "23503":
	            return "Relationship does not exists for this entity";
	        case "42601":
	            return "Query is not well formed";
	        case "08001":
	            return "Connection failed during operation";
	        default:
	            return "Unable to process the content";
		 }
	}
	
	private int executeUpdate(QueryData qd) throws ResponseException {
		
		Connection con;
		
		try {
			con = ConnectionPool.getConnection();
		} 
		catch (SQLException | InterruptedException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Couldn't get the connection", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Server is experiencing high load right now!");
		}
		
		PreparedStatement pst;
		try {
			pst = StatementFactory.createPreparedStatement(con, qd);
		} 
		catch (SQLException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Error during preparing statement", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the request");
		}
		
		int affectedRows = 0;
		
		try {
			affectedRows = pst.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Unable to perform sql operation", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, handleSQLException(e.getSQLState()));
		}

		
		try {
			pst.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.WARNING, "Unable to close the prepared statement", e);
		}
		
		try {
			ConnectionPool.addExistingConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.WARNING, "Error while returning connection", e);
		}
		
		return affectedRows;
	}
	
	private Map<String, Map<String, Object>> executeQuery(QueryData qd) throws ResponseException {

		Connection con;
		
		try {
			con = ConnectionPool.getConnection();
		} 
		catch (SQLException | InterruptedException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Couldn't get the connection", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Server is experiencing high load right now!");
		}
		
		PreparedStatement pst;
		
		try {
			pst = StatementFactory.createPreparedStatement(con, qd);
		} 
		catch (SQLException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Error during preparing statement", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the request");
		}

		ResultSet rs;
		try {
			rs = pst.executeQuery();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Unable to perform sql operation", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, handleSQLException(e.getSQLState()));
		}
		
		Map<String, Map<String, Object>> map = null;
		
		try {
			map = ResultSetMapParser.processRow(rs);
		} 
		catch (SQLException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Error while processing result set", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the request");
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.WARNING, "Unable to close the result set", e);
		}
		
		try {
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.WARNING, "Unable to close the prepared statement", e);
		}
		
		try {
			ConnectionPool.addExistingConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.WARNING, "Error while returning connection", e);
		}
		
		return map;
	}
	
	private List<Map<String, Map<String, Object>>> executeOperation(QueryData qd) throws ResponseException {
		
		Connection con;
		try {
			con = ConnectionPool.getConnection();
		} 
		catch (SQLException | InterruptedException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Couldn't get the connection", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Server is experiencing high load right now!");
		}
		
		PreparedStatement pst;
		try {
			pst = StatementFactory.createPreparedStatement(con, qd);
		} catch (SQLException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Error during preparing statement", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the request");
		}
		
		ResultSet rs;
		try {
			rs = pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Unable to perform sql operation", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, handleSQLException(e.getSQLState()));
		}
		
		List<Map<String, Map<String, Object>>> mapList;
		try {
			mapList = ResultSetMapParser.processAllRows(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Error while processing result set", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the request");
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.WARNING, "Unable to close the result set", e);
		}
		
		try {
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.WARNING, "Unable to close the prepared statement", e);
		}
		
		try {
			ConnectionPool.addExistingConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.WARNING, "Error while returning connection", e);
		}
		
		return mapList;
	}
	
	public int save(Model model) throws ResponseException {
		
		Class<?> modelClass = model.getClass();
		String modelName = modelClass.getSimpleName();
		String tableName = DataBaseConfig.getTableName(modelName);
		TableNames tableNameEnum = TableNames.getEnumValue(tableName);
		
		Map<String, Object> modelMap = null;
		
		modelMap = ModelMapParser.convertToMap(model);

		
		List<String> fieldNames = new ArrayList<String>();
		List<List<Object>> fieldValues = new ArrayList<>();
		
		fieldValues.add(new ArrayList<Object>());
		
		for(Map.Entry<String, Object> entry : modelMap.entrySet()){
			fieldNames.add(entry.getKey());
			fieldValues.get(0).add(entry.getValue());
		}
		
		List<TableColumnEntry> tableColumnEntries = new ArrayList<>();
		tableColumnEntries.add(new TableColumnEntry(tableNameEnum, fieldNames));
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.CREATE);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setFieldValues(fieldValues);
		
		return executeUpdate(qd);
	
	}
	
	public int saveAll(List<Model> modelList) throws ResponseException {
		if(modelList == null || modelList.isEmpty()) {
			return 0;
		}
		
		Class<?> modelClass = modelList.get(0).getClass();
		String modelName = modelClass.getSimpleName();
		String tableName = DataBaseConfig.getTableName(modelName);
		TableNames tableNameEnum = TableNames.getEnumValue(tableName);

		Map<String, Object> modelMap = ModelMapParser.convertToMap(modelList.get(0));

		Set<String> keys = modelMap.keySet();
		
		
		List<String> fieldNames = new ArrayList<String>();
		
		for(String columnName : keys) {
			fieldNames.add(columnName);
		}
		
		List<TableColumnEntry> tableColumnEntries = new ArrayList<>();
		tableColumnEntries.add(new TableColumnEntry(tableNameEnum, fieldNames));
		
		List<List<Object>> fieldValues = new ArrayList<>();
		
		for(Model model : modelList) {
			modelMap = ModelMapParser.convertToMap(model);
			List<Object> rowFieldValues = new ArrayList<>();
			for(String columnName : keys) {
				rowFieldValues.add(modelMap.get(columnName));
			}
			fieldValues.add(rowFieldValues);
		}
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.CREATE);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setFieldValues(fieldValues);
		
		return executeUpdate(qd);
	
	}
	
	public Map<String, Map<String, Object>> saveAndReturn(Model model, List<String> returnEntries) throws ResponseException  {
		
		if(model == null) {
			throw new NullPointerException("Model cannot be null");
		}
		
		Class<?> modelClass = model.getClass();
		String modelName = modelClass.getSimpleName();
		String tableName = DataBaseConfig.getTableName(modelName);
		TableNames tableNameEnum = TableNames.getEnumValue(tableName);
		
		Map<String, Object> modelMap = ModelMapParser.convertToMap(model);

		
		modelMap.remove("id");
		
		List<String> fieldNames = new ArrayList<String>();
		List<List<Object>> fieldValues = new ArrayList<>();
		
		fieldValues.add(new ArrayList<Object>());
		
		for(Map.Entry<String, Object> entry : modelMap.entrySet()){
			fieldNames.add(entry.getKey());
			fieldValues.get(0).add(entry.getValue());
		}
		
		List<TableColumnEntry> tableColumnEntries = new ArrayList<>();
		tableColumnEntries.add(new TableColumnEntry(tableNameEnum, fieldNames));
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.CREATE);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setFieldValues(fieldValues);
		qd.setReturnEntries(returnEntries);
		return executeQuery(qd);
	}
	
	public List<Map<String, Map<String, Object>>> saveAllAndReturn(List<Model> modelList, List<String> returnEntries) throws ResponseException {
		if(modelList == null || modelList.isEmpty()) {
			throw new NullPointerException("Models cannot be null or empty");
		}
		
		Class<?> modelClass = modelList.get(0).getClass();
		String modelName = modelClass.getSimpleName();
		String tableName = DataBaseConfig.getTableName(modelName);
		TableNames tableNameEnum = TableNames.getEnumValue(tableName);

		Map<String, Object> modelMap = ModelMapParser.convertToMap(modelList.get(0));

		Set<String> keys = modelMap.keySet();
		
		keys.remove("id");
		
		List<String> fieldNames = new ArrayList<String>();
		
		for(String columnName : keys) {
			fieldNames.add(columnName);
		}
		
		List<TableColumnEntry> tableColumnEntries = new ArrayList<>();
		tableColumnEntries.add(new TableColumnEntry(tableNameEnum, fieldNames));
		
		List<List<Object>> fieldValues = new ArrayList<>();
		
		for(Model model : modelList) {
			modelMap = ModelMapParser.convertToMap(model);
			List<Object> rowFieldValues = new ArrayList<>();
			for(String columnName : keys) {
				rowFieldValues.add(modelMap.get(columnName));
			}
			fieldValues.add(rowFieldValues);
		}
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.CREATE);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setFieldValues(fieldValues);
		qd.setReturnEntries(returnEntries);
		
		return executeOperation(qd);
	}
	
	public Map<String, Map<String, Object>> findOneById(TableColumnEntry requiredFields, Long id) throws ResponseException {
		
		TableNames tableName = requiredFields.getTableName();
		
		TableConditionEntry idCondition = new TableConditionEntry(tableName, Arrays.asList(new ConditionEntry(null, "id", Arrays.asList(Operators.EQUAL), id)));
		
		List<TableColumnEntry> tableColumnEntries = new ArrayList<>();
		tableColumnEntries.add(requiredFields);
		
		List<TableConditionEntry> tableConditionEntries = new ArrayList<>();
		tableConditionEntries.add(idCondition);
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setConditionEntries(tableConditionEntries);
		qd.setLimit(1);
		
		return executeQuery(qd);
	}
	
	public Map<String, Map<String, Object>> findOne(TableColumnEntry requiredFields, TableConditionEntry requiredContitions) throws ResponseException {
		
		TableNames tableName = requiredFields.getTableName();
		
		if(tableName != requiredContitions.getTableName()) {
			throw new IllegalArgumentException("Table Name does not match!");
		}
		
		List<TableColumnEntry> tableColumnEntries = new ArrayList<>();
		tableColumnEntries.add(requiredFields);
		
		List<TableConditionEntry> tableConditionEntries = new ArrayList<>();
		tableConditionEntries.add(requiredContitions);
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setConditionEntries(tableConditionEntries);
		qd.setLimit(1);

		return executeQuery(qd);
	}
	
	public List<Map<String, Map<String, Object>>> findAll(TableColumnEntry requiredFields, TableConditionEntry requiredContitions) throws ResponseException {
		
		TableNames tableName = requiredFields.getTableName();
		
		if(tableName != requiredContitions.getTableName()) {
			throw new IllegalArgumentException("Table Name does not match!");
		}
		
		List<TableColumnEntry> tableColumnEntries = new ArrayList<>();
		tableColumnEntries.add(requiredFields);
		
		List<TableConditionEntry> tableConditionEntries = new ArrayList<>();
		tableConditionEntries.add(requiredContitions);
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setConditionEntries(tableConditionEntries);
		
		return executeOperation(qd);
		
	}
	
	public List<Map<String, Map<String, Object>>> findAll(TableColumnEntry requiredFields, TableConditionEntry requiredContitions, Integer limit, Integer offset) throws ResponseException {
		
		TableNames tableName = requiredFields.getTableName();
		
		if(tableName != requiredContitions.getTableName()) {
			throw new IllegalArgumentException("Table Name does not match!");
		}
		
		List<TableColumnEntry> tableColumnEntries = new ArrayList<>();
		tableColumnEntries.add(requiredFields);
		
		List<TableConditionEntry> tableConditionEntries = new ArrayList<>();
		tableConditionEntries.add(requiredContitions);
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setConditionEntries(tableConditionEntries);
		qd.setLimit(limit);
		qd.setOffset(offset);

		return executeOperation(qd);
	}
	
	public List<Map<String, Map<String, Object>>> findAll(TableColumnEntry requiredFields, TableConditionEntry requiredContitions, Map<GroupEntry, Functions> fieldFunctions, Integer limit, Integer offset) throws ResponseException {
		
		TableNames tableName = requiredFields.getTableName();
		
		if(tableName != requiredContitions.getTableName()) {
			throw new IllegalArgumentException("Table Name does not match!");
		}
		
		List<TableColumnEntry> tableColumnEntries = new ArrayList<>();
		tableColumnEntries.add(requiredFields);
		
		List<TableConditionEntry> tableConditionEntries = new ArrayList<>();
		tableConditionEntries.add(requiredContitions);
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setFieldFunctions(fieldFunctions);
		qd.setConditionEntries(tableConditionEntries);
		qd.setLimit(limit);
		qd.setOffset(offset);

		return executeOperation(qd);
		
	}
	
	public List<Map<String, Map<String, Object>>> findAll(TableColumnEntry requiredFields, TableConditionEntry requiredContitions, Map<GroupEntry, Functions> fieldFunctions, List<GroupEntry> groupEntries, Integer limit, Integer offset) throws ResponseException {
		TableNames tableName = requiredFields.getTableName();
		
		if(tableName != requiredContitions.getTableName()) {
			throw new IllegalArgumentException("Table Name does not match!");
		}
		
		List<TableColumnEntry> tableColumnEntries = new ArrayList<>();
		tableColumnEntries.add(requiredFields);
		
		List<TableConditionEntry> tableConditionEntries = new ArrayList<>();
		tableConditionEntries.add(requiredContitions);
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setFieldFunctions(fieldFunctions);
		qd.setConditionEntries(tableConditionEntries);
		qd.setGroupEntries(groupEntries);
		qd.setLimit(limit);
		qd.setOffset(offset);

		return executeOperation(qd);
	}
	
	public List<Map<String, Map<String, Object>>> findAll(TableColumnEntry requiredFields, TableConditionEntry requiredContitions, Map<GroupEntry, Functions> fieldFunctions, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException {
		TableNames tableName = requiredFields.getTableName();
		
		if(tableName != requiredContitions.getTableName()) {
			throw new IllegalArgumentException("Table Name does not match!");
		}
		
		List<TableColumnEntry> tableColumnEntries = new ArrayList<>();
		tableColumnEntries.add(requiredFields);
		
		List<TableConditionEntry> tableConditionEntries = new ArrayList<>();
		tableConditionEntries.add(requiredContitions);
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setFieldFunctions(fieldFunctions);
		qd.setConditionEntries(tableConditionEntries);
		qd.setGroupEntries(groupEntries);
		qd.setOrderEntries(orderEntries);
		qd.setLimit(limit);
		qd.setOffset(offset);

		return executeOperation(qd);
	}
	
	public List<Map<String, Map<String, Object>>> findAll(TableColumnEntry requiredFields, TableConditionEntry requiredContitions, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException {
		TableNames tableName = requiredFields.getTableName();
		
		if(tableName != requiredContitions.getTableName()) {
			throw new IllegalArgumentException("Table Name does not match!");
		}
		
		List<TableColumnEntry> tableColumnEntries = new ArrayList<>();
		tableColumnEntries.add(requiredFields);
		
		List<TableConditionEntry> tableConditionEntries = new ArrayList<>();
		tableConditionEntries.add(requiredContitions);
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setConditionEntries(tableConditionEntries);
		qd.setGroupEntries(groupEntries);
		qd.setOrderEntries(orderEntries);
		qd.setLimit(limit);
		qd.setOffset(offset);
		
		return executeOperation(qd);
	}
	
	public List<Map<String, Map<String, Object>>> findAll(TableColumnEntry requiredFields, TableConditionEntry requiredContitions, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException {
		TableNames tableName = requiredFields.getTableName();
		
		if(tableName != requiredContitions.getTableName()) {
			throw new IllegalArgumentException("Table Name does not match!");
		}
		
		List<TableColumnEntry> tableColumnEntries = new ArrayList<>();
		tableColumnEntries.add(requiredFields);
		
		List<TableConditionEntry> tableConditionEntries = new ArrayList<>();
		tableConditionEntries.add(requiredContitions);
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setConditionEntries(tableConditionEntries);
		qd.setOrderEntries(orderEntries);
		qd.setLimit(limit);
		qd.setOffset(offset);
		
		return executeOperation(qd);
	}


	
	public Map<String, Map<String, Object>> findOneWithJoin(List<TableColumnEntry> requiredTablefields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions) throws ResponseException {
		if(requiredTablefields == null || requiredTablefields.isEmpty()) {
			throw new IllegalArgumentException("Required fields are not provided");
		}
		if(joinEntries == null || joinEntries.isEmpty()) {
			throw new IllegalArgumentException("Join fields are not provided");
		}
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(requiredTablefields);
		qd.setJoinEntries(joinEntries);
		qd.setConditionEntries(requiredTableConditions);
		qd.setLimit(1);
		
		return executeQuery(qd);
	}
	
	public List<Map<String, Map<String, Object>>> findAllWithJoin(List<TableColumnEntry> requiredTablefields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions) throws ResponseException {
		if(requiredTablefields == null || requiredTablefields.isEmpty()) {
			throw new IllegalArgumentException("Required fields are not provided");
		}
		if(joinEntries == null || joinEntries.isEmpty()) {
			throw new IllegalArgumentException("Join fields are not provided");
		}
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(requiredTablefields);
		qd.setJoinEntries(joinEntries);
		qd.setConditionEntries(requiredTableConditions);
		
		return executeOperation(qd);
	}
	
	public List<Map<String, Map<String, Object>>> findAllWithJoin(List<TableColumnEntry> requiredTablefields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, Integer limit, Integer offset) throws ResponseException {
		if(requiredTablefields == null || requiredTablefields.isEmpty()) {
			throw new IllegalArgumentException("Required fields are not provided");
		}
		if(joinEntries == null || joinEntries.isEmpty()) {
			throw new IllegalArgumentException("Join fields are not provided");
		}
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(requiredTablefields);
		qd.setJoinEntries(joinEntries);
		qd.setConditionEntries(requiredTableConditions);
		qd.setLimit(limit);
		qd.setOffset(offset);
		
		return executeOperation(qd);
	}
	
	public List<Map<String, Map<String, Object>>> findAllWithJoin(List<TableColumnEntry> requiredTablefields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, Map<GroupEntry, Functions> fieldFunctions, Integer limit, Integer offset) throws ResponseException {
		if(requiredTablefields == null || requiredTablefields.isEmpty()) {
			throw new IllegalArgumentException("Required fields are not provided");
		}
		if(joinEntries == null || joinEntries.isEmpty()) {
			throw new IllegalArgumentException("Join fields are not provided");
		}
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(requiredTablefields);
		qd.setJoinEntries(joinEntries);
		qd.setConditionEntries(requiredTableConditions);
		qd.setFieldFunctions(fieldFunctions);
		qd.setLimit(limit);
		qd.setOffset(offset);
		
		return executeOperation(qd);
	}
	
	public List<Map<String, Map<String, Object>>> findAllWithJoin(List<TableColumnEntry> requiredTablefields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, Map<GroupEntry, Functions> fieldFunctions, List<GroupEntry> groupEntries, Integer limit, Integer offset) throws ResponseException {
		if(requiredTablefields == null || requiredTablefields.isEmpty()) {
			throw new IllegalArgumentException("Required fields are not provided");
		}
		if(joinEntries == null || joinEntries.isEmpty()) {
			throw new IllegalArgumentException("Join fields are not provided");
		}
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(requiredTablefields);
		qd.setJoinEntries(joinEntries);
		qd.setConditionEntries(requiredTableConditions);
		qd.setFieldFunctions(fieldFunctions);
		qd.setGroupEntries(groupEntries);
		qd.setLimit(limit);
		qd.setOffset(offset);
		
		return executeOperation(qd);
	}
	
	public List<Map<String, Map<String, Object>>> findAllWithJoin(List<TableColumnEntry> requiredTablefields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, Map<GroupEntry, Functions> fieldFunctions, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException {
		if(requiredTablefields == null || requiredTablefields.isEmpty()) {
			throw new IllegalArgumentException("Required fields are not provided");
		}
		if(joinEntries == null || joinEntries.isEmpty()) {
			throw new IllegalArgumentException("Join fields are not provided");
		}
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(requiredTablefields);
		qd.setJoinEntries(joinEntries);
		qd.setConditionEntries(requiredTableConditions);
		qd.setFieldFunctions(fieldFunctions);
		qd.setGroupEntries(groupEntries);
		qd.setOrderEntries(orderEntries);
		qd.setLimit(limit);
		qd.setOffset(offset);
		
		return executeOperation(qd);
	}
	
	public List<Map<String, Map<String, Object>>> findAllWithJoin(List<TableColumnEntry> requiredTablefields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException {
		if(requiredTablefields == null || requiredTablefields.isEmpty()) {
			throw new IllegalArgumentException("Required fields are not provided");
		}
		if(joinEntries == null || joinEntries.isEmpty()) {
			throw new IllegalArgumentException("Join fields are not provided");
		}
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(requiredTablefields);
		qd.setJoinEntries(joinEntries);
		qd.setConditionEntries(requiredTableConditions);
		qd.setGroupEntries(groupEntries);
		qd.setOrderEntries(orderEntries);
		qd.setLimit(limit);
		qd.setOffset(offset);
		
		return executeOperation(qd);
	}
	
	public List<Map<String, Map<String, Object>>> findAllWithJoin(List<TableColumnEntry> requiredTablefields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException {
		if(requiredTablefields == null || requiredTablefields.isEmpty()) {
			throw new IllegalArgumentException("Required fields are not provided");
		}
		if(joinEntries == null || joinEntries.isEmpty()) {
			throw new IllegalArgumentException("Join fields are not provided");
		}
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.READ);
		qd.setTableColumnEntries(requiredTablefields);
		qd.setJoinEntries(joinEntries);
		qd.setConditionEntries(requiredTableConditions);
		qd.setOrderEntries(orderEntries);
		qd.setLimit(limit);
		qd.setOffset(offset);
		
		return executeOperation(qd);
	}
	
	public int update(Model model, TableConditionEntry requiredContitions) throws ResponseException {
		if(model == null) {
			throw new NullPointerException("Model cannot be null");
		}
		
		Class<?> modelClass = model.getClass();
		String modelName = modelClass.getSimpleName();
		String tableName = DataBaseConfig.getTableName(modelName);
		TableNames tableNameEnum = TableNames.getEnumValue(tableName);
		
		Map<String, Object> modelMap = ModelMapParser.convertToMap(model);

		
		List<String> fieldNames = new ArrayList<String>();
		List<List<Object>> fieldValues = new ArrayList<>();
		
		fieldValues.add(new ArrayList<Object>());
		
		for(Map.Entry<String, Object> entry : modelMap.entrySet()){
			fieldNames.add(entry.getKey());
			fieldValues.get(0).add(entry.getValue());
		}

		
		List<TableColumnEntry> tableColumnEntries = new ArrayList<>();
		tableColumnEntries.add(new TableColumnEntry(tableNameEnum, fieldNames));
		
		List<TableConditionEntry> tableConditionEntries = new ArrayList<>();
		tableConditionEntries.add(requiredContitions);
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.UPDATE);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setFieldValues(fieldValues);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setConditionEntries(tableConditionEntries);


		return executeUpdate(qd);
	}
	
	public List<Map<String, Map<String, Object>>> updateAndReturn(Model model, TableConditionEntry requiredContitions, List<String> returnEntries) throws ResponseException {
		if(model == null) {
			throw new NullPointerException("Model cannot be null");
		}
		
		Class<?> modelClass = model.getClass();
		String modelName = modelClass.getSimpleName();
		String tableName = DataBaseConfig.getTableName(modelName);
		TableNames tableNameEnum = TableNames.getEnumValue(tableName);
		
		Map<String, Object> modelMap = ModelMapParser.convertToMap(model);

		
		List<String> fieldNames = new ArrayList<String>();
		List<List<Object>> fieldValues = new ArrayList<>();
		
		fieldValues.add(new ArrayList<Object>());
		
		for(Map.Entry<String, Object> entry : modelMap.entrySet()){
			fieldNames.add(entry.getKey());
			fieldValues.get(0).add(entry.getValue());
		}
		
		List<TableColumnEntry> tableColumnEntries = new ArrayList<>();
		tableColumnEntries.add(new TableColumnEntry(tableNameEnum, fieldNames));
		
		List<TableConditionEntry> tableConditionEntries = new ArrayList<>();
		tableConditionEntries.add(requiredContitions);
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.UPDATE);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setFieldValues(fieldValues);
		qd.setTableColumnEntries(tableColumnEntries);
		qd.setConditionEntries(tableConditionEntries);
		qd.setReturnEntries(returnEntries);

		return executeOperation(qd);
	}
	
	public int delete(TableConditionEntry requiredContitions) throws ResponseException {
		
		if(requiredContitions == null || requiredContitions.getTableName() == null || requiredContitions.getColumnContitions() == null || requiredContitions.getColumnContitions().isEmpty()) {
			throw new IllegalArgumentException("Condition data is not provided");
		}
		
		List<TableConditionEntry> tableConditionEntries = Arrays.asList(requiredContitions);
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.DELETE);
		qd.setConditionEntries(tableConditionEntries);
		
		return executeUpdate(qd);
	}
	
	public List<Map<String, Map<String, Object>>> deleteAndReturn(TableConditionEntry requiredContitions, List<String> returnEntries) throws ResponseException {
		
		if(requiredContitions == null || requiredContitions.getTableName() == null || requiredContitions.getColumnContitions() == null || requiredContitions.getColumnContitions().isEmpty()) {
			throw new IllegalArgumentException("Condition data is not provided");
		}
		
		List<TableConditionEntry> tableConditionEntries = Arrays.asList(requiredContitions);
		
		QueryData qd = new QueryData();
		
		qd.setOperation(CRUD.DELETE);
		qd.setConditionEntries(tableConditionEntries);
		qd.setReturnEntries(returnEntries);
		
		return executeOperation(qd);
	}
}
