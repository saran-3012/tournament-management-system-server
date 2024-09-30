package com.saran.tms.queries;

import java.util.List;
import java.util.Map;

import com.saran.tms.enums.Functions;
import com.saran.tms.enums.JoinTypes;
import com.saran.tms.enums.Operators;
import com.saran.tms.enums.SortOrder;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.OrderEntry;

@Deprecated
public class QueryBuilder {

	private StringBuilder queryBuilder;
	
	public QueryBuilder() {
		queryBuilder = new StringBuilder("");
	}
	
	public QueryBuilder insert(String tableName) {
		queryBuilder.append("INSERT INTO ").append(tableName).append(" ");
		return this;
	}
	
	public QueryBuilder update(String tableName) {
		queryBuilder.append("UPDATE ").append(tableName).append(" SET ");
		return this;
	}
	
	public QueryBuilder delete(String tableName) {
		queryBuilder.append("DELETE FROM ").append(tableName).append(" ");
		return this;
	}
	
	public QueryBuilder select(String columnName) {
		queryBuilder.append("SELECT ").append(columnName).append(" ");
		return this;
	}
	
	public QueryBuilder openBracket() {
		queryBuilder.append("( ");
		return this;
	}
	
	public QueryBuilder openBracket(String attribute) {
		queryBuilder.append("( ").append(attribute).append(" ");
		return this;
	}
	
	public QueryBuilder closeBracket() {
		queryBuilder.append(") ");
		return this;
	}
	
	public QueryBuilder values() {
		queryBuilder.append("VALUES ");
		return this;
	}
	
	public QueryBuilder returning() {
		queryBuilder.append("RETURNING ");
		return this;
	}
	
	public QueryBuilder returning(String returnEntry) {
		return this.returning().field(returnEntry);
	}
	
	public QueryBuilder returnings(List<String> returnEntries) {
		if(returnEntries == null || returnEntries.isEmpty()) {
			return this;
		}
		
		return this.returning().fields(returnEntries);
	}
	
	public QueryBuilder as(String attribute, String value) {
		queryBuilder.append(attribute).append(" AS ").append(value).append(" ");
		return this;
	}
	
	public QueryBuilder from() {
		queryBuilder.append("FROM ");
		return this;
	}
	
	public QueryBuilder from(String tableName) {
		queryBuilder.append("FROM ").append(tableName).append(" ");
		return this;
	}

	public QueryBuilder field(String attribute) {
		queryBuilder.append(attribute).append(" ");
		return this;
	}
	
	public QueryBuilder fields(List<String> fieldNames) {
		
		if(fieldNames == null || fieldNames.isEmpty()) {
			return this;
		}
		
		int n = fieldNames.size();
		
		this.field(fieldNames.get(0));
		
		for(int i=1; i<n; i++) {
			this.comma(fieldNames.get(i));
		}
		
		return this;
	}
	
	public QueryBuilder fieldWithFunction(String attribute, Functions function) {
		if(function == null) {
			return this.field(attribute);
		}
		return this.function(attribute, function.getFunction());
	}
	
	public QueryBuilder fieldsWithFunctions(List<String> fieldNames, Map<String, Functions> fieldFunctions) {
		
		
		if(fieldNames == null || fieldNames.isEmpty()) {
			return this;
		}

		if(fieldFunctions == null) {
			return this.fields(fieldNames);
		}

		int n = fieldNames.size();
		
		String fieldName = fieldNames.get(0);
		this.fieldWithFunction(fieldName, fieldFunctions.get(fieldName));
		
		for(int i=1; i<n; i++) {
			fieldName = fieldNames.get(i);
			this.fieldWithFunction(fieldName, fieldFunctions.get(fieldName));
		}
		
		return this;
	}
	
	public QueryBuilder fieldWithFiller(String attribute) {
		return this.equal(attribute, "?");
	}
	
	public QueryBuilder fieldsWithFillers(List<String> fieldNames) {
		
		if(fieldNames == null || fieldNames.isEmpty()) {
			return this;
		}

		int n = fieldNames.size();
		
		this.fieldWithFiller(fieldNames.get(0));
		
		for(int i=1; i<n; i++) {
			this.fieldWithFiller(fieldNames.get(i));
		}
		
		return this;
	}
	
