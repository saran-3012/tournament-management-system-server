package com.saran.tms.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
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
import com.saran.tms.models.TeamMemberModel;
import com.saran.tms.models.TournamentModel;
import com.saran.tms.models.TournamentTeamModel;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.TeamMemberService;
import com.saran.tms.services.TournamentService;
import com.saran.tms.services.TournamentTeamService;

@RouteGroup(path="/api/v1")
public class TournamentTeamController implements Controller {
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams", method="POST", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData saveTeam(RequestData request) throws ResponseException {
		
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
		
		List<Model> tournamentInfo = TournamentService.findTournamentById(params);
		int tournamentModelIndex = (tournamentInfo.get(0) instanceof TournamentModel)? 0 : 1;
		
		TournamentModel tournament = (TournamentModel) tournamentInfo.get(tournamentModelIndex);
		
		final int maxParticipation = tournament.getMaxParticipation();
		
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
			
			long registrationCount = TournamentTeamService.getTeamCount(params, queryParams);
			if(registrationCount >= maxParticipation) {
				throw new ResponseException(StatusCodes.CONFLICT, "Maximum number of teams registered");
			}
			
			JSONObject reqBody = request.getBody();

			TournamentTeamModel team = (TournamentTeamModel) JsonModelParser.parse(reqBody, TournamentTeamModel.class);
			team.setTournamentId(tournament.getTournamentId());
			
			return TournamentTeamService.saveTeam(team);
		};
		
		ConcurrencyLimiter concurrencyLimiter = ConcurrencyLimiterFactory.getConcurrencyLimiter("TournamentRegistration:Teams", maxParticipation, organizationId, tournamentId);
		
		TournamentTeamModel newTeam;
		try {
			newTeam = (TournamentTeamModel) concurrencyLimiter.executeCallBack(callBack);
		} 
		catch(ResponseException e) {
			throw e;
		}
		catch(IllegalStateException e) {
			ApplicationLogger.log(Level.INFO, "Registration queue is full", e);
			throw new ResponseException(StatusCodes.TOO_MANY_REQUESTS, "Registration queue is full");
		}
		catch (Exception e) {
			ApplicationLogger.log(Level.WARNING, e.getMessage(), e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Something went wrong, try again");
		}
		
		TeamMemberModel teamMember = new TeamMemberModel();
		
		teamMember.setUserId(newTeam.getTeamLeaderId());
		teamMember.setTeamId(newTeam.getTeamId());
		
		TeamMemberModel teamLeader = TeamMemberService.saveTeamMember(teamMember);
		
		JSONObject teamData = ModelJsonParser.parseAndMerge(Arrays.asList(newTeam, teamLeader));

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", teamData);
		jsonData.put("message", "Team registered successfully");
		
		return new ResponseData(StatusCodes.CREATED, jsonData);
	}

	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams/:team_id", method="GET", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findTeam(RequestData request) throws ResponseException {
		Params params = request.getParams();
		
		List<Model> teamDetails = TournamentTeamService.findTeamById(params);
		
		JSONObject teamData = ModelJsonParser.parseAndMerge(teamDetails);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", teamData);
		jsonData.put("message", "Team found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams", method="GET", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findTeams(RequestData request) throws ResponseException {
		Params params = request.getParams();
		QueryParams queryParams = request.getQueryParams();
		
		List<List<Model>> teamDetailsList = TournamentTeamService.findTeams(params, queryParams);
		
		JSONArray teamsData = new JSONArray();
		
		for(List<Model> teamDetails : teamDetailsList) {
			
			JSONObject teamData = ModelJsonParser.parseAndMerge(teamDetails);
			teamsData.put(teamData);
		}
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", teamsData);
		jsonData.put("message", "Teams found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/teams/:team_id", method="PUT", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData updateTeam(RequestData request) throws ResponseException {
		
		if(request.getUserRole() == UserRoles.ORGANIZATION_MEMBER) {
			
			ResponseData teamResponse  = this.findTeam(request);
			JSONObject jsonData = teamResponse.getData();
			JSONObject teamData = jsonData.optJSONObject("data");
			
			if(teamData.optLong("teamLeaderId") != request.getUserId()) {
				throw new ResponseException(StatusCodes.FORBIDDEN, "You are not allowed to perform this operation");
			}
		}
		
		JSONObject reqBody = request.getBody();
		Params params = request.getParams();

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
		
		if(request.getUserRole() == UserRoles.ORGANIZATION_MEMBER) {
			
			ResponseData teamResponse  = this.findTeam(request);
			JSONObject jsonData = teamResponse.getData();
			JSONObject teamData = jsonData.optJSONObject("data");
			
			if(teamData.optLong("teamLeaderId") != request.getUserId()) {
				throw new ResponseException(StatusCodes.FORBIDDEN, "You are not allowed to perform this operation");
			}
		}
		
		Params params = request.getParams();
		
		TournamentTeamModel deletedTeam = TournamentTeamService.deleteTeamById(params);
		
		JSONObject teamData = ModelJsonParser.parse(deletedTeam);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", teamData);
		jsonData.put("message", "Team deleted successfully");
		
		return new ResponseData(StatusCodes.NO_CONTENT, jsonData);
	}
	
}
