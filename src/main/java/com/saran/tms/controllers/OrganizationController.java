package com.saran.tms.controllers;

import java.util.List;
import java.util.Map;

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
import com.saran.tms.models.OrganizationModel;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.OrganizationService;

@RouteGroup(path="/api/v1")
public class OrganizationController implements Controller {
	
	@Route(path="/orgs", method="POST")
	public ResponseData saveOrganization(RequestData request) throws ResponseException {
		
		JSONObject reqBody = request.getBody();

		OrganizationModel org = (OrganizationModel) JsonModelParser.parse(reqBody, OrganizationModel.class);
		
		OrganizationModel newOrg = OrganizationService.saveOrganization(org);
		
		JSONObject orgData = ModelJsonParser.parse(newOrg);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", orgData);
		jsonData.put("message", "Organization created successfully");
		
		return new ResponseData(StatusCodes.CREATED, jsonData);
	}
	
	@Route(path="/orgs/:org_id", method="GET", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findOrganization(RequestData request) throws ResponseException {
		Map<String, String> params = request.getParams();
		
		List<Model> orgDetails = OrganizationService.findOrganizationById(params);
		
		JSONObject orgData = ModelJsonParser.parseAndMerge(orgDetails);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", orgData);
		jsonData.put("message", "Organization found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs", method="GET")
	public ResponseData findOrganizations(RequestData request) throws ResponseException {
		Map<String, String> params = request.getParams();
		Map<String, String[]> queryParams = request.getQueryParams();
		
		List<List<Model>> orgDetailsList = OrganizationService.findOrganizations(params, queryParams);
		
		JSONArray orgsData = new JSONArray();
		
		for(List<Model> orgDetails : orgDetailsList) {
			JSONObject orgData = ModelJsonParser.parseAndMerge(orgDetails);
			orgsData.put(orgData);
		}
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", orgsData);
		jsonData.put("message", "Organizations found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs/:org_id", method="PUT", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN})
	public ResponseData updateOrganization(RequestData request) throws ResponseException {
		JSONObject reqBody = request.getBody();
		Map<String, String> params = request.getParams();

		OrganizationModel org = (OrganizationModel) JsonModelParser.parse(reqBody, OrganizationModel.class);
		
		org.setOrganizationCreatedAt(null);
		
		if(request.getUserRole() != UserRoles.APP_ADMIN) {
			org.setOrganizationStatus(null);
		}
		
		OrganizationModel updatedOrg = OrganizationService.updateOrganizationById(params, org);
		
		JSONObject orgData = ModelJsonParser.parse(updatedOrg);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", orgData);
		jsonData.put("message", "Organization updated successfully");
		
		return new ResponseData(StatusCodes.ACCEPTED, jsonData);
	}
	
	@Route(path="/orgs/:org_id", method="DELETE")
	public ResponseData deleteOrganization(RequestData request) throws ResponseException {
		Map<String, String> params = request.getParams();
		
		OrganizationModel deletedOrg = OrganizationService.deleteOrganizationById(params);
		
		JSONObject orgData = ModelJsonParser.parse(deletedOrg);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", orgData);
		jsonData.put("message", "Organization deleted successfully");
		
		return new ResponseData(StatusCodes.NO_CONTENT, jsonData);
	}
}