	public QueryBuilder condition(String columnName, List<Operators> prefixOperators, List<Operators> suffixOperators) {
		if(prefixOperators != null) {
			for(Operators operator : prefixOperators) {
				this.field(operator.getOperator());
			}
		}
		
		this.field(columnName);
		
		if(suffixOperators != null) {
			for(Operators operator : suffixOperators) {
				this.field(operator.getOperator());
			}
		}
		
		this.qm();
		
		return this;
	}
	
	public QueryBuilder conditions(List<ConditionEntry> conditionEntries) {
		
		if(conditionEntries == null || conditionEntries.isEmpty()) {
			return this;
		}
		
		for(ConditionEntry conditionEntry : conditionEntries) {
			this.condition(conditionEntry.getColumnName(), conditionEntry.getPrefixOperators(), conditionEntry.getSuffixOperators());
		}
		
		return this;
	}
	
	public QueryBuilder funtionCondition(String columnName, List<Operators> prefixOperators, List<Operators> suffixOperators, Functions function) {
		if(function == null) {
			return this.condition(columnName, prefixOperators, suffixOperators);
		}
		
		if(prefixOperators != null) {
			for(Operators operator : prefixOperators) {
				this.field(operator.getOperator());
			}
		}
		
		this.function(columnName, function.getFunction());
		
		if(suffixOperators != null) {
			for(Operators operator : suffixOperators) {
				this.field(operator.getOperator());
			}
		}
		
		this.qm();
		
		return this;
	}
	
	public QueryBuilder functionConditions(List<ConditionEntry> conditionEntries, Map<String, Functions> conditionFunctions) {
		if(conditionEntries == null || conditionEntries.isEmpty()) {
			return this;
		}
		
		for(ConditionEntry conditionEntry : conditionEntries) {
			this.funtionCondition(conditionEntry.getColumnName(), conditionEntry.getPrefixOperators(), conditionEntry.getSuffixOperators(), conditionFunctions.get(conditionEntry.getColumnName()));
		}
		
		return this;
	}
	
	public QueryBuilder where() {
		queryBuilder.append("WHERE ");
		return this;
	}
	
	public QueryBuilder whereCondition(ConditionEntry conditionEntry) {
		this.where();
		
		this.condition(conditionEntry.getColumnName(), conditionEntry.getPrefixOperators(), conditionEntry.getSuffixOperators());
		
		return this;
	}
	
	public QueryBuilder whereConditions(List<ConditionEntry> conditionEntries) {
		
		if(conditionEntries == null || conditionEntries.isEmpty()) {
			return this;
		}
		
		this.where();
		
		return this.conditions(conditionEntries);
	}
	
	public QueryBuilder having() {
		queryBuilder.append("HAVING ");
		return this;
	}
	
	public QueryBuilder havingCondition(ConditionEntry conditionEntry, Functions function) {
		this.having();
		
		this.funtionCondition(conditionEntry.getColumnName(), conditionEntry.getPrefixOperators(), conditionEntry.getSuffixOperators(), function);
		
		return this;
	}
	
	public QueryBuilder havingConditions(List<ConditionEntry> conditionEntries, Map<String, Functions> conditionFunctions) {
		if(conditionEntries == null) {
			return this;
		}
		
		this.having();
		
		if(conditionFunctions == null) {
			return this.conditions(conditionEntries);
		}
		
		return this.functionConditions(conditionEntries, conditionFunctions);
	}
	
	
	public QueryBuilder comma() {
		queryBuilder.append(", ");
		return this;
	}
	
	public QueryBuilder comma(String attribute) {
		queryBuilder.append(", ").append(attribute).append(" ");
		return this;
	}
	
	public QueryBuilder and() {
		queryBuilder.append("AND ");
		return this;
	}
	
	public QueryBuilder or() {
		queryBuilder.append("OR ");
		return this;
	}
	
	public QueryBuilder not() {
		queryBuilder.append("NOT ");
		return this;
	}
	
	public QueryBuilder equal(String attribute, String value) {
		queryBuilder.append(attribute).append(" = ").append(value).append(" ");
		return this;
	}
	
