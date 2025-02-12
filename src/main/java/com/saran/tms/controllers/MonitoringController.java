package com.saran.tms.controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import com.saran.tms.annotations.Route;
import com.saran.tms.annotations.RouteGroup;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.enums.UserRoles;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.MonitoringService;

// This will be changed to /api/v1 after UI devlopment
@RouteGroup(path="/admin/v1")
public class MonitoringController implements Controller {
	
	
	
	@Route(path="/admin/app/stats", method="GET", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER, UserRoles.USER})
	public ResponseData getStats(RequestData request) throws ResponseException {
		
		JSONObject appStats = MonitoringService.retriveAppStats(request.getQueryParams());
		
		JSONObject jsonData = new JSONObject();
		
		jsonData.put("data", appStats);
		jsonData.put("message", "Data retrived successfully");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	
	@Route(path="/admin/app/object_refs", method="GET", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER, UserRoles.USER})
	public ResponseData getAvailableObjectReferences(RequestData request) throws ResponseException {
		
		JSONArray availableObjRefs = MonitoringService.getAvailableObjectReferences();
		
		JSONObject jsonData = new JSONObject();
		
		jsonData.put("data", availableObjRefs);
		jsonData.put("message", "Available Object references retrived successfully");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/admin/app/objects", method="GET", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER, UserRoles.USER})
	public ResponseData getObjectSize(RequestData request) throws ResponseException {
		
		JSONObject objSizes = MonitoringService.retriveObjectsSize(request.getQueryParams());
		
		JSONObject jsonData = new JSONObject();
		
		jsonData.put("data", objSizes);
		jsonData.put("message", "Object sizes are returned in bytes");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
}
