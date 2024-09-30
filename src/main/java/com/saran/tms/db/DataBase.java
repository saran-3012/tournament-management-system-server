package com.saran.tms.db;


import java.util.List;
import java.util.Map;

import com.saran.tms.enums.Functions;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.models.Model;
import com.saran.tms.pojo.GroupEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.OrderEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;

public abstract class DataBase {
	
	public abstract int save(Model model) throws ResponseException;
	public abstract int saveAll(List<Model> modelList) throws ResponseException;
	public abstract Map<String, Map<String, Object>> saveAndReturn(Model model, List<String> returnEntries) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> saveAllAndReturn(List<Model> modelList, List<String> returnEntries) throws ResponseException;
	public abstract Map<String, Map<String, Object>> findOneById(TableColumnEntry requiredFields, Long id) throws ResponseException;
	public abstract Map<String, Map<String, Object>> findOne(TableColumnEntry requiredFields, TableConditionEntry requiredContitions) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> findAll(TableColumnEntry requiredFields, TableConditionEntry requiredContitions) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> findAll(TableColumnEntry requiredFields, TableConditionEntry requiredContitions, Integer limit, Integer offset) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> findAll(TableColumnEntry requiredFields, TableConditionEntry requiredContitions, Map<String, Functions> fieldFunctions, Integer limit, Integer offset) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> findAll(TableColumnEntry requiredFields, TableConditionEntry requiredContitions, Map<String, Functions> fieldFunctions, List<GroupEntry> groupEntries, Integer limit, Integer offset) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> findAll(TableColumnEntry requiredFields, TableConditionEntry requiredContitions, Map<String, Functions> fieldFunctions, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> findAll(TableColumnEntry requiredFields, TableConditionEntry requiredContitions, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> findAll(TableColumnEntry requiredFields, TableConditionEntry requiredContitions, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException;
	public abstract Map<String, Map<String, Object>> findOneWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> findAllWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> findAllWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, Integer limit, Integer offset) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> findAllWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, Map<String, Functions> fieldFunctions, Integer limit, Integer offset) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> findAllWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, Map<String, Functions> fieldFunctions, List<GroupEntry> groupEntries, Integer limit, Integer offset) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> findAllWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, Map<String, Functions> fieldFunctions, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> findAllWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, List<GroupEntry> groupEntries, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> findAllWithJoin(List<TableColumnEntry> requiredTableFields, List<JoinEntry> joinEntries, List<TableConditionEntry> requiredTableConditions, List<OrderEntry> orderEntries, Integer limit, Integer offset) throws ResponseException;
	public abstract int update(Model model, TableConditionEntry requiredContitions) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> updateAndReturn(Model model, TableConditionEntry requiredContitions, List<String> returnEntries) throws ResponseException;
	public abstract int delete(TableConditionEntry requiredContitions) throws ResponseException;
	public abstract List<Map<String, Map<String, Object>>> deleteAndReturn(TableConditionEntry requiredContitions, List<String> returnEntries) throws ResponseException;

}
