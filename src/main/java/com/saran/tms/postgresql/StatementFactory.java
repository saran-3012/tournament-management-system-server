package com.saran.tms.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import com.saran.tms.enums.StatusCodes;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.JoinConditionEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.TableConditionEntry;

public class StatementFactory {
	
	private static void setObject(PreparedStatement pst, int parameterIndex, Object value) throws SQLException {
		
		if(value instanceof Integer) {				
			pst.setInt(parameterIndex, (Integer) value);
		}
		else if(value instanceof Long) {
			pst.setLong(parameterIndex, (Long) value);
		}
		else if(value instanceof Float) {
			pst.setFloat(parameterIndex, (Float) value);
		}
		else if(value instanceof Double) {
			pst.setDouble(parameterIndex, (Double) value);
		}
		else if(value instanceof String) {
			pst.setString(parameterIndex, (String) value);
		}
		else {
			pst.setObject(parameterIndex, value);
		}
	}
	
	private static PreparedStatement prepareInsertStatement(PreparedStatement pst, List<List<Object>> fieldValues) throws SQLException, IllegalArgumentException {
		
		if(fieldValues == null || fieldValues.isEmpty()) {
			throw new IllegalArgumentException("No values provided");
		}
		
		int parameterIndex = 1;
		
		for(List<Object> fieldRow : fieldValues) {
			if(fieldRow == null) {
				throw new IllegalArgumentException("No field row values provided");
			}
			for(Object fieldValue : fieldRow) {
				setObject(pst, parameterIndex++, fieldValue);
			}
		}
		
		return pst;
	}

	private static PreparedStatement prepareSelectStatement(PreparedStatement pst, List<JoinEntry> joinEntries, List<TableConditionEntry> tableConditionEntries, Integer limit, Integer offset) throws SQLException {
		int parameterIndex = 1;
		
		if(joinEntries != null) {
			for(JoinEntry joinEntry : joinEntries) {
				if(joinEntry.getType() == 1) {
					JoinConditionEntry jce = (JoinConditionEntry) joinEntry;
					setObject(pst, parameterIndex++, jce.getValue());
				}
			}
		}
		
		if(tableConditionEntries != null) {
			for(TableConditionEntry tableConditionEntry : tableConditionEntries) {
				for(ConditionEntry conditionEntry : tableConditionEntry.getColumnContitions()) {
					Object value = conditionEntry.getValue();
					if(value != null) {						
						setObject(pst, parameterIndex++, conditionEntry.getValue());
					}
				}
			}
		}
		
		if(limit != null) {
			setObject(pst, parameterIndex++, limit);
		}
		
		if(offset != null) {
			setObject(pst, parameterIndex++, offset);
		}
		
		return pst;
	}
	
	private static PreparedStatement prepareUpdateStatement(PreparedStatement pst, List<List<Object>> fieldValues, List<TableConditionEntry> tableConditionEntries) throws SQLException {
		
		if(fieldValues == null || fieldValues.isEmpty()) {
			throw new IllegalArgumentException("No values provided");
		}
		
		if(tableConditionEntries == null || tableConditionEntries.isEmpty()) {
			throw new IllegalArgumentException("No filter conditions provided");
		}
		
		int parameterIndex = 1;
		
		for(Object fieldValue : fieldValues.get(0)) {
			setObject(pst, parameterIndex++, fieldValue);
		}
		

		for(TableConditionEntry tableConditionEntry : tableConditionEntries) {
			for(ConditionEntry conditionEntry : tableConditionEntry.getColumnContitions()) {
				setObject(pst, parameterIndex++, conditionEntry.getValue());
			}
		}
		
		return pst;
		
	}
	
	private static PreparedStatement prepareDeleteStatement(PreparedStatement pst, List<TableConditionEntry> tableConditionEntries) throws SQLException {
		if(tableConditionEntries == null || tableConditionEntries.isEmpty()) {
			throw new IllegalArgumentException("No filter conditions provided");
		}
		
		int parameterIndex = 1;
		
		for(TableConditionEntry tableConditionEntry : tableConditionEntries) {
			for(ConditionEntry conditionEntry : tableConditionEntry.getColumnContitions()) {
				setObject(pst, parameterIndex++, conditionEntry.getValue());
			}
		}
		
		return pst;
	}
	
	protected static PreparedStatement createPreparedStatement(Connection con, QueryData query) throws SQLException, ResponseException {
		
		String sqlQuery = QueryFactory.buildQuery(query);
		
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Couldn't get the connection", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Connection failed during operation");
			
		}
		
		switch(query.getOperation()) {
			case CREATE:
				return prepareInsertStatement(pst, query.getFieldValues());
			case READ:
				return prepareSelectStatement(pst, query.getJoinEntries(), query.getConditionEntries(), query.getLimit(), query.getOffset());
			case UPDATE:
				return prepareUpdateStatement(pst, query.getFieldValues(), query.getConditionEntries());
			case DELETE:
				return prepareDeleteStatement(pst, query.getConditionEntries());
			default:
				throw new ResponseException(StatusCodes.UNPROCESSABLE_CONTENT, "Undefined operation!");
		}
		
	}

}
