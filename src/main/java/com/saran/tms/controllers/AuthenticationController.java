package com.saran.tms.controllers;


import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.saran.tms.adapters.JsonModelParser;
import com.saran.tms.adapters.ModelJsonParser;
import com.saran.tms.annotations.Route;
import com.saran.tms.annotations.RouteGroup;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.enums.UserRoles;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.models.OrganizationModel;
import com.saran.tms.models.UserModel;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.OrganizationService;
import com.saran.tms.services.UserService;
import com.saran.tms.validators.ModelValidator;
import com.saran.tms.validators.PasswordBCrypter;
import com.saran.tms.validators.RSADecryptor;

@RouteGroup(path="/auth")
public class AuthenticationController implements Controller {
	
	@Route(path="/checkin", method="POST", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData checkin(RequestData request) throws ResponseException {
		
		Cookie jsessionCookie = request.getCookie("JSESSIONID");
		
		if(jsessionCookie == null) {
			throw new ResponseException(StatusCodes.PRECONDITION_REQUIRED, "Login to continue");
		}
		
		HttpSession session = request.getSession(false);
		if(session == null) {
			throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Session Expired, Login again");
		}
		
		Long requestUserId = request.getUserId();
		UserRoles userRole = request.getUserRole();
		
		UserModel user = UserService.findUserById(Map.ofEntries(Map.entry("user_id", requestUserId.toString())));
		
		if(user == null || user.getRole() != userRole.getRolePriority()) {
			session.invalidate();
			throw new ResponseException(StatusCodes.NOT_FOUND, "Invalid session, Login again");
		}
		
		user.setPassword(null);
		
		JSONObject userData = ModelJsonParser.parse(user);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", userData);
		jsonData.put("id", session.getId());
		jsonData.put("message", "User logged in successfully");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}

	@Route(path="/login", method="POST", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER, UserRoles.USER})
	public ResponseData login(RequestData request) throws ResponseException {
		 
		JSONObject reqBody = request.getBody();
			
		String userEmail = reqBody.getString("email").toLowerCase();
		
		String encryptedPassword = reqBody.getString("password");
		String privateKeyFilePath = System.getenv("PRIVATE_KEY_PATH");
		String decryptedPassword = RSADecryptor.decrypt(encryptedPassword, privateKeyFilePath);
		
		
		UserModel user = null;
		
		try {
			user = UserService.findUserByEmail(Map.ofEntries(Map.entry("email", userEmail)));
		}
		catch(Exception e) {}
		
		if(user == null) {
			throw new ResponseException(StatusCodes.UNAUTHORIZED, "User credentials are invalid");
		}
		
		String hashedPassword = user.getPassword();
		user.setPassword(null);
		
		boolean isMatches = PasswordBCrypter.verifyPassword(decryptedPassword, hashedPassword);
			
		if(!isMatches) {
			throw new ResponseException(StatusCodes.UNAUTHORIZED, "User credentials are invalid");
		}
		
		HttpSession session = request.getSession(true);
		
		Map<String, String> headers = request.getHeaders();
			
		session.setAttribute("user-agent", headers.get("user-agent"));
		session.setAttribute("sec-ch-ua", headers.get("sec-ch-ua"));
		session.setAttribute("sec-ch-ua-platform", headers.get("sec-ch-ua-platform"));

		Long userId = (Long) user.getUserId();
		Short userRole = (Short) user.getRole();
		Long organizationId = (Long) user.getOrganizationId();
		
		session.setAttribute("userId", userId);
		session.setAttribute("userRole", UserRoles.getUserRole(userRole));
		session.setAttribute("organizationId", organizationId);
		session.setMaxInactiveInterval(86400);
		
		Cookie jsessionCookie = new Cookie("JSESSIONID", session.getId());  
		jsessionCookie.setMaxAge(86400);  
		jsessionCookie.setHttpOnly(true);
		jsessionCookie.setPath("/tms"); 
			
		JSONObject userData = ModelJsonParser.parse(user);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", userData);
		jsonData.put("message", "User logged in successfully");
		
		return new ResponseData(StatusCodes.OK, jsonData).addCookie(jsessionCookie);

	}
	
	@Route(path="/register", method="POST", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.USER})
	public ResponseData register(RequestData request) throws ResponseException {
		
		Long requestUserId = request.getUserId();
		UserRoles userRole = request.getUserRole();
		JSONObject reqBody = request.getBody();
		
		OrganizationModel org = (OrganizationModel) JsonModelParser.parse(reqBody.optJSONObject("orgData"), OrganizationModel.class);
		if(org != null) {
			org.setOrganizationId(null);
		}
		UserModel user = (UserModel) JsonModelParser.parse(reqBody.getJSONObject("userData"), UserModel.class);
		
		String userEmail = user.getEmail();
		if(userEmail != null) {
			user.setEmail(userEmail.toLowerCase());
		}
		
		Short role = null;
		Long organizationId = null;
		
		switch(userRole) {
			case USER:
				org.setOrganizationStatus(null);
				org = OrganizationService.saveOrganization(org);
				organizationId = org.getOrganizationId();
				role = 1;
				break;
				
			case ORGANIZATION_ADMIN:
				role = 0;
				UserModel orgAdmin = UserService.findUserById(Map.ofEntries(Map.entry("user_id", requestUserId.toString())));
				organizationId = orgAdmin.getOrganizationId();
				break;
				
			case APP_ADMIN:
				org.setOrganizationStatus((short) 1);
				System.out.println("Admin org");
				org = OrganizationService.saveOrganization(org);
				role = 1;
				break;
				
			default:
				throw new ResponseException(StatusCodes.FORBIDDEN, "You are not allowed to perform this operation");
		}
		
		if(organizationId == null) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Organization is not specified properly");
		}
		
