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
import com.saran.tms.models.TournamentModel;
import com.saran.tms.models.TournamentParticipantModel;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.SportService;
import com.saran.tms.services.TournamentParticipantService;
import com.saran.tms.services.TournamentService;
import com.saran.tms.services.TournamentTeamService;

@RouteGroup(path="/api/v1")
public class TournamentController implements Controller {
	

	@Route(path="/orgs/:org_id/tournaments", method="POST", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN})
	public ResponseData saveTournament(RequestData request) throws ResponseException {
		
		JSONObject reqBody = request.getBody();
		Params params = request.getParams();

		TournamentModel tournament = (TournamentModel) JsonModelParser.parse(reqBody.getJSONObject("tournamentData"), TournamentModel.class);
		
		if(tournament.getRegistrationStartDate() > tournament.getRegistrationEndDate()) {
			throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Closing date should be after the opening date");
		}
		
		SportModel sport = (SportModel) JsonModelParser.parse(reqBody.getJSONObject("sportData"), SportModel.class);
		
		SportModel sportWithId = null;
		try {
			sportWithId = SportService.findSport(sport);
		}
		catch(Exception e){}
		
		if(sportWithId == null) {
			sportWithId = SportService.saveSport(sport);
		}
		
		tournament.setSportId(sportWithId.getSportId());
		
		Long organizationId = Long.parseLong(params.get("org_id"));
		tournament.setOrganizationId(organizationId);
		
		TournamentModel newtournament = TournamentService.saveTournament(tournament);
		
		JSONObject tournamentData = ModelJsonParser.parse(newtournament);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", tournamentData);
		jsonData.put("message", "Tournament created successfully");
		
		return new ResponseData(StatusCodes.CREATED, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id", method="GET", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findTournament(RequestData request) throws ResponseException {
		Params params = request.getParams();
		
		List<Model> tournamentDetails = TournamentService.findTournamentById(params);
		
		JSONObject tournamentData = ModelJsonParser.parseAndMerge(tournamentDetails);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", tournamentData);
		jsonData.put("message", "Tournament found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments", method="GET", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findTournaments(RequestData request) throws ResponseException {
		Params params = request.getParams();
		QueryParams queryParams = request.getQueryParams();
		
		List<List<Model>> tournamentDetailsList = TournamentService.findTournaments(params, queryParams);
		
		JSONArray tournamentsData = new JSONArray();
		
		for(List<Model> tournamentDetails : tournamentDetailsList) {
			JSONObject tournamentData = ModelJsonParser.parseAndMerge(tournamentDetails);
			tournamentsData.put(tournamentData);
		}
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", tournamentsData);
		jsonData.put("message", "Tournaments found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/contestants", method="GET", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findTournamentAndContestants(RequestData request) throws ResponseException {
		
		ResponseData tournamentResponse = this.findTournament(request);
		
		JSONObject jsonData = tournamentResponse.getData();
		
		JSONObject tournamentData = (JSONObject) jsonData.remove("data");
		
		Short participationType = (short) tournamentData.getInt("sportType");

		Params params = request.getParams();
		QueryParams queryParams = request.getQueryParams();
		
		List<List<Model>> contestantDetailsList = null;
		
		Boolean count = queryParams.getBoolean("include_count");
		boolean needCount = count != null && count;
		Long contestantsCount = null;
		
		Boolean includeUser = queryParams.getBoolean("include_user");
		boolean needUser = includeUser != null && includeUser;
		JSONObject userParticipationData = null;
		
		if(participationType == 0) {
			contestantDetailsList = TournamentParticipantService.findParticipants(params, queryParams);
			if(needCount) {
				contestantsCount = TournamentParticipantService.getParticipantCount(params, queryParams);
			}
			if(needUser) {
				params.put("user_id", request.getUserId().toString());
				List<Model> userParticipation = TournamentParticipantService.findUserParticipant(params);
				userParticipationData = ModelJsonParser.parseAndMerge(userParticipation);
			}
		}
		else if(participationType == 1){
			contestantDetailsList = TournamentTeamService.findTeams(params, queryParams);
			if(needCount) {
				contestantsCount = TournamentTeamService.getTeamCount(params, queryParams);
			}
			if(needUser) {
				params.put("user_id", request.getUserId().toString());
				List<Model> userTeam = TournamentTeamService.findUserTeam(params);
				userParticipationData = ModelJsonParser.parseAndMerge(userTeam);
			}
		}
		else {
			throw new ResponseException(StatusCodes.UNPROCESSABLE_CONTENT, "Invalid participation type, Try again");
		}
		
		JSONArray contestantsData = new JSONArray();
		
		for(List<Model> contestantDetails : contestantDetailsList) {
			JSONObject contestantData = ModelJsonParser.parseAndMerge(contestantDetails);
			contestantsData.put(contestantData);
		}
		
		JSONObject dataObject = new JSONObject();
		
		if(needCount) {
			dataObject.put("count", contestantsCount);
		}
		
		if(needUser) {
			dataObject.put("userParticipation", userParticipationData);
		}
		
		Boolean needTournamentData = queryParams.getBoolean("include_tournament");
		if(needTournamentData != null && needTournamentData) {			
			dataObject.put("tournament", tournamentData);
		}
	
		dataObject.put(participationType == 0? "participants" : "teams", contestantsData);
		
		jsonData.put("data", dataObject);
		jsonData.put("message", "Tournament and contestants found successfully");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id", method="PUT", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN})
	public ResponseData updateTournament(RequestData request) throws ResponseException {
		JSONObject reqBody = request.getBody();
		Params params = request.getParams();

		JSONObject updateTournamentData = reqBody.optJSONObject("tournamentData");
		JSONObject updateSportData = reqBody.optJSONObject("sportData");
		
		
		TournamentModel tournament = (TournamentModel) JsonModelParser.parse(updateTournamentData, TournamentModel.class);
		
		if(updateSportData != null && !updateSportData.isEmpty()) {
			List<Model> tournamentDetailsWithRegisteredCount = TournamentService.findTournamentByIdWithRegiteredCount(params);
			JSONObject tournamentDataWithRegisteredCount = ModelJsonParser.parseAndMerge(tournamentDetailsWithRegisteredCount);
			
			int teamSize = tournamentDataWithRegisteredCount.optInt("teamSize");
			int sportType = tournamentDataWithRegisteredCount.optInt("sportType");
			int registeredCount = tournamentDataWithRegisteredCount.optInt("count");
			
			Integer newTeamSize = updateSportData.optInt("teamSize");
			Integer newSportType = updateSportData.optInt("sportType");
			
			
			String tournamentUpdateOption = request.getHeaders().get("Tms-Tournament-Update-Option");
			
			if(registeredCount > 0 && ((newTeamSize != null && newTeamSize < teamSize) || (newSportType != null && newSportType != sportType)) && tournamentUpdateOption == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_REQUIRED, "Changing the sport will remove all registered contestants");
			}
			
			if(tournamentUpdateOption == "1") {
				SportModel sport = (SportModel) JsonModelParser.parse(updateSportData, SportModel.class);
				
				
				SportModel sportWithId = null;
				try {
					sportWithId = SportService.findSport(sport);
				}
				catch(Exception e){}
				
				if(sportWithId == null) {
					sportWithId = SportService.saveSport(sport);
				}
				
				tournament.setSportId(sportWithId.getSportId());
			}
		}

		
		tournament.setTournamentCreatedAt(null);
		tournament.setOrganizationId(null);
		
		TournamentModel updatedTournament = TournamentService.updateTournamentById(params, tournament);
		
		JSONObject tournamentData = ModelJsonParser.parse(updatedTournament);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", tournamentData);
		jsonData.put("message", "Tournament updated successfully");
		
		return new ResponseData(StatusCodes.ACCEPTED, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id", method="DELETE", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN})
	public ResponseData deleteTournament(RequestData request) throws ResponseException {
		Params params = request.getParams();
		
		TournamentModel deletedTournament = TournamentService.deleteTournamentById(params);
		
		JSONObject tournamentData = ModelJsonParser.parse(deletedTournament);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", tournamentData);
		jsonData.put("message", "Tournament deleted successfully");
		
		return new ResponseData(StatusCodes.NO_CONTENT, jsonData);
	}
}
