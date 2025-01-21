package com.saran.tms.controllers;

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
}
