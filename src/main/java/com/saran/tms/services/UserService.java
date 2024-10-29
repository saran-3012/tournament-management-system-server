package com.saran.tms.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.saran.tms.dao.Dao;
import com.saran.tms.enums.Operators;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.models.Model;
import com.saran.tms.models.UserModel;
import com.saran.tms.pojo.ConditionEntry;

public class UserService {
	
	public static UserModel saveUser(UserModel user) throws ResponseException {
		Dao userDao = new Dao(UserModel.class);
		UserModel newUser = (UserModel) userDao.saveAndReturn(user, Arrays.asList("*"));
		return newUser;
	}
	
	public static UserModel findUserById(Map<String, String> params) throws ResponseException{
		Dao userDao = new Dao(UserModel.class);
		UserModel user = (UserModel) userDao.findOne(
				Arrays.asList("*"), 
				Arrays.asList(
						new ConditionEntry(null, "user_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("user_id")))
				)
			);
		if(user == null) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "User not found");
		}
		return user;
	}
	
	public static UserModel findUserByEmail(Map<String, String> params) throws ResponseException{
		Dao userDao = new Dao(UserModel.class);
		UserModel user = (UserModel) userDao.findOne(
				Arrays.asList("*"), 
				Arrays.asList(
						new ConditionEntry(null, "email", Arrays.asList(Operators.EQUAL), params.get("email").toString())
						)
				);
		if(user == null) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "User not found");
		}
		return user;
	}
	
	
	
	public static List<Model> findUsers(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		Dao userDao = new Dao(UserModel.class);
		
		List<ConditionEntry> conditions = new ArrayList<>();
		
		Operators operator = null;
		
		String organizationId = params.get("org_id");
		if(organizationId != null) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "organization_id", Arrays.asList(Operators.EQUAL), Long.parseLong(organizationId)));
			operator = Operators.AND;
		}
		
		
		String userNames[] = queryParams.get("filter_username");
		if(userNames != null && userNames.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "user_name", Arrays.asList(Operators.ILIKE), '%' + userNames[0] + '%'));
			operator = Operators.AND;
		}
		
		String roles[] = queryParams.get("filter_role");
		if(roles != null && roles.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "role", Arrays.asList(Operators.EQUAL), Short.parseShort(roles[0])));
		}
		
		Integer limit = 20;
		Integer page = 0;
		
		String limits[] = queryParams.get("limit");
		String pages[] = queryParams.get("page");
		
		if(limits != null && limits.length > 0) {
			limit = Integer.parseInt(limits[0]);
		}
		
		if(pages != null && pages.length > 0) {
			page = Integer.parseInt(pages[0]);
		}
		
		Integer offset = limit * page;
		
		List<Model>  users = userDao.findAll(
				Arrays.asList("*"), 
				conditions,
				limit,
				offset
			);
		if(users == null || users.isEmpty()) {
			users = new ArrayList<>();
		}
		return users;
	}


	public static UserModel updateUserById(Map<String, String> params, UserModel user) throws ResponseException {
		Dao userDao = new Dao(UserModel.class);
		List<Model> updatedUsers = userDao.updateAndReturn(user, Arrays.asList(new ConditionEntry(null, "user_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("user_id")))), Arrays.asList("*"));
		if(updatedUsers == null || updatedUsers.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "User not found");
		}
		return (UserModel) updatedUsers.get(0);
	}
	
	public static List<Model> updateUsers(Map<String, String> params, Map<String, String[]> queryParams, UserModel user) throws ResponseException {
		Dao userDao = new Dao(UserModel.class);
		List<ConditionEntry> conditions = new ArrayList<>();
		
		Operators operator = null;
		
		String organizationId = params.get("org_id");
		if(organizationId != null) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "organization_id", Arrays.asList(Operators.EQUAL), Long.parseLong(organizationId)));
			operator = Operators.AND;
		}
		
		
		String userNames[] = queryParams.get("filter_username");
		if(userNames != null && userNames.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "user_name", Arrays.asList(Operators.ILIKE), '%' + userNames[0] + '%'));
			operator = Operators.AND;
		}
		
		String roles[] = queryParams.get("filter_role");
		if(roles != null && roles.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "role", Arrays.asList(Operators.EQUAL), Short.parseShort(roles[0])));
		}
		
		List<Model> updatedUsers = userDao.updateAndReturn(user, conditions, Arrays.asList("*"));
		if(updatedUsers == null || updatedUsers.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "User not found");
		}
		return updatedUsers;
	}
	
	public static UserModel deleteUserById(Map<String, String> params) throws ResponseException {
		Dao userDao = new Dao(UserModel.class);
		List<Model> deletedUsers = userDao.deleteAndReturn(Arrays.asList(new ConditionEntry(null, "user_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("user_id")))), Arrays.asList("*"));
		if(deletedUsers == null || deletedUsers.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "User not found");
		}
		return (UserModel) deletedUsers.get(0);
	}
}
