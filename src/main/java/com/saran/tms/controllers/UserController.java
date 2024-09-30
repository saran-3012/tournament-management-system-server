package com.saran.tms.controllers;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.saran.tms.adapters.JsonModelParser;
import com.saran.tms.adapters.ModelJsonParser;
import com.saran.tms.annotations.ParentRoute;
import com.saran.tms.annotations.Route;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.models.Model;
import com.saran.tms.models.UserModel;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.UserService;


@ParentRoute(path="/api/v1")
public class UserController implements Controller{
	
	
	@Route(path="/orgs/:org_id/users", method="POST")
	public ResponseData createUser(RequestData request) throws ResponseException {
		
		JSONObject reqBody = request.getBody();
		Map<String, String> params = request.getParams();
		

		UserModel user = (UserModel) JsonModelParser.parse(reqBody, UserModel.class);
		
		Long organizationId = Long.parseLong(params.get("org_id"));
		
		if(organizationId != null) {
			user.setOrganizationId(organizationId);
		}
			
		UserModel newUser = UserService.createUser(user);
			
		JSONObject userData = ModelJsonParser.parse(newUser);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", userData);
		jsonData.put("message", "User created successfully");
		
		return new ResponseData(StatusCodes.CREATED, jsonData);
		
	}
	
	@Route(path="/users/:user_id", method="GET")
	public ResponseData getUser(RequestData request) throws ResponseException {
		Map<String, String> params = request.getParams();
		 
		UserModel user = UserService.getUserById(params);
			
		JSONObject userData = ModelJsonParser.parse(user);
			
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", userData);
		jsonData.put("message", "User found");
		
		return new ResponseData(StatusCodes.OK, jsonData);

	}

	
	@Route(path="/orgs/:org_id/users", method="GET")
	public ResponseData getOrganizationUsers(RequestData request) throws ResponseException {
		Map<String, String> params = request.getParams();
		
		List<Model> users = UserService.getOrganizationUsers(params);
		
		JSONArray usersData = new JSONArray();
		
		for(Model user : users) {
			JSONObject userData = ModelJsonParser.parse(user);
			usersData.put(userData);
		}
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", usersData);
		jsonData.put("message", "Users found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs/:org_id/users/:user_id", method="GET")
	public ResponseData getOrganizationUser(RequestData request) throws ResponseException {
		
		Map<String, String> params = request.getParams();
 
		UserModel user = UserService.getUserById(params);
			
		JSONObject userData = ModelJsonParser.parse(user);
			
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", userData);
		jsonData.put("message", "User found");
		
		return new ResponseData(StatusCodes.OK, jsonData);

	}
	
	
}
