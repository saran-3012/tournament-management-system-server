package com.saran.tms.controllers;

import java.util.ArrayList;
import java.util.List;

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
import com.saran.tms.models.TournamentEventParticipantModel;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.TournamentEventParticipantService;

@RouteGroup(path="/api/v1")
public class TournamentEventParticipantController implements Controller {
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/events/:event_id/participants", method="POST", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN})
	public ResponseData saveTournamentEventTeams(RequestData request) throws ResponseException {
		
		Params params = request.getParams();
		JSONObject reqBody = request.getBody();

		JSONArray eventParticipantsData = reqBody.getJSONArray("tournamentEventParticipants");
		
		Long tournamentEventId;
		try {
			tournamentEventId = params.getLong("event_id");
			if(tournamentEventId == null) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Tournament event id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Invalid tournament event id");
		}
		
		int n = eventParticipantsData.length();
		List<Model> eventParticipants = new ArrayList<>(n);
		
		for(int i=0; i<n; i++) {
			TournamentEventParticipantModel eventParticipant = (TournamentEventParticipantModel) JsonModelParser.parse(eventParticipantsData.getJSONObject(i), TournamentEventParticipantModel.class);
			eventParticipant.setTournamentEventParticipantId(null);
			eventParticipant.setTournamentEventId(tournamentEventId);
			eventParticipants.set(i, eventParticipant);
		}
		
		List<Model> newEventParticipants = TournamentEventParticipantService.saveAllTournamentEventParticipants(eventParticipants);
		JSONArray newEventParticipantsData = ModelJsonParser.parseAll(newEventParticipants);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", newEventParticipantsData);
		jsonData.put("message", "Participants added to event successfully");
		
		return new ResponseData(StatusCodes.ACCEPTED, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/events/:event_id/participants/:event_participant_id", method="DELETE", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN})
	public ResponseData deleteTournamentEventParticipants(RequestData request) throws ResponseException {
		Params params = request.getParams();
		
		List<Model> tournamentEventParticipants = TournamentEventParticipantService.deleteTournamentEventParticipantBatch(params);
		
		JSONArray deletedEventParticipantsData = ModelJsonParser.parseAll(tournamentEventParticipants);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", deletedEventParticipantsData);
		jsonData.put("message", "Participants removed from event successfully");
		
		return new ResponseData(StatusCodes.ACCEPTED, jsonData);
	}
}
