package com.saran.tms.postgresql;

import java.util.List;
import java.util.Map;


import com.saran.tms.enums.Functions;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.enums.TableNames;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.pojo.GroupEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.OrderEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;
import com.saran.tms.validators.EntityValidator;

public class QueryFactory {

	private static String buildInsertQuery(TableNames tableName, List<String> fieldNames, List<List<Object>> fieldValues, List<String> returnEntries) throws IllegalArgumentException, ResponseException {
		
		if(fieldNames == null || fieldNames.isEmpty() || fieldValues == null || fieldValues.isEmpty()) {
			throw new IllegalArgumentException("No fields are provided!");
		}
		
		if(!EntityValidator.validateEntity(tableName, fieldNames)) {
			throw new ResponseException(StatusCodes.UNPROCESSABLE_CONTENT, "Fields provided are not valid!");
		}

		int n = fieldNames.size();
		int m = fieldValues.size();
		
		
		QueryBuilder qb = new QueryBuilder();
		
		qb.insert(tableName.getTableName())
			.openBracket()
			.fields(fieldNames)
			.closeBracket()
			.values()
			.qm(m, n)
			.returnings(returnEntries);
		
		return qb.build();
	}
	
	private static String buildSelectQuery(List<TableColumnEntry> tableColumnEntries, Map<GroupEntry, Functions> fieldFunctions, List<JoinEntry> joinEntries, List<TableConditionEntry> conditionEntries, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws IllegalArgumentException, ResponseException {
		QueryBuilder qb = new QueryBuilder();

		qb.select()
			.tableColumnsWithFunctions(tableColumnEntries, fieldFunctions)
			.from(tableColumnEntries.get(0).getTableName().getTableName())
			.joinsWithOns(joinEntries)
			.whereTableConditions(conditionEntries)
			.group(groupEntries)
			.orders(orderEntries)
			.limit(limit)
			.offset(offset);
		
		return qb.build();
	}
	
	private static String buildUpdateQuery(TableNames tableName, List<String> fieldNames, List<TableConditionEntry> conditionEntries, List<String> returnEntries) throws IllegalArgumentException {
		
		if(fieldNames == null || fieldNames.isEmpty() || conditionEntries == null || conditionEntries.isEmpty()) {
			throw new IllegalArgumentException("No fields are provided!");
		}
		
		QueryBuilder qb = new QueryBuilder();
		
		qb.update(tableName.getTableName())
			.fieldsWithFillers(fieldNames)
			.whereConditions(conditionEntries.get(0).getTableName(), conditionEntries.get(0).getColumnContitions())
			.returnings(returnEntries);
		
		return qb.build();
	}
	
	private static String buildDeleteQuery(List<TableConditionEntry> conditionEntries, List<String> returnEntries) throws IllegalArgumentException {
		
		if(conditionEntries == null || conditionEntries.isEmpty()) {
			throw new IllegalArgumentException("No condition fields are provided!");
		}
		
		TableNames tableName = conditionEntries.get(0).getTableName();
		
		QueryBuilder qb = new QueryBuilder();
		
		qb.delete(tableName.getTableName())
			.whereConditions(tableName, conditionEntries.get(0).getColumnContitions())
			.returnings(returnEntries);
		
		return qb.build();
	}
	
	protected static String buildQuery(QueryData query) throws IllegalArgumentException, ResponseException {
		switch(query.getOperation()) {
			case CREATE:
				return buildInsertQuery(query.getTableColumnEntries().get(0).getTableName(), query.getTableColumnEntries().get(0).getColumnNames(), query.getFieldValues(), query.getReturnEntries());
			case READ:
				return buildSelectQuery(query.getTableColumnEntries(), query.getFieldFunctions(), query.getJoinEntries(), query.getConditionEntries(), query.getGroupEntries(), query.getOrderEntries(), query.getLimit(), query.getOffset());
			case UPDATE:
				return buildUpdateQuery(query.getTableColumnEntries().get(0).getTableName(), query.getTableColumnEntries().get(0).getColumnNames(), query.getConditionEntries(), query.getReturnEntries());
			case DELETE:
				return buildDeleteQuery(query.getConditionEntries(), query.getReturnEntries());
			default:
				throw new ResponseException(StatusCodes.UNPROCESSABLE_CONTENT, "Undefined operation!");
		}
	}
}
