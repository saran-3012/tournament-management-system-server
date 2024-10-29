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
import com.saran.tms.models.TeamMemberModel;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.TeamMemberService;
import com.saran.tms.services.TournamentService;

@RouteGroup(path="/api/v1")
public class TeamMemberController implements Controller {
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams/:team_id/members", method="POST", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData saveTeamMember(RequestData request) throws ResponseException {
		
		Map<String, String> params = request.getParams();
		
		Long teamId = null;
		
		try {
			teamId = Long.parseLong(params.get("team_id"));
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid team id");
		}
		
		List<Model> tournamentDetails = TournamentService.findTournamentById(params);
		
		JSONObject tournamentData = ModelJsonParser.parseAndMerge(tournamentDetails);
		
		Long maxTeamSize = tournamentData.optLong("teamSize");
		
		Long teamMemberCount = TeamMemberService.getTeamMemberCount(params, null);
		
		if(teamMemberCount >= maxTeamSize) {
			throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Team is full");
		}
		
		JSONObject reqBody = request.getBody();
		
		TeamMemberModel teamMember = (TeamMemberModel) JsonModelParser.parse(reqBody, TeamMemberModel.class);
		teamMember.setTeamId(teamId);
		
		TeamMemberModel newTeamMember = TeamMemberService.saveTeamMember(teamMember);
		
		JSONObject teamMemberData = ModelJsonParser.parse(newTeamMember);
		
		JSONObject jsonData = new JSONObject();
		
		jsonData.put("data", teamMemberData);
		jsonData.put("message", "Team Member created successfully");
		
		return new ResponseData(StatusCodes.CREATED, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams/:team_id/members/:member_id", method="GET", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findTeamMember(RequestData request) throws ResponseException {
		
		Map<String, String> params = request.getParams();
		
		List<Model> teamMemberDetails = TeamMemberService.findTeamMemberById(params);
		
		JSONObject teamMemberData = ModelJsonParser.parseAndMerge(teamMemberDetails);
		
		JSONObject jsonData = new JSONObject();
		
		jsonData.put("data", teamMemberData);
		jsonData.put("message", "Team member found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	} 
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams/:team_id/members", method="GET", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findTeamMembers(RequestData request) throws ResponseException {
		Map<String, String> params = request.getParams();
		Map<String, String[]> queryParams = request.getQueryParams();
		
		List<List<Model>> teamMemberDetailsList = TeamMemberService.findTeamMembers(params, queryParams);
		
		JSONArray teamMembersData = new JSONArray();
		
		for(List<Model> teamMemberDetails : teamMemberDetailsList) {
			JSONObject teamMemberData = ModelJsonParser.parseAndMerge(teamMemberDetails);
			teamMembersData.put(teamMemberData);
		}
		
		JSONObject jsonData = new JSONObject();
		
		jsonData.put("data", teamMembersData);
		jsonData.put("message", "Team members found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams/:team_id/members/:member_id", method="DELETE", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData deleteTeamMember(RequestData request) throws ResponseException {
		
		if(request.getUserRole() == UserRoles.ORGANIZATION_MEMBER) {
			ResponseData teamMemberResponse = this.findTeamMember(request);
			JSONObject jsonData = teamMemberResponse.getData();
			JSONObject teamMemberData = jsonData.optJSONObject("data");
			
			if(teamMemberData.optLong("teamLeaderId") != request.getUserId() && teamMemberData.optLong("userId") != request.getUserId()) {
				throw new ResponseException(StatusCodes.FORBIDDEN, "You are not allowed to perform this operation");
			}
		}
		
		Map<String, String> params = request.getParams();
		
		TeamMemberModel deletedTeamMember = TeamMemberService.deleteTeamMemberById(params);
		
		JSONObject teamMemberData = ModelJsonParser.parse(deletedTeamMember);
		
		JSONObject jsonData = new JSONObject();
		
		jsonData.put("data", teamMemberData);
		jsonData.put("message", "Team member deleted successfully");
		
		return new ResponseData(StatusCodes.ACCEPTED, jsonData);
	}
}
