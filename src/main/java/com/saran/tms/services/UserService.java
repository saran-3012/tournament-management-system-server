package com.saran.tms.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.saran.tms.dao.Dao;
import com.saran.tms.enums.Operators;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.models.Model;
import com.saran.tms.models.UserModel;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.utils.Utilities;

public class UserService {
	
	public static UserModel saveUser(UserModel user) throws ResponseException {
		Dao userDao = new Dao(UserModel.class);
		UserModel newUser = (UserModel) userDao.saveAndReturn(user, Arrays.asList("*"));
		return newUser;
	}
	
	public static UserModel findUserById(Params params) throws ResponseException{
		Dao userDao = new Dao(UserModel.class);
		Long userId;
		try {
			userId = params.getLong("user_id");
			if(userId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "User id is null");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid user id");
		}
		UserModel user = (UserModel) userDao.findOne(
				Arrays.asList("*"), 
				Arrays.asList(
						new ConditionEntry(null, "user_id", Arrays.asList(Operators.EQUAL), userId)
				)
			);
		if(user == null) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "User not found");
		}
		return user;
	}
	
	public static UserModel findUserByEmail(Params params) throws ResponseException{
		Dao userDao = new Dao(UserModel.class);
		UserModel user = (UserModel) userDao.findOne(
				Arrays.asList("*"), 
				Arrays.asList(
						new ConditionEntry(null, "email", Arrays.asList(Operators.EQUAL), params.get("email"))
						)
				);
		if(user == null) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "User not found");
		}
		return user;
	}
	
	
	
	public static List<Model> findUsers(Params params, QueryParams queryParams) throws ResponseException {
		Dao userDao = new Dao(UserModel.class);
		
		List<ConditionEntry> conditions = new ArrayList<>();
		
		Operators operator = null;

		try {
			Long organizationId = params.getLong("org_id");
			if(organizationId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Organization id is not provided");
			}
			conditions.add(new ConditionEntry(Arrays.asList(operator), "organization_id", Arrays.asList(Operators.EQUAL), organizationId));
			operator = Operators.AND;
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid organization id");
		}
		
		String userName = queryParams.get("filter_username");
		if(userName != null) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "user_name", Arrays.asList(Operators.ILIKE), '%' + userName + '%'));
			operator = Operators.AND;
		}
		
		try {
			Short role = queryParams.getShort("filter_role");
			if(role != null) {
				conditions.add(new ConditionEntry(Arrays.asList(operator), "role", Arrays.asList(Operators.EQUAL), role));
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid User role");
		}
		
		Integer limit;
		Integer page;
		
		try {
			limit = (int) Utilities.nullFallback(queryParams.getInt("limit"), 20);
			if(limit < 0) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Limit cannot be negative");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid limit value");
		}
		
		try {
			page = (int) Utilities.nullFallback(queryParams.getInt("page"), 0);
			if(page < 0) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Page cannot be negative");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid page value");
		}
		
		Integer offset = limit * page;
		
		Boolean excludeLimit = queryParams.getBoolean("exclude_limit");
		if(excludeLimit != null && excludeLimit) {
			limit = null;
			offset = null;
		}
		
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


	public static UserModel updateUserById(Params params, UserModel user) throws ResponseException {
		Dao userDao = new Dao(UserModel.class);
		Long userId;
		try {
			userId = params.getLong("user_id");
			if(userId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "User id is null");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid user id");
		}
		List<Model> updatedUsers = userDao.updateAndReturn(
										user, 
										Arrays.asList(	
											new ConditionEntry(null, "user_id", Arrays.asList(Operators.EQUAL), userId)
										), 
										Arrays.asList("*")
									);
		if(updatedUsers == null || updatedUsers.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "User not found");
		}
		return (UserModel) updatedUsers.get(0);
	}
	
	public static List<Model> updateUsers(Params params, QueryParams queryParams, UserModel user) throws ResponseException {
		Dao userDao = new Dao(UserModel.class);
		List<ConditionEntry> conditions = new ArrayList<>();
		
		Operators operator = null;
		
		try {
			Long organizationId = params.getLong("org_id");
			if(organizationId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Organization id is not provided");
			}
			conditions.add(new ConditionEntry(Arrays.asList(operator), "organization_id", Arrays.asList(Operators.EQUAL), organizationId));
			operator = Operators.AND;
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid organization id");
		}
		
		
		String userName = queryParams.get("filter_username");
		if(userName != null) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "user_name", Arrays.asList(Operators.ILIKE), userName));
			operator = Operators.AND;
		}
		
		Short role;
		try {
			role = queryParams.getShort("filter_role");
			if(role != null) {
				conditions.add(new ConditionEntry(Arrays.asList(operator), "role", Arrays.asList(Operators.EQUAL), role));
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid user role");
		}
		
		List<Model> updatedUsers = userDao.updateAndReturn(user, conditions, Arrays.asList("*"));
		if(updatedUsers == null || updatedUsers.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "User not found");
		}
		return updatedUsers;
	}
	
	public static UserModel deleteUserById(Params params) throws ResponseException {
		Dao userDao = new Dao(UserModel.class);
		Long userId;
		try {
			userId = params.getLong("user_id");
			if(userId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "User id is null");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid user id");
		}
		List<Model> deletedUsers = userDao.deleteAndReturn(
										Arrays.asList(
											new ConditionEntry(null, "user_id", Arrays.asList(Operators.EQUAL), userId)
										), 
										Arrays.asList("*")
									);
		if(deletedUsers == null || deletedUsers.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "User not found");
		}
		return (UserModel) deletedUsers.get(0);
	}
}
