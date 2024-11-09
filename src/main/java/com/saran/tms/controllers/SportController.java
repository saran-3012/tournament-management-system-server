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
import com.saran.tms.models.SportModel;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.SportService;

@RouteGroup(path="/api/v1")
public class SportController implements Controller {
	
	@Route(path="/sports", method="POST")
	public ResponseData saveSport(RequestData request) throws ResponseException {
		
		JSONObject reqBody = request.getBody();

		SportModel sport = (SportModel) JsonModelParser.parse(reqBody, SportModel.class);
		
		SportModel newSport = SportService.saveSport(sport);
		
		JSONObject sportData = ModelJsonParser.parse(newSport);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", sportData);
		jsonData.put("message", "Sport created successfully");
		
		return new ResponseData(StatusCodes.CREATED, jsonData);
	}
	
	@Route(path="/sports/:sport_id", method="GET", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findSport(RequestData request) throws ResponseException {
		Params params = request.getParams();
		
		SportModel sport = SportService.findSportById(params);
		
		JSONObject sportData = ModelJsonParser.parse(sport);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", sportData);
		jsonData.put("message", "Sport found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/sports", method="GET", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findSports(RequestData request) throws ResponseException {
		Params params = request.getParams();
		QueryParams queryParams = request.getQueryParams();
		
		List<Model> sports = SportService.findSports(params, queryParams);
		
		JSONArray sportsData = new JSONArray();
		
		for(Model sport : sports) {
			SportModel sportModel = (SportModel) sport;
			JSONObject sportData = ModelJsonParser.parse(sportModel);
			sportsData.put(sportData);
		}
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", sportsData);
		jsonData.put("message", "Sports found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/sports/:sport_id", method="PUT")
	public ResponseData updateSport(RequestData request) throws ResponseException {
		JSONObject reqBody = request.getBody();
		Params params = request.getParams();

		SportModel sport = (SportModel) JsonModelParser.parse(reqBody, SportModel.class);
		
		sport.setSportId(null);
		
		SportModel updatedSport = SportService.updateSportById(params, sport);
		
		JSONObject sportData = ModelJsonParser.parse(updatedSport);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", sportData);
		jsonData.put("message", "Sport updated successfully");
		
		return new ResponseData(StatusCodes.ACCEPTED, jsonData);
	}
	
	@Route(path="/sports/:sport_id", method="DELETE")
	public ResponseData deleteOrganization(RequestData request) throws ResponseException {
		Params params = request.getParams();
		
		SportModel deletedSport = SportService.deleteSportById(params);
		
		JSONObject sportData = ModelJsonParser.parse(deletedSport);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", sportData);
		jsonData.put("message", "Sport deleted successfully");
		
		return new ResponseData(StatusCodes.NO_CONTENT, jsonData);
	}
}
