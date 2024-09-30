package com.saran.tms.controllers;


import java.util.Map;

import org.json.JSONObject;

import com.saran.tms.adapters.JsonModelParser;
import com.saran.tms.adapters.ModelJsonParser;
import com.saran.tms.annotations.ParentRoute;
import com.saran.tms.annotations.Route;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.models.UserModel;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.UserService;
import com.saran.tms.validators.ModelValidator;
import com.saran.tms.validators.PasswordBCrypter;

@ParentRoute(path="/auth")
public class AuthenticationController implements Controller {

	@Route(path="/login", method="POST")
	public ResponseData login(RequestData request) throws ResponseException {
		 
		JSONObject reqBody = request.getBody();
			
		String userEmail = reqBody.getString("email");
		String userPassword = reqBody.getString("password");
		
		
		UserModel user = UserService.getUserByEmail(Map.ofEntries(Map.entry("email", userEmail)));
		
		if(user == null) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "User not found");
		}
		
		String hashedPassword = user.getPassword();
		user.setPassword(null);
		
		boolean isMatches = PasswordBCrypter.verifyPassword(userPassword, hashedPassword);
			
		if(!isMatches) {
			throw new ResponseException(StatusCodes.UNAUTHORIZED, "User credentials are invalid");
		}
		
		JSONObject userData = ModelJsonParser.parse(user);
			
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", userData);
		jsonData.put("message", "User logged in successfully");
		
		return new ResponseData(StatusCodes.OK, jsonData);

	}
	
	@Route(path="/register", method="POST")
	public ResponseData register(RequestData request) throws ResponseException {
		
		JSONObject reqBody = request.getBody();
		
		UserModel user = (UserModel) JsonModelParser.parse(reqBody, UserModel.class);
		
		if(!ModelValidator.validateModel(user)) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Provided data is not valid or malformed");
		}
		
		String hashedPassword = PasswordBCrypter.encryptPassword(user.getPassword());
		user.setPassword(hashedPassword);
			
		UserModel newUser = UserService.createUser(user);
		newUser.setPassword(null);
			
		JSONObject userData = ModelJsonParser.parse(newUser);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", userData);
		jsonData.put("message", "User registered successfully");
		
		return new ResponseData(StatusCodes.CREATED, jsonData);
	}
}
