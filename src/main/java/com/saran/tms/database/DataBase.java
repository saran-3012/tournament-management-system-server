package com.saran.tms.database;

import java.util.List;
import java.util.Map;

import com.saran.tms.enums.TableNames;
import com.saran.tms.exceptions.DataBaseException;
import com.saran.tms.models.Model;


public interface DataBase {
	public int save(Model model) throws DataBaseException;
	public int saveAll(List<Model> modelList) throws DataBaseException;
	
	public Map<String, Map<String, Object>> saveAndReturn(Model model, List<String> returnEntries) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> saveAllAndReturn(List<Model> modelList, List<String> returnEntries) throws DataBaseException;
	
	public List<Map<String, Map<String, Object>>> findAll(List<ColumnEntry> requiredFields, TableNames tableName) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> findAll(List<ColumnEntry> requiredFields, TableNames tableName, ConditionEntry requiredConditions) throws DataBaseException;
	public <T> List<Map<String, Map<String, Object>>> findAll(List<ColumnEntry> requiredFields, TableNames tableName, List<T> groupEntries) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> findAll(List<ColumnEntry> requiredFields, TableNames tableName, RangeEntry rangeEntry) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> findAll(List<ColumnEntry> requiredFields, TableNames tableName, ConditionEntry requiredConditions, List<QueryEntry> groupEntries) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> findAll(List<ColumnEntry> requiredFields, TableNames tableName, ConditionEntry requiredConditions, RangeEntry rangeEntry) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> findAll(List<ColumnEntry> requiredFields, TableNames tableName, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries) throws DataBaseException;
	public <T> List<Map<String, Map<String, Object>>> findAll(List<ColumnEntry> requiredFields, TableNames tableName, List<T> groupEntries, RangeEntry rangeEntry) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> findAll(List<ColumnEntry> requiredFields, TableNames tableName, ConditionEntry requiredConditions, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries) throws DataBaseException;
	public <T> List<Map<String, Map<String, Object>>> findAll(List<ColumnEntry> requiredFields, TableNames tableName, ConditionEntry requiredConditions, List<T> groupEntries, RangeEntry rangeEntry) throws DataBaseException;
	public <T> List<Map<String, Map<String, Object>>> findAll(List<ColumnEntry> requiredFields, TableNames tableName, List<T> groupEntries, List<OrderEntry> orderEntries, RangeEntry rangeEntry) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> findAll(List<ColumnEntry> requiredFields, TableNames tableName, ConditionEntry requiredConditions, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries, RangeEntry rangeEntry) throws DataBaseException;

	public List<Map<String, Map<String, Object>>> findAllWithJoin(List<ColumnEntry> requiredFields, TableNames tableName, List<JoinEntry> joinEntries) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> findAllWithJoin(List<ColumnEntry> requiredFields, TableNames tableName, List<JoinEntry> joinEntries, ConditionEntry requiredConditions) throws DataBaseException;
	public <T> List<Map<String, Map<String, Object>>> findAllWithJoin(List<ColumnEntry> requiredFields, TableNames tableName, List<JoinEntry> joinEntries, List<T> groupEntries) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> findAllWithJoin(List<ColumnEntry> requiredFields, TableNames tableName, List<JoinEntry> joinEntries, RangeEntry rangeEntry) throws DataBaseException;
	public <T> List<Map<String, Map<String, Object>>> findAllWithJoin(List<ColumnEntry> requiredFields, TableNames tableName, List<JoinEntry> joinEntries, ConditionEntry requiredConditions, List<T> groupEntries) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> findAllWithJoin(List<ColumnEntry> requiredFields, TableNames tableName, List<JoinEntry> joinEntries, ConditionEntry requiredConditions, RangeEntry rangeEntry) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> findAllWithJoin(List<ColumnEntry> requiredFields, TableNames tableName, List<JoinEntry> joinEntries, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries) throws DataBaseException;
	public <T> List<Map<String, Map<String, Object>>> findAllWithJoin(List<ColumnEntry> requiredFields, TableNames tableName, List<JoinEntry> joinEntries, List<T> groupEntries, RangeEntry rangeEntry) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> findAllWithJoin(List<ColumnEntry> requiredFields, TableNames tableName, List<JoinEntry> joinEntries, ConditionEntry requiredConditions, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries) throws DataBaseException;
	public <T> List<Map<String, Map<String, Object>>> findAllWithJoin(List<ColumnEntry> requiredFields, TableNames tableName, List<JoinEntry> joinEntries, ConditionEntry requiredConditions, List<T> groupEntries, RangeEntry rangeEntry) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> findAllWithJoin(List<ColumnEntry> requiredFields, TableNames tableName, List<JoinEntry> joinEntries, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries, RangeEntry rangeEntry) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> findAllWithJoin(List<ColumnEntry> requiredFields, TableNames tableName, List<JoinEntry> joinEntries, ConditionEntry requiredConditions, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries, RangeEntry rangeEntry) throws DataBaseException;

	public int update(Model model, ConditionEntry requiredConditions) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> updateAndReturn(Model model, ConditionEntry requiredConditions, List<String> returnEntries) throws DataBaseException;
	
	public int delete(ConditionEntry requiredConditions) throws DataBaseException;
	public List<Map<String, Map<String, Object>>> deleteAndReturn(ConditionEntry requiredConditions, List<String> returnEntries) throws DataBaseException;
}
