package com.saran.tms.controllers;

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
import com.saran.tms.models.TournamentEventTeamModel;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.TournamentEventTeamService;

@RouteGroup(path="/api/v1")
public class TournamentEventTeamController implements Controller {
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/events/:event_id/teams", method="POST", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN})
	public ResponseData saveTournamentEventTeams(RequestData request) throws ResponseException {
		
		Params params = request.getParams();
		JSONObject reqBody = request.getBody();

		JSONArray eventTeamsData = reqBody.getJSONArray("tournamentEventTeams");
		List<Model> eventTeams = JsonModelParser.parse(eventTeamsData, TournamentEventTeamModel.class);
		
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
		
		for(Model eventTeam : eventTeams) {
			((TournamentEventTeamModel) eventTeam).setTournamentEventTeamId(null);
			((TournamentEventTeamModel) eventTeam).setTournamentEventId(tournamentEventId);
			
		}
		
		List<Model> newEventTeams = TournamentEventTeamService.saveAllTournamentEventTeams(eventTeams);
		JSONArray newEventTeamsData = ModelJsonParser.parseAll(newEventTeams);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", newEventTeamsData);
		jsonData.put("message", "Teams added to event successfully");
		
		return new ResponseData(StatusCodes.ACCEPTED, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/events/:event_id/teams/:event_team_id", method="DELETE", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN})
	public ResponseData deleteTournamentEventTeams(RequestData request) throws ResponseException {
		Params params = request.getParams();
		
		List<Model> tournamentEventTeams = TournamentEventTeamService.deleteTournamentEventTeamBatch(params);
		
		JSONArray deletedEventTeamsData = ModelJsonParser.parseAll(tournamentEventTeams);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", deletedEventTeamsData);
		jsonData.put("message", "Teams removed from event successfully");
		
		return new ResponseData(StatusCodes.ACCEPTED, jsonData);
	}
}