//		Decrypt the encrypted password to normal text
		String encryptedPassword = user.getPassword();
		String privateKeyFilePath = System.getenv("PRIVATE_KEY_PATH");
		String decryptedPassword = RSADecryptor.decrypt(encryptedPassword, privateKeyFilePath);

//		Hash the decrypted password
		String hashedPassword = PasswordBCrypter.encryptPassword(decryptedPassword);
		
		user.setOrganizationId(organizationId);
		user.setRole(role);
		user.setPassword(hashedPassword);	
		
		if(!ModelValidator.validateModel(user)) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Provided data is not valid or malformed");
		}
		
		UserModel newUser = UserService.saveUser(user);
		newUser.setPassword(null);
			
		JSONObject userData = ModelJsonParser.parse(newUser);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", userData);
		jsonData.put("message", "User registered successfully");
		
		return new ResponseData(StatusCodes.CREATED, jsonData);
	}
	
	@Route(path="/logout", method="POST", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER, UserRoles.USER})
	public ResponseData logout(RequestData request) {
		
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		
		Cookie jsessionCookie = new Cookie("JSESSIONID", null);  
		jsessionCookie.setMaxAge(0);  
		jsessionCookie.setHttpOnly(true);
		jsessionCookie.setPath("/tms"); 

		return new ResponseData(StatusCodes.NO_CONTENT, null).addCookie(jsessionCookie);
	}
	
	@Route(path="/change-password", method="POST", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData changePassword(RequestData request) throws ResponseException {
		Long requestUserId = request.getUserId();
		UserRoles userRole = request.getUserRole();
		JSONObject reqBody = request.getBody();
		
		Long userId = reqBody.optLong("userId");

		if(userId == null) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "User id not provided");
		}
		
		UserModel user = UserService.findUserById(Map.ofEntries(Map.entry("user_id", userId.toString())));
		
		String newPassword = null;
		
		String privateKeyFilePath = System.getenv("PRIVATE_KEY_PATH");
		
		switch(userRole) {
			case ORGANIZATION_MEMBER:
				if(requestUserId != userId) {
					throw new ResponseException(StatusCodes.FORBIDDEN, "You are not allowed to perform this operation");
				}
				String hashedPassword = user.getPassword();
				String oldPassword = reqBody.optString("oldPassword");
				if(oldPassword == null) {
					throw new ResponseException(StatusCodes.UNAUTHORIZED, "Old password not provided");
				}
				
				String decryptedOldPassword = RSADecryptor.decrypt(oldPassword, privateKeyFilePath);
				
				boolean isMatches = PasswordBCrypter.verifyPassword(decryptedOldPassword, hashedPassword);
				if(!isMatches) {
					throw new ResponseException(StatusCodes.UNAUTHORIZED, "Invalid credentials");
				}
				newPassword = reqBody.optString("newPassword");
				break;
				
			case ORGANIZATION_ADMIN:
				UserModel orgAdmin = UserService.findUserById(Map.ofEntries(Map.entry("user_id", requestUserId.toString())));
				if(orgAdmin.getOrganizationId() != user.getOrganizationId()) {
					throw new ResponseException(StatusCodes.FORBIDDEN, "You are not allowed to perform this operation");
				}
				newPassword = reqBody.optString("password");
				break;
				
			case APP_ADMIN:
				newPassword = reqBody.optString("password");
				break;
				
			default:
				throw new ResponseException(StatusCodes.FORBIDDEN, "You are not allowed to perform this operation");
		}

		if(newPassword == null) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "New Password is not provided");
		}
		
		String decryptedNewPassword = RSADecryptor.decrypt(newPassword, privateKeyFilePath);
		
		
		String hashedPassword = PasswordBCrypter.encryptPassword(decryptedNewPassword);
		
		UserModel passwordUpdatedUser = new UserModel();
		
		passwordUpdatedUser.setPassword(hashedPassword);
		
		passwordUpdatedUser = UserService.updateUserById(Map.ofEntries(Map.entry("user_id", userId.toString())), passwordUpdatedUser);
		
		passwordUpdatedUser.setPassword(null);
		
		JSONObject userData = ModelJsonParser.parse(passwordUpdatedUser);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", userData);
		jsonData.put("message", "User password changed successfully");
		
		return new ResponseData(StatusCodes.ACCEPTED, jsonData);
	}
}
