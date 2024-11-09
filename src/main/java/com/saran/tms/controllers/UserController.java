package com.saran.tms.controllers;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.saran.tms.adapters.JsonModelParser;
import com.saran.tms.adapters.ModelJsonParser;
import com.saran.tms.annotations.Route;
import com.saran.tms.annotations.RouteGroup;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.enums.UserRoles;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.models.Model;
import com.saran.tms.models.UserModel;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.UserService;
import com.saran.tms.validators.PasswordBCrypter;
import com.saran.tms.validators.RSADecryptor;


@RouteGroup(path="/api/v1")
public class UserController implements Controller{
	
	
	@Route(path="/orgs/:org_id/users", method="POST", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN})
	public ResponseData saveUser(RequestData request) throws ResponseException {
		
		JSONObject reqBody = request.getBody();
		Params params = request.getParams();

		UserModel user = (UserModel) JsonModelParser.parse(reqBody, UserModel.class);
		
		Long organizationId = null;
		
		try {
			organizationId = Long.parseLong(params.get("org_id"));	
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.UNPROCESSABLE_CONTENT, "Invalid organization id");
		}
		
		if(organizationId != null) {
			user.setOrganizationId(organizationId);
		}
		
		UserRoles userRole = request.getUserRole();
		
		if(userRole == UserRoles.ORGANIZATION_ADMIN) {
			user.setRole((short) 0);
		}
		
		String encryptedPassword = user.getPassword();
		String privateKeyFilePath = System.getenv("PRIVATE_KEY_PATH");
		String decryptedPassword = RSADecryptor.decrypt(encryptedPassword, privateKeyFilePath);
		String hashedPassword = PasswordBCrypter.encryptPassword(decryptedPassword);
		
		user.setPassword(hashedPassword);	
			
		UserModel newUser = UserService.saveUser(user);
		newUser.setPassword(null);
		
		JSONObject userData = ModelJsonParser.parse(newUser);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", userData);
		jsonData.put("message", "User created successfully");
		
		return new ResponseData(StatusCodes.CREATED, jsonData);
		
	}
	
	@Route(path="/users/:user_id", method="GET")
	public ResponseData findUser(RequestData request) throws ResponseException {
		Params params = request.getParams();
		
		UserModel user = UserService.findUserById(params);
		user.setPassword(null);
		
		JSONObject userData = ModelJsonParser.parse(user);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", userData);
		jsonData.put("message", "User found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
		
	}
	
	@Route(path="/users", method="GET")
	public ResponseData findUsers(RequestData request) throws ResponseException {
		Params params = request.getParams();
		QueryParams queryParams = request.getQueryParams();
		
		List<Model> users = UserService.findUsers(params, queryParams);

		JSONArray usersData = new JSONArray();
		
		for(Model user : users) {
			UserModel userModel = (UserModel) user;
			userModel.setPassword(null);
			JSONObject userData = ModelJsonParser.parse(userModel);
			usersData.put(userData);
		}
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", usersData);
		jsonData.put("message", "Users found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}

	
	@Route(path="/orgs/:org_id/users", method="GET", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN})
	public ResponseData findOrganizationUsers(RequestData request) throws ResponseException {
		Params params = request.getParams();
		QueryParams queryParams = request.getQueryParams();
		
		List<Model> users = UserService.findUsers(params, queryParams);

		JSONArray usersData = new JSONArray();
		
		for(Model user : users) {
			UserModel userModel = (UserModel) user;
			userModel.setPassword(null);
			JSONObject userData = ModelJsonParser.parse(userModel);
			usersData.put(userData);
		}
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", usersData);
		jsonData.put("message", "Users found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs/:org_id/users/:user_id", method="GET", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findOrganizationUser(RequestData request) throws ResponseException {
		
		Params params = request.getParams();
 
		UserModel user = UserService.findUserById(params);
	
		user.setPassword(null);
		
		JSONObject userData = ModelJsonParser.parse(user);
			
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", userData);
		jsonData.put("message", "User found");
		
		return new ResponseData(StatusCodes.OK, jsonData);

	}
	
	@Route(path="/users/:user_id", method="PUT")
	public ResponseData updateUser(RequestData request) throws ResponseException {
		JSONObject reqBody = request.getBody();
		Params params = request.getParams();

		UserModel user = (UserModel) JsonModelParser.parse(reqBody, UserModel.class);
		user.setPassword(null);
		
		UserModel updatedUser = UserService.updateUserById(params, user);
		updatedUser.setPassword(null);
		
		JSONObject userData = ModelJsonParser.parse(updatedUser);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", userData);
		jsonData.put("message", "User updated successfully");
		
		return new ResponseData(StatusCodes.ACCEPTED, jsonData);
		
	}
	

	@Route(path="/orgs/:org_id/users/:user_id", method="PUT", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData updateOrganizationUser(RequestData request) throws ResponseException {
		JSONObject reqBody = request.getBody();
		Params params = request.getParams();

		UserModel user = (UserModel) JsonModelParser.parse(reqBody, UserModel.class);
		user.setPassword(null);
		user.setRole(null);
		
		UserModel updatedUser = UserService.updateUserById(params, user);
		updatedUser.setPassword(null);
		
		JSONObject userData = ModelJsonParser.parse(updatedUser);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", userData);
		jsonData.put("message", "User updated successfully");
		
		return new ResponseData(StatusCodes.ACCEPTED, jsonData);
	}
	
	@Route(path="/orgs/:org_id/users", method="PUT", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN})
	public ResponseData updateUsers(RequestData request) throws ResponseException {
		JSONObject reqBody = request.getBody();
		Params params = request.getParams();
		QueryParams queryParams = request.getQueryParams();
		
		UserModel user = (UserModel) JsonModelParser.parse(reqBody, UserModel.class);
		user.setPassword(null);
		
		List<Model> updatedUsers = UserService.updateUsers(params, queryParams, user);
		
		JSONArray usersData = new JSONArray();
		
		for(Model updatedUser : updatedUsers) {
			UserModel userModel = (UserModel) updatedUser;
			userModel.setPassword(null);
			JSONObject userData = ModelJsonParser.parse(userModel);
			usersData.put(userData);
		}
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", usersData);
		jsonData.put("message", "User updated successfully");
		
		return new ResponseData(StatusCodes.ACCEPTED, jsonData);
		
	}
	
	@Route(path="/orgs/:org_id/users/:user_id", method="DELETE", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN}) 
	public ResponseData deleteUser(RequestData request) throws ResponseException {
		Params params = request.getParams();
		
		UserModel deletedUser = UserService.deleteUserById(params);
		deletedUser.setPassword(null);
		
		JSONObject userData = ModelJsonParser.parse(deletedUser);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", userData);
		jsonData.put("message", "User deleted successfully");
		
		return new ResponseData(StatusCodes.NO_CONTENT, jsonData);
		
	}
}
