package com.saran.tms.queries;

import java.util.List;
import java.util.Map;

import com.saran.tms.enums.CRUD;
import com.saran.tms.enums.Functions;
import com.saran.tms.enums.TableNames;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.OrderEntry;

@Deprecated
public class QueryData {
	private TableNames tableName;
	private CRUD operation;
	private List<String> fieldNames;
	private List<List<Object>> fieldValues;
	private List<JoinEntry> joinEntries;
	private List<ConditionEntry> whereConditionEntries;
	private List<String> groupEntries;
	private List<ConditionEntry> havingConditionEntries;
	private List<OrderEntry> orderEntries;
	private List<String> returnEntries;
	
	private Map<String, Functions> fieldFunctions;
	private Map<String, Functions> conditionFunctions;
	
	private Integer limit;
	private Integer offset;
	
	public QueryData() {}

	public QueryData(TableNames tableName, CRUD operation, List<String> fieldNames, List<List<Object>> fieldValues,
			List<JoinEntry> joinEntries, List<ConditionEntry> whereConditionEntries, List<String> groupEntries,
			List<ConditionEntry> havingConditionEntries, List<OrderEntry> orderEntries, List<String> returnEntries,
			Map<String, Functions> fieldFunctions, Map<String, Functions> conditionFunctions, Integer limit, Integer offset) {
		this.tableName = tableName;
		this.operation = operation;
		this.fieldNames = fieldNames;
		this.fieldValues = fieldValues;
		this.joinEntries = joinEntries;
		this.whereConditionEntries = whereConditionEntries;
		this.groupEntries = groupEntries;
		this.havingConditionEntries = havingConditionEntries;
		this.orderEntries = orderEntries;
		this.returnEntries = returnEntries;
		this.fieldFunctions = fieldFunctions;
		this.conditionFunctions = conditionFunctions;
		this.limit = limit;
		this.offset = offset;
	}
	
	public TableNames getTableName() {
		return tableName;
	}
	public void setTableName(TableNames tableName) {
		this.tableName = tableName;
	}
	
	public CRUD getOperation() {
		return operation;
	}
	public void setOperation(CRUD operation) {
		this.operation = operation;
	}
	
	public List<String> getFieldNames() {
		return fieldNames;
	}
	public void setFieldNames(List<String> fieldNames) {
		this.fieldNames = fieldNames;
	}
	
	public List<List<Object>> getFieldValues() {
		return fieldValues;
	}
	public void setFieldValues(List<List<Object>> fieldValues) {
		this.fieldValues = fieldValues;
	}
	
	public List<JoinEntry> getJoinEntries() {
		return joinEntries;
	}
	public void setJoinEntries(List<JoinEntry> joinEntries) {
		this.joinEntries = joinEntries;
	}
	
	public List<ConditionEntry> getWhereConditionEntries() {
		return whereConditionEntries;
	}
	public void setWhereConditionEntries(List<ConditionEntry> whereConditionEntries) {
		this.whereConditionEntries = whereConditionEntries;
	}
	
	public List<String> getGroupEntries() {
		return groupEntries;
	}
	public void setGroupEntries(List<String> groupEntries) {
		this.groupEntries = groupEntries;
	}
	
	public List<ConditionEntry> getHavingConditionEntries() {
		return havingConditionEntries;
	}
	public void setHavingConditionEntries(List<ConditionEntry> havingConditionEntries) {
		this.havingConditionEntries = havingConditionEntries;
	}
	
	public List<OrderEntry> getOrderEntries() {
		return orderEntries;
	}
	public void setOrderEntries(List<OrderEntry> orderEntries) {
		this.orderEntries = orderEntries;
	}
	
	public List<String> getReturnEntries() {
		return returnEntries;
	}
	public void setReturnEntries(List<String> returnEntries) {
		this.returnEntries = returnEntries;
	}
	
	public Map<String, Functions> getFieldFunctions() {
		return fieldFunctions;
	}
	public void setFieldFunctions(Map<String, Functions> fieldFunctions) {
		this.fieldFunctions = fieldFunctions;
	}
	
	public Map<String, Functions> getConditionFunctions() {
		return conditionFunctions;
	}
	public void setConditionFunctions(Map<String, Functions> conditionFunctions) {
		this.conditionFunctions = conditionFunctions;
	}

	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
}
