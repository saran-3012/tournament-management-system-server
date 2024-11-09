package com.saran.tms.controllers;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;

import com.saran.tms.adapters.JsonModelParser;
import com.saran.tms.adapters.ModelJsonParser;
import com.saran.tms.annotations.Route;
import com.saran.tms.annotations.RouteGroup;
import com.saran.tms.concurrency.CallBackFunction;
import com.saran.tms.concurrency.ConcurrencyLimiter;
import com.saran.tms.concurrency.ConcurrencyLimiterFactory;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.enums.UserRoles;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.models.Model;
import com.saran.tms.models.SportModel;
import com.saran.tms.models.TeamMemberModel;
import com.saran.tms.models.TournamentModel;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.TeamMemberService;
import com.saran.tms.services.TournamentService;

@RouteGroup(path="/api/v1")
public class TeamMemberController implements Controller {
	
//	EXPERIMENTAL FOR CONCURRENCY CONTROL
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams/:team_id/members", method="POST", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData saveTeamMember(RequestData request) throws ResponseException {
		
		Params params = request.getParams();
		QueryParams queryParams = request.getQueryParams();
		
		Long organizationId = null;
		try {
			organizationId = Long.parseLong(params.get("org_id"));
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid organization id");
		}
		
		Long tournamentId = null;
		try {
			tournamentId = Long.parseLong(params.get("tournament_id"));
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
		
		Long teamId = null;
		try {
			teamId = Long.parseLong(params.get("team_id"));
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid team id");
		}
		
		List<Model> tournamentInfo = TournamentService.findTournamentById(params);
		int tournamentModelIndex = (tournamentInfo.get(0) instanceof TournamentModel)? 0 : 1;
		
		TournamentModel tournament = (TournamentModel) tournamentInfo.get(tournamentModelIndex);
		SportModel sport = (SportModel) tournamentInfo.get(1 - tournamentModelIndex);
		
		final int maxTeamSize = sport.getTeamSize();
		
		CallBackFunction callBack = (values) -> {
			short tournamentStatus = (short) tournament.getTournamentStatus();
			if(tournamentStatus == 2) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Tournament has already completed");
			}
			if(tournamentStatus == 3) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Tournament has been cancelled");
			}
			
			long currentTimeMillis = Instant.now().toEpochMilli();
			
			long tournamentRegistrationStartDate = tournament.getRegistrationStartDate(); 
			long tournamentRegistrationEndDate = tournament.getRegistrationEndDate();
			
			if(currentTimeMillis < tournamentRegistrationStartDate) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Registration not yet started");
			}
			
			if(currentTimeMillis > tournamentRegistrationEndDate) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Registration period has been completed");
			}
			
			long teamMemberCount = TeamMemberService.getTeamMemberCount(params, queryParams);
			if(teamMemberCount >= maxTeamSize) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Team is already full");
			}
			
			JSONObject reqBody = request.getBody();
			
			TeamMemberModel teamMember = (TeamMemberModel) JsonModelParser.parse(reqBody, TeamMemberModel.class);
			teamMember.setTeamId(Long.parseLong(params.get("team_id")));
			
			return TeamMemberService.saveTeamMember(teamMember);
		};
		
		ConcurrencyLimiter concurrencyLimiter = ConcurrencyLimiterFactory.getConcurrencyLimiter("TournamentRegistration:TeamMembers", maxTeamSize, organizationId, tournamentId, teamId);
		
		TeamMemberModel newTeamMember;
		try {
			newTeamMember = (TeamMemberModel) concurrencyLimiter.executeCallBack(callBack);
		} 
		catch(ResponseException e) {
			throw e;
		}
		catch(IllegalStateException e) {
			ApplicationLogger.log(Level.INFO, "Registration queue is full", e);
			throw new ResponseException(StatusCodes.TOO_MANY_REQUESTS, "Registration queue is full");
		}
		catch(Exception e) {
			ApplicationLogger.log(Level.WARNING, e.getMessage(), e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Something went wrong, try again");
		}
		
		JSONObject teamMemberData = ModelJsonParser.parse(newTeamMember);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", teamMemberData);
		jsonData.put("message", "Team Member created successfully");
		
		return new ResponseData(StatusCodes.CREATED, jsonData);
	}
	
//	ORIGINAL IMPLEMENTATION
	
//	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams/:team_id/members", method="POST", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
//	public ResponseData saveTeamMember(RequestData request) throws ResponseException {
//		
//		Params params = request.getParams();
//		
//		Long teamId = null;
//		
//		try {
//			teamId = Long.parseLong(params.get("team_id"));
//		}
//		catch(NumberFormatException e) {
//			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid team id");
//		}
//		
//		List<Model> tournamentDetails = TournamentService.findTournamentById(params);
//		
//		JSONObject tournamentData = ModelJsonParser.parseAndMerge(tournamentDetails);
//		
//		Long maxTeamSize = tournamentData.optLong("teamSize");
//		
//		Long teamMemberCount = TeamMemberService.getTeamMemberCount(params, null);
//		
//		if(teamMemberCount >= maxTeamSize) {
//			throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Team is full");
//		}
//		
//		JSONObject reqBody = request.getBody();
//		
//		TeamMemberModel teamMember = (TeamMemberModel) JsonModelParser.parse(reqBody, TeamMemberModel.class);
//		teamMember.setTeamId(teamId);
//		
//		TeamMemberModel newTeamMember = TeamMemberService.saveTeamMember(teamMember);
//		
//		JSONObject teamMemberData = ModelJsonParser.parse(newTeamMember);
//		
//		JSONObject jsonData = new JSONObject();
//		
//		jsonData.put("data", teamMemberData);
//		jsonData.put("message", "Team Member created successfully");
//		
//		return new ResponseData(StatusCodes.CREATED, jsonData);
//	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams/:team_id/members/:member_id", method="GET", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findTeamMember(RequestData request) throws ResponseException {
		
		Params params = request.getParams();
		
		List<Model> teamMemberDetails = TeamMemberService.findTeamMemberById(params);
		
		JSONObject teamMemberData = ModelJsonParser.parseAndMerge(teamMemberDetails);
		
		JSONObject jsonData = new JSONObject();
		
		jsonData.put("data", teamMemberData);
		jsonData.put("message", "Team member found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	} 
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams/:team_id/members", method="GET", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findTeamMembers(RequestData request) throws ResponseException {
		Params params = request.getParams();
		QueryParams queryParams = request.getQueryParams();
		
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
		
		Params params = request.getParams();
		
		TeamMemberModel deletedTeamMember = TeamMemberService.deleteTeamMemberById(params);
		
		JSONObject teamMemberData = ModelJsonParser.parse(deletedTeamMember);
		
		JSONObject jsonData = new JSONObject();
		
		jsonData.put("data", teamMemberData);
		jsonData.put("message", "Team member deleted successfully");
		
		return new ResponseData(StatusCodes.ACCEPTED, jsonData);
	}
}
