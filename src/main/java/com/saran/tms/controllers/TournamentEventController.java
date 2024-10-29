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
import com.saran.tms.models.TournamentEventModel;
import com.saran.tms.models.TournamentEventParticipantModel;
import com.saran.tms.models.TournamentEventTeamModel;
import com.saran.tms.models.TournamentModel;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.TournamentEventParticipantService;
import com.saran.tms.services.TournamentEventService;
import com.saran.tms.services.TournamentEventTeamService;
import com.saran.tms.services.TournamentService;

@RouteGroup(path="/api/v1")
public class TournamentEventController implements Controller {
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/events", method="POST", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN})
	public ResponseData saveTournamentEvent(RequestData request) throws ResponseException {
		
		JSONObject reqBody = request.getBody();
		Map<String, String> params = request.getParams();
		
		Long tournamentId = null;
		
		try {
			tournamentId = Long.parseLong(params.get("tournament_id"));
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid Tournament id");
		}
		
		List<Model> tournamentDetails = TournamentService.findTournamentById(params);
		JSONObject tournamentData = ModelJsonParser.parseAndMerge(tournamentDetails);
		final short participationType = (short) tournamentData.getInt("sportType");
		
		TournamentEventModel tournamentEvent = (TournamentEventModel) JsonModelParser.parse(reqBody.getJSONObject("eventData"), TournamentEventModel.class);
		tournamentEvent.setTournamentId(tournamentId);
		TournamentEventModel newTournamentEvent = TournamentEventService.saveTournamentEvent(tournamentEvent); 
		JSONObject tournamentEventData = ModelJsonParser.parse(newTournamentEvent);
		final Long tournamentEventId = newTournamentEvent.getTournamentEventId();
		
		List<Model> newEventContestants = null;
		
		if(participationType == 0) {
			List<Model> tournamentEventParticipants = JsonModelParser.parse(reqBody.optJSONArray("eventParticipantsData"), TournamentEventParticipantModel.class);
			for(Model tournamentEventParticipant : tournamentEventParticipants) {
				((TournamentEventParticipantModel) tournamentEventParticipant).setTournamentEventId(tournamentEventId);
			}
			newEventContestants = TournamentEventParticipantService.saveAllTournamentEventParticipant(tournamentEventParticipants);
		}
		else if(participationType == 1) {
			List<Model> tournamentEventTeams = JsonModelParser.parse(reqBody.optJSONArray("eventTeamsData"), TournamentEventTeamModel.class);
			for(Model tournamentEventTeam : tournamentEventTeams) {
				((TournamentEventTeamModel) tournamentEventTeam).setTournamentEventId(tournamentEventId);
			}
			newEventContestants = TournamentEventTeamService.saveAllTournamentEventTeam(tournamentEventTeams);
		}
		
		JSONArray newContestantsData = ModelJsonParser.parseAll(newEventContestants);
		
		for(Object newContestantData : newContestantsData) {
			Long winCount = (Long) ((JSONObject) newContestantData).remove("count");
			((JSONObject) newContestantData).put("winCount", winCount);
		}
		
		JSONObject dataObject = new JSONObject();
		
		dataObject.put("event", tournamentEventData);
		dataObject.put((participationType == 0)? "eventParticipants" : "eventTeams", newContestantsData);
		
		JSONObject jsonData = new JSONObject();
		
		jsonData.put("data", dataObject);
		jsonData.put("message", "Event scheduled successfully");
		
		return new ResponseData(StatusCodes.CREATED, jsonData);
		
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/events/:event_id", method="GET", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findTournamentEvent(RequestData request) throws ResponseException {
		Map<String, String> params = request.getParams();
		
		TournamentEventModel tournamentEvent = TournamentEventService.findTournamentEventById(params);
		
		JSONObject tournamentEventData = ModelJsonParser.parse(tournamentEvent);
		
		JSONObject jsonData = new JSONObject();
		
		jsonData.put("data", tournamentEventData);
		jsonData.put("message", "Event found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/events", method="GET", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findTournamentEvents(RequestData request) throws ResponseException {
		Map<String, String> params = request.getParams();
		Map<String, String[]> queryParams = request.getQueryParams();
		
		List<Model> tournamentEvents = TournamentEventService.findTournamentEvents(params, queryParams);
		
		JSONArray tournamentEventsData = ModelJsonParser.parseAll(tournamentEvents);
		
		JSONObject jsonData = new JSONObject();
		
		jsonData.put("data", tournamentEventsData);
		jsonData.put("message", "Events found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/events/:event_id/contestants", method="GET", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findTournamentEventContestants(RequestData request) throws ResponseException {
		Map<String, String> params = request.getParams();
		Map<String, String[]> queryParams = request.getQueryParams();
		
		List<Model> tournamentDetails = TournamentService.findTournamentById(params);
		
		if(tournamentDetails == null || tournamentDetails.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Tournament not found");
		}
		
		SportModel sport = (tournamentDetails.get(0) instanceof TournamentModel)? (SportModel) tournamentDetails.get(1) : (SportModel) tournamentDetails.get(0);
		
		List<List<Model>> eventContestantsDetails = null;
		
		switch(sport.getSportType()) {
			case 0:
				eventContestantsDetails = TournamentEventParticipantService.findTournamentEventParticipants(params, queryParams);
				break;
			case 1:
				eventContestantsDetails = TournamentEventTeamService.findTournamentEventTeams(params, queryParams);
				break;
			default:
				throw new ResponseException(StatusCodes.UNPROCESSABLE_CONTENT, "Something wrong with participation type");
		}
		
		JSONArray eventContestants = new JSONArray();
		
		for(List<Model> eventContestantDetails : eventContestantsDetails) {
			JSONObject eventContestantData = ModelJsonParser.parseAndMerge(eventContestantDetails);
			eventContestants.put(eventContestantData);
		}
		
		JSONObject jsonData = new JSONObject();
		
		jsonData.put("data", eventContestants);
		jsonData.put("message", "Event contestants found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
		
	}
}
