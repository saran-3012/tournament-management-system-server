package com.saran.tms.postgresql;

import java.util.List;
import java.util.Map;

import com.saran.tms.enums.CRUD;
import com.saran.tms.enums.Functions;
import com.saran.tms.pojo.GroupEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.OrderEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;

public class QueryData {
	
	private CRUD operation;
	private List<TableColumnEntry> tableColumnEntries;
	private List<List<Object>> fieldValues;
	private List<JoinEntry> joinEntries;
	private List<TableConditionEntry> conditionEntries;
	private List<GroupEntry> groupEntries;
	private List<OrderEntry> orderEntries;
	private List<String> returnEntries;
	
	private Map<GroupEntry, Functions> fieldFunctions;
	
	private Integer limit;
	private Integer offset;
	
	public QueryData() {}

	public QueryData(CRUD operation, List<TableColumnEntry> tableColumnEntries, List<List<Object>> fieldValues,
			List<JoinEntry> joinEntries, List<TableConditionEntry> conditionEntries, List<GroupEntry> groupEntries,
			List<OrderEntry> orderEntries, List<String> returnEntries, Map<GroupEntry, Functions> fieldFunctions,
			Integer limit, Integer offset) {
		super();
		this.operation = operation;
		this.tableColumnEntries = tableColumnEntries;
		this.fieldValues = fieldValues;
		this.joinEntries = joinEntries;
		this.conditionEntries = conditionEntries;
		this.groupEntries = groupEntries;
		this.orderEntries = orderEntries;
		this.returnEntries = returnEntries;
		this.fieldFunctions = fieldFunctions;
		this.limit = limit;
		this.offset = offset;
	}

	public CRUD getOperation() {
		return operation;
	}

	public void setOperation(CRUD operation) {
		this.operation = operation;
	}

	public List<TableColumnEntry> getTableColumnEntries() {
		return tableColumnEntries;
	}

	public void setTableColumnEntries(List<TableColumnEntry> tableColumnEntries) {
		this.tableColumnEntries = tableColumnEntries;
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

	public List<TableConditionEntry> getConditionEntries() {
		return conditionEntries;
	}

	public void setConditionEntries(List<TableConditionEntry> conditionEntries) {
		this.conditionEntries = conditionEntries;
	}

	public List<GroupEntry> getGroupEntries() {
		return groupEntries;
	}

	public void setGroupEntries(List<GroupEntry> groupEntries) {
		this.groupEntries = groupEntries;
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

	public Map<GroupEntry, Functions> getFieldFunctions() {
		return fieldFunctions;
	}

	public void setFieldFunctions(Map<GroupEntry, Functions> fieldFunctions) {
		this.fieldFunctions = fieldFunctions;
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
