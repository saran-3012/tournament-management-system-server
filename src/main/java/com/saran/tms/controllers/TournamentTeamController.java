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
import com.saran.tms.models.TournamentTeamModel;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.OrganizationService;
import com.saran.tms.services.TournamentTeamService;

@RouteGroup(path="/api/v1")
public class TournamentTeamController {
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams", method="POST", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData saveTeam(RequestData request) throws ResponseException {
		
		JSONObject reqBody = request.getBody();

		TournamentTeamModel team = (TournamentTeamModel) JsonModelParser.parse(reqBody, TournamentTeamModel.class);
		
		TournamentTeamModel newTeam = TournamentTeamService.saveTeam(team);
		
		JSONObject teamData = ModelJsonParser.parse(newTeam);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", teamData);
		jsonData.put("message", "Team created successfully");
		
		return new ResponseData(StatusCodes.CREATED, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams/:team_id", method="GET", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findTeam(RequestData request) throws ResponseException {
		Map<String, String> params = request.getParams();
		
		TournamentTeamModel team = TournamentTeamService.findTeamById(params);
		
		JSONObject teamData = ModelJsonParser.parse(team);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", teamData);
		jsonData.put("message", "Team found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams", method="GET", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findTeams(RequestData request) throws ResponseException {
		Map<String, String> params = request.getParams();
		Map<String, String[]> queryParams = request.getQueryParams();
		
		List<Model> teams = TournamentTeamService.findTeams(params, queryParams);
		
		JSONArray teamsData = new JSONArray();
		
		for(Model team : teams) {
			TournamentTeamModel teamModel = (TournamentTeamModel) team;
			JSONObject teamData = ModelJsonParser.parse(teamModel);
			teamsData.put(teamData);
		}
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", teamsData);
		jsonData.put("message", "Teams found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams/:team_id", method="PUT", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData updateTeam(RequestData request) throws ResponseException {
		JSONObject reqBody = request.getBody();
		Map<String, String> params = request.getParams();

		TournamentTeamModel team = (TournamentTeamModel) JsonModelParser.parse(reqBody, TournamentTeamModel.class);
		
		team.setTeamId(null);

		if(request.getUserRole() != UserRoles.ORGANIZATION_ADMIN) {
			team.setTeamStatus(null);
		}
		
		TournamentTeamModel updatedTeam = TournamentTeamService.updateTeamById(params, team);
		
		JSONObject teamData = ModelJsonParser.parse(updatedTeam);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", teamData);
		jsonData.put("message", "Team updated successfully");
		
		return new ResponseData(StatusCodes.ACCEPTED, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams/:team_id", method="DELETE", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData deleteTeam(RequestData request) throws ResponseException {
		Map<String, String> params = request.getParams();
		
		TournamentTeamModel deletedTeam = TournamentTeamService.deleteTeamById(params);
		
		JSONObject teamData = ModelJsonParser.parse(deletedTeam);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", teamData);
		jsonData.put("message", "Team deleted successfully");
		
		return new ResponseData(StatusCodes.NO_CONTENT, jsonData);
	}
	
}