	public QueryBuilder equal() {
		queryBuilder.append("= ");
		return this;
	}
	
	public QueryBuilder notEqual(String attribute, String value) {
		queryBuilder.append(attribute).append(" != ").append(value).append(" ");
		return this;
	}
	
	public QueryBuilder notEqual() {
		queryBuilder.append("!= ");
		return this;
	}
	
	public QueryBuilder gt(String attribute, String value) {
		queryBuilder.append(attribute).append(" > ").append(value).append(" ");
		return this;
	}
	
	public QueryBuilder gt() {
		queryBuilder.append("> ");
		return this;
	}
	
	public QueryBuilder ge(String attribute, String value) {
		queryBuilder.append(attribute).append(" >= ").append(value).append(" ");
		return this;
	}
	
	public QueryBuilder ge() {
		queryBuilder.append(">= ");
		return this;
	}
	
	public QueryBuilder lt(String attribute, String value) {
		queryBuilder.append(attribute).append(" < ").append(value).append(" ");
		return this;
	}
	
	public QueryBuilder lt() {
		queryBuilder.append("< ");
		return this;
	}
	
	public QueryBuilder le(String attribute, String value) {
		queryBuilder.append(attribute).append(" <= ").append(value).append(" ");
		return this;
	}
	
	public QueryBuilder le() {
		queryBuilder.append("<= ");
		return this;
	}
	
	public QueryBuilder qm() {
		queryBuilder.append("? ");
		return this;
	}
	
	public QueryBuilder qm(int repeatitions) {
		if(repeatitions <= 0) {
			return this;
		}
		this.openBracket("?");
		for(int i=1; i<repeatitions; i++) {
			this.comma().qm();
		}
		this.closeBracket();
		return this;
	}
	
	public QueryBuilder qm(int totalInserts,int repeatitions) {
		if(totalInserts <= 0) {
			return this;
		}
		this.qm(repeatitions);
		for(int i=0; i<totalInserts; i++) {
			this.comma().qm(repeatitions);
		}
		return this;
	}
	
	public QueryBuilder like(String attribute, String value) {
		queryBuilder.append(attribute).append(" LIKE ").append(value).append(" ");
		return this;
	}
	
	public QueryBuilder like() {
		queryBuilder.append("LIKE ");
		return this;
	}
	
	public QueryBuilder ilike(String attribute, String value) {
		queryBuilder.append(attribute).append(" ILIKE ").append(value).append(" ");
		return this;
	}
	
	public QueryBuilder ilike() {
		queryBuilder.append("ILIKE ");
		return this;
	}
	
	public QueryBuilder join(String tableName) {
		queryBuilder.append("JOIN ").append(tableName).append(" ");
		return this;
	}
	
	public QueryBuilder leftJoin(String tableName) {
		queryBuilder.append("LEFT JOIN ").append(tableName).append(" ");
		return this;
	}
	
	public QueryBuilder rightJoin(String tableName) {
		queryBuilder.append("RIGHT JOIN ").append(tableName).append(" ");
		return this;
	}
	
	public QueryBuilder outerJoin(String tableName) {
		queryBuilder.append("OUTER JOIN ").append(tableName).append(" ");
		return this;
	}
	
	public QueryBuilder on(String columnName1, String columnName2) {
		queryBuilder.append(columnName1).append(" ON ").append(columnName2).append(" ");
		return this;
	}
	
	public QueryBuilder joinWithOn(String tableName1, String tableName2, String columnName1, String columnName2, JoinTypes joinType) {
		switch(joinType) {
			case JOIN:
				return this.join(tableName2).on(tableName1 + "." + columnName1, tableName2 + "." + columnName2);
			case LEFT_JOIN:
				return this.leftJoin(tableName2).on(tableName1 + "." + columnName1, tableName2 + "." + columnName2);
			case RIGHT_JOIN:
				return this.rightJoin(tableName2).on(tableName1 + "." + columnName1, tableName2 + "." + columnName2);
			case OUTER_JOIN:
				return this.outerJoin(tableName2).on(tableName1 + "." + columnName1, tableName2 + "." + columnName2);
			default:
				return this;
		}
	}
	
