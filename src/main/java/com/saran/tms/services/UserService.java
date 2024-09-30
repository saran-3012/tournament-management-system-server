package com.saran.tms.services;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.saran.tms.dao.Dao;
import com.saran.tms.enums.Operators;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.models.Model;
import com.saran.tms.models.UserModel;
import com.saran.tms.pojo.ConditionEntry;

public class UserService {
	
	public static UserModel createUser(UserModel user) throws ResponseException {
		Dao udao = new Dao(UserModel.class);
		UserModel newUser = (UserModel) udao.saveAndReturn(user, Arrays.asList("*"));
		newUser.setPassword(null);
		return newUser;
	}
	
	public static UserModel getUserById(Map<String, String> params) throws ResponseException{
		Dao udao = new Dao(UserModel.class);
		UserModel user = (UserModel) udao.findOne(
				Arrays.asList("*"), 
				Arrays.asList(
						new ConditionEntry(null, "user_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("user_id")))
				)
			);
		return user;
	}
	
	public static UserModel getUserByEmail(Map<String, String> params) throws ResponseException{
		Dao udao = new Dao(UserModel.class);
		UserModel user = (UserModel) udao.findOne(
				Arrays.asList("*"), 
				Arrays.asList(
						new ConditionEntry(null, "email", Arrays.asList(Operators.EQUAL), params.get("email").toString())
						)
				);
		return user;
	}
	
	
	public static List<Model> getOrganizationUsers(Map<String, String> params) throws ResponseException {
		Dao udao = new Dao(UserModel.class);
		List<Model>  users = udao.findAll(
				Arrays.asList("*"), 
				Arrays.asList(
						new ConditionEntry(null, "organization_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("org_id")))
				)
			);
		return users;
	}
}
