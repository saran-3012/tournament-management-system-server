package com.saran.tms.postgresql;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.saran.tms.enums.Functions;
import com.saran.tms.enums.JoinTypes;
import com.saran.tms.enums.Operators;
import com.saran.tms.enums.SortOrder;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.enums.TableNames;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.GroupEntry;
import com.saran.tms.pojo.JoinConditionEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.OrderEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;
import com.saran.tms.validators.EntityValidator;

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
	
	public QueryBuilder select() {
		queryBuilder.append("SELECT ");
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
		queryBuilder.append("FROM ").append(tableName).append(' ');
		return this;
	}

	public QueryBuilder field(String attribute) {
		queryBuilder.append(attribute).append(' ');
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
	
	public QueryBuilder tableField(TableNames tableName, String attribute) throws ResponseException {
		if(!EntityValidator.validateEntity(tableName, attribute)) {
			throw new ResponseException(StatusCodes.UNPROCESSABLE_CONTENT, "Field does not exists for this entity");
		}
		queryBuilder.append(tableName.getTableName()).append('.').append(attribute).append(' ');
		return this;
	}
	
	public QueryBuilder tableColumnFields(TableNames tableName, List<String> fieldNames) throws ResponseException {
		if(fieldNames == null || fieldNames.isEmpty()) {
			return this;
		}
		
		int n = fieldNames.size();
		
		this.tableField(tableName, fieldNames.get(0));
		
		for(int i=1; i<n; i++) {
			this.comma();
			this.tableField(tableName, fieldNames.get(i));
		}
		
		return this;
	}
	
	public QueryBuilder tableColumnFields(TableColumnEntry tableColumnEntry) throws ResponseException {
		if(tableColumnEntry == null) {
			return this;
		}
		
		return this.tableColumnFields(tableColumnEntry.getTableName(), tableColumnEntry.getColumnNames());
	}
	
	public QueryBuilder tableFields(List<TableColumnEntry> tableColumnEntries) throws ResponseException {
		if(tableColumnEntries == null || tableColumnEntries.isEmpty()) {
			return this;
		}
		
		int n = tableColumnEntries.size();
		
		this.tableColumnFields(tableColumnEntries.get(0));
		
		for(int i=1; i<n; i++) {
			this.comma();
			this.tableColumnFields(tableColumnEntries.get(i));
		}
		
		return this;
	}
	
	public QueryBuilder fieldWithFunction(String attribute, Functions function) {
		if(function == null) {
			return this.field(attribute);
		}
		return this.function(attribute, function.getFunction());
	}
	
	public QueryBuilder fieldsWithFunctions(List<String> fieldNames, Map<GroupEntry, Functions> fieldFunctions) {
		
		
		if(fieldNames == null || fieldNames.isEmpty()) {
			return this;
		}

		if(fieldFunctions == null) {
			return this.fields(fieldNames);
		}

		int n = fieldNames.size();
		
		String fieldName = fieldNames.get(0);
		this.fieldWithFunction(fieldName, fieldFunctions.get(new GroupEntry(null, fieldName)));
		
		for(int i=1; i<n; i++) {
			fieldName = fieldNames.get(i);
			this.comma();
			this.fieldWithFunction(fieldName, fieldFunctions.get(new GroupEntry(null, fieldName)));
		}
		
		return this;
	}
	
	public QueryBuilder tableFieldsWithFunctions(TableNames tableName, List<String> fieldNames, Map<GroupEntry, Functions> fieldFunctions) {
		if(fieldNames == null || fieldNames.isEmpty()) {
			return this;
		}

		if(fieldFunctions == null) {
			return this.fields(fieldNames);
		}

		int n = fieldNames.size();
		
		String fieldName = fieldNames.get(0);
		
		this.fieldWithFunction(tableName.getTableName() + '.' + fieldName, fieldFunctions.get(new GroupEntry(tableName, fieldName)));
		
		for(int i=1; i<n; i++) {
			fieldName = fieldNames.get(i);
			this.comma();
			this.fieldWithFunction(tableName.getTableName() + '.' + fieldName, fieldFunctions.get(new GroupEntry(tableName, fieldName)));
		}
		
		return this;
	}
	
	public QueryBuilder tableFieldsWithFunctions(TableColumnEntry tableColumnEntry, Map<GroupEntry, Functions> fieldFunctions) throws ResponseException {
		
		if(fieldFunctions == null) {
			return this.tableColumnFields(tableColumnEntry);
		}
		
		this.tableFieldsWithFunctions(tableColumnEntry.getTableName(), tableColumnEntry.getColumnNames(), fieldFunctions);
		
		return this;
	}
	
	public QueryBuilder tableColumnsWithFunctions(List<TableColumnEntry> tableColumnEntries, Map<GroupEntry, Functions> fieldFunctions) throws ResponseException {
		
		if(tableColumnEntries == null || tableColumnEntries.isEmpty()) {
			return this;
		}
		
		if(fieldFunctions == null) {
			return this.tableFields(tableColumnEntries);
		}
		
		int n = tableColumnEntries.size();
		
		this.tableFieldsWithFunctions(tableColumnEntries.get(0), fieldFunctions);
		
		for(int i=1; i<n; i++) {
			this.comma();
			this.tableFieldsWithFunctions(tableColumnEntries.get(i), fieldFunctions);
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
			this.comma();
			this.fieldWithFiller(fieldNames.get(i));
		}
		
		return this;
	}
	
	public QueryBuilder condition(TableNames tableName ,String columnName, List<Operators> prefixOperators, List<Operators> suffixOperators) {
		if(prefixOperators != null) {
			for(Operators operator : prefixOperators) {
				if(operator == null) continue;
				this.field(operator.getOperator());
			}
		}
		
		this.field((tableName != null && columnName != null) ? tableName.getTableName() + '.' + columnName : (columnName != null)? columnName : "");
		
		if(suffixOperators != null) {
			for(Operators operator : suffixOperators) {
				if(operator == null) continue;
				this.field(operator.getOperator());
			}
		}
		
		
		if(columnName != null) {
			this.qm();
		}
		return this;
	}
	
	public QueryBuilder conditions(TableNames tableName, List<ConditionEntry> conditionEntries) {
		for(ConditionEntry conditionEntry : conditionEntries) {
			this.condition(tableName ,conditionEntry.getColumnName(), conditionEntry.getPrefixOperators(), conditionEntry.getSuffixOperators());
		}
		return this;
	}
	
	public QueryBuilder tableConditions(List<TableConditionEntry> tableConditionEntries) {
		
		if(tableConditionEntries == null || tableConditionEntries.isEmpty()) {
			return this;
		}
		for(TableConditionEntry tableConditionEntry : tableConditionEntries) {
			this.conditions(tableConditionEntry.getTableName(), tableConditionEntry.getColumnContitions());
		}
		
		return this;
	}
	
	public QueryBuilder where() {
		queryBuilder.append("WHERE ");
		return this;
	}
	
	public QueryBuilder whereCondition(TableNames tableName, ConditionEntry conditionEntry) {
		this.where();
		
		this.condition(tableName, conditionEntry.getColumnName(), conditionEntry.getPrefixOperators(), conditionEntry.getSuffixOperators());
		
		return this;
	}
	
	public QueryBuilder whereConditions(TableNames tableName, List<ConditionEntry> conditionEntries) {
		if(conditionEntries == null || conditionEntries.isEmpty()) {
			return this;
		}
		
		this.where();
		
		return this.conditions(tableName, conditionEntries);
	}
	
	public QueryBuilder whereTableConditions(List<TableConditionEntry> tableConditionEntries) {
		
		if(tableConditionEntries == null || tableConditionEntries.isEmpty()) {
			return this;
		}
		
		boolean canFormQuery = false;
		
		for(TableConditionEntry tableConditionEntry : tableConditionEntries) {
			List<ConditionEntry> columnConditions = tableConditionEntry.getColumnContitions();
			if(columnConditions != null && !columnConditions.isEmpty()) {
				canFormQuery = true;
				break;
			}
		}
		
		if(!canFormQuery) {
			return this;
		}
		
		this.where();
		
		return this.tableConditions(tableConditionEntries);
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
		for(int i=1; i<totalInserts; i++) {
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
	
	public QueryBuilder join(TableNames tableName) {
		queryBuilder.append("JOIN ").append(tableName.getTableName()).append(" ");
		return this;
	}
	
	public QueryBuilder leftJoin(TableNames tableName) {
		queryBuilder.append("LEFT JOIN ").append(tableName.getTableName()).append(" ");
		return this;
	}
	
	public QueryBuilder rightJoin(TableNames tableName) {
		queryBuilder.append("RIGHT JOIN ").append(tableName.getTableName()).append(" ");
		return this;
	}
	
	public QueryBuilder outerJoin(TableNames tableName) {
		queryBuilder.append("OUTER JOIN ").append(tableName.getTableName()).append(" ");
		return this;
	}
	
	public QueryBuilder on(TableNames tableName1, TableNames tableName2, String columnName1, String columnName2) {
		queryBuilder.append("ON ").append(tableName1.getTableName()).append('.').append(columnName1).append(" = ").append(tableName2.getTableName()).append('.').append(columnName2).append(" ");
		return this;
	}
	
	public QueryBuilder joinWithOn(TableNames tableName1, TableNames tableName2, String columnName1, String columnName2, JoinTypes joinType) {
		switch(joinType) {
			case JOIN:
				return this.join(tableName2).on(tableName1, tableName2, columnName1, columnName2);
			case LEFT_JOIN:
				return this.leftJoin(tableName2).on(tableName1, tableName2, columnName1, columnName2);
			case RIGHT_JOIN:
				return this.rightJoin(tableName2).on(tableName1, tableName2, columnName1, columnName2);
			case OUTER_JOIN:
				return this.outerJoin(tableName2).on(tableName1, tableName2, columnName1, columnName2);
			default:
				return this;
		}
	}
	
	public QueryBuilder joinsWithOns(List<JoinEntry> joinEntries) {
		if(joinEntries == null || joinEntries.isEmpty()) {
			return this;
		}
		
		for(JoinEntry joinEntry : joinEntries) {
			switch(joinEntry.getType()) {
				case 0:
					this.joinWithOn(joinEntry.getTableName1(), joinEntry.getTableName2(), joinEntry.getColumnName1(), joinEntry.getColumnName2(), joinEntry.getJoinType());
					break;
				case 1:
					JoinConditionEntry jce = (JoinConditionEntry) joinEntry;
					this.condition(jce.getConditionTableName(), jce.getConditionColumnName(), Arrays.asList(jce.getPrefixOperator()), Arrays.asList(jce.getSuffixOperator()));
					break;
				default:
			}
		}
		
		return this;
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
	
	public QueryBuilder group(TableNames tableName, String columnName) {
		queryBuilder.append("GROUP BY ").append(tableName.getTableName()).append('.').append(columnName).append(" ");
		return this;
	}
	
	public QueryBuilder group(GroupEntry groupEntry) {
		return this.group(groupEntry.getTableName(), groupEntry.getColumnName());
	} 
	
	public QueryBuilder group(List<GroupEntry> groupEntries) {
		if(groupEntries == null || groupEntries.isEmpty()) {
			return this;
		}
		
		int n = groupEntries.size();
		
		this.group(groupEntries.get(0));
		
		for(int i=1; i<n; i++) {
			this.comma(groupEntries.get(i).getTableName().getTableName() + '.' + groupEntries.get(i).getColumnName());
		}
		
		
		return this;
	}
	
	public QueryBuilder order() {
		queryBuilder.append("ORDER BY ");
		return this;
	}
	
	public QueryBuilder order(String columnName) {
		queryBuilder.append("ORDER BY ").append(columnName).append(" ");
		return this;
	}
	
	public QueryBuilder order(TableNames tableName, String columnName) {
		queryBuilder.append("ORDER BY ").append(tableName.getTableName()).append('.').append(columnName).append(" ");
		return this;
	}
	
	public QueryBuilder order(TableNames tableName, String columnName, SortOrder sortOrder) {
		return this.order(tableName, columnName).sortOrder(sortOrder);
	}
	
	public QueryBuilder order(OrderEntry orderEntry) {
		return this.order(orderEntry.getTableName(), orderEntry.getColumnName(), orderEntry.getSortOrder());
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
	
	public QueryBuilder sortOrder(TableNames tableName, String columnName, SortOrder sortOrder) {
		switch(sortOrder) {
			case DESC:
				return this.desc(tableName.getTableName() + '.' + columnName);
			case ASC:
			default:
				return this.asc(tableName.getTableName() + '.' + columnName);
		}
	}
	
	public QueryBuilder sortOrder(OrderEntry orderEntry) {
		return this.sortOrder(orderEntry.getTableName() ,orderEntry.getColumnName(), orderEntry.getSortOrder());
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