	public QueryBuilder count(String columnName) {
		queryBuilder.append("COUNT( ").append(columnName).append(") ");
		return this;
	}
	
	public QueryBuilder sum(String columnName) {
		queryBuilder.append("SUM( ").append(columnName).append(") ");
		return this;
	}
	
	public QueryBuilder avg(String columnName) {
		queryBuilder.append("AVG( ").append(columnName).append(") ");
		return this;
	}
	
	public QueryBuilder min(String columnName) {
		queryBuilder.append("MIN( ").append(columnName).append(") ");
		return this;
	}
	
	public QueryBuilder max(String columnName) {
		queryBuilder.append("MAX( ").append(columnName).append(") ");
		return this;
	}
	
	public QueryBuilder function(String columnName, String functionName) {
		queryBuilder.append(functionName).append("( ").append(columnName).append(") ");
		return this;
	}
	
	public QueryBuilder group() {
		queryBuilder.append("GROUP BY ");
		return this;
	}
	
	public QueryBuilder group(String columnName) {
		queryBuilder.append("GROUP BY ").append(columnName).append(" ");
		return this;
	} 
	
	public QueryBuilder group(List<String> groupEntries) {
		if(groupEntries == null || groupEntries.isEmpty()) {
			return this;
		}
		
		return this.group().fields(groupEntries);
	}
	
	public QueryBuilder order() {
		queryBuilder.append("ORDER BY ");
		return this;
	}
	
	public QueryBuilder order(String columnName) {
		queryBuilder.append("ORDER BY ").append(columnName).append(" ");
		return this;
	}
	
	public QueryBuilder order(String columnName, String order) {
		queryBuilder.append("ORDER BY ").append(columnName).append(" ").append(order).append(" ");
		return this;
	}
	
	public QueryBuilder order(String columnName, SortOrder sortOrder) {
		return this.order(columnName).sortOrder(sortOrder);
	}
	
	public QueryBuilder order(OrderEntry orderEntry) {
		return this.order(orderEntry.getColumnName(), orderEntry.getSortOrder());
	}
	
	public QueryBuilder sortOrder(SortOrder sortOrder) {
		switch(sortOrder) {
			case DESC:
				return this.desc();
			case ASC:
			default:
				return this.asc();
		}
	}
	
	public QueryBuilder sortOrder(String columnName, SortOrder sortOrder) {
		switch(sortOrder) {
			case DESC:
				return this.desc(columnName);
			case ASC:
			default:
				return this.asc(columnName);
		}
	}
	
	public QueryBuilder sortOrder(OrderEntry orderEntry) {
		return this.sortOrder(orderEntry.getColumnName(), orderEntry.getSortOrder());
	}
	
	public QueryBuilder asc() {
		queryBuilder.append(" ASC ");
		return this;
	}
	
	public QueryBuilder asc(String columnName) {
		queryBuilder.append(columnName).append(" ASC ");
		return this;
	}
	
	public QueryBuilder desc() {
		queryBuilder.append(" DESC ");
		return this;
	}
	
	public QueryBuilder desc(String columnName) {
		queryBuilder.append(columnName).append(" DESC ");
		return this;
	}
	
	public QueryBuilder orders(List<OrderEntry> orderEntries) {
		if(orderEntries == null || orderEntries.isEmpty()) {
			return this;
		}
		
		int n = orderEntries.size();
		
		this.order(orderEntries.get(0));
		
		for(int i=1; i<n; i++) {
			this.comma().sortOrder(orderEntries.get(i));
		}
		
		return this;
	}
	
	public QueryBuilder limit() {
		queryBuilder.append("LIMIT ");
		return this;
	}
	
	public QueryBuilder limit(Integer limit) {
		if(limit == null) {
			return this;
		}
		
		return this.limit().qm();
	}
	
	public QueryBuilder offset() {
		queryBuilder.append("OFFSET ");
		return this;
	}
	
	public QueryBuilder offset(Integer offset) {
		if(offset == null) {
			return this;
		}
		
		return this.offset().qm();
	}
	
	public String build() {
		return queryBuilder.toString();
	}
}