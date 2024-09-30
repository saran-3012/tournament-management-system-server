package com.saran.tms.queries;

import java.util.List;
import java.util.Map;


import com.saran.tms.enums.Functions;
import com.saran.tms.enums.TableNames;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.OrderEntry;

@Deprecated
public class QueryFactory {
	
	private static String buildInsertQuery(TableNames tableName, List<String> fieldNames, List<List<Object>> fieldValues, List<String> returnEntries) {
		int n = fieldNames.size();
		int m = fieldValues.size();
		
		if(n == 0 || m == 0) {
			throw new IllegalArgumentException("No fields are provided!");
		}
		
		QueryBuilder qb = new QueryBuilder();
		
		qb.insert(tableName.getTableName())
			.openBracket()
			.fields(fieldNames)
			.closeBracket()
			.qm(m, n)
			.returnings(returnEntries);
		
		return qb.build();
	}
	
	private static String buildSelectQuery(TableNames tableName, List<String> fieldNames, Map<String, Functions> fieldFunctions, List<JoinEntry> joinEntries, List<ConditionEntry> whereConditionEntries, List<String> groupEntries, List<ConditionEntry> havingConditionEntries, Map<String, Functions> conditionFunctions, List<OrderEntry> orderEntries, Integer limit, Integer offset) {
		int n = fieldNames.size();
		
		QueryBuilder qb = new QueryBuilder();
		
		qb.select(tableName.getTableName())
			.fieldsWithFunctions(fieldNames, fieldFunctions)
			.from(tableName.getTableName())
			.whereConditions(whereConditionEntries)
			.group(groupEntries)
			.havingConditions(havingConditionEntries, conditionFunctions)
			.orders(orderEntries)
			.limit(limit)
			.offset(offset);
		
		return qb.build();
	}
	
	private static String buildUpdateQuery(TableNames tableName, List<String> fieldNames, List<ConditionEntry> conditionEntries) {
		int n = fieldNames.size();
		int m = conditionEntries.size();
		
		if(n == 0 || m == 0) {
			throw new IllegalArgumentException("No fields are provided!");
		}
		
		QueryBuilder qb = new QueryBuilder();
		
		qb.update(tableName.getTableName())
			.fieldsWithFillers(fieldNames)
			.whereConditions(conditionEntries);
		
		return qb.build();
	}
	
	private static String buildDeleteQuery(TableNames tableName, List<ConditionEntry> conditionEntries) {
		int n = conditionEntries.size();
		
		if(n == 0) {
			throw new IllegalArgumentException("No condition fields are provided!");
		}
		
		QueryBuilder qb = new QueryBuilder();
		
		qb.delete(tableName.getTableName())
			.whereConditions(conditionEntries);
		
		return qb.build();
	}
	
	public static String buildQuery(QueryData query) {
		switch(query.getOperation()) {
			case CREATE:
				return buildInsertQuery(query.getTableName(), query.getFieldNames(), query.getFieldValues(), query.getReturnEntries());
			case READ:
				return buildSelectQuery(query.getTableName(), query.getFieldNames(), query.getFieldFunctions(), query.getJoinEntries(), query.getWhereConditionEntries(), query.getGroupEntries(), query.getHavingConditionEntries(), query.getConditionFunctions(), query.getOrderEntries(), query.getLimit(), query.getOffset());
			case UPDATE:
				return buildUpdateQuery(query.getTableName(), query.getFieldNames(), query.getWhereConditionEntries());
			case DELETE:
				return buildDeleteQuery(query.getTableName(), query.getWhereConditionEntries());
			default:
				return null;
		}
	}
}
