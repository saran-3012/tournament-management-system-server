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
import com.saran.tms.models.TournamentModel;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.TournamentService;

@RouteGroup(path="/api/v1")
public class TournamentController implements Controller {
	

	@Route(path="/orgs/:org_id/tournaments", method="POST", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN})
	public ResponseData saveTournament(RequestData request) throws ResponseException {
		
		JSONObject reqBody = request.getBody();
		Map<String, String> params = request.getParams();

		TournamentModel tournament = (TournamentModel) JsonModelParser.parse(reqBody, TournamentModel.class);
		
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
		Map<String, String> params = request.getParams();
		
		List<Model> tournamentDetails = TournamentService.findTournamentById(params);
		
		JSONObject tournamentData = ModelJsonParser.parseAndMerge(tournamentDetails);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", tournamentData);
		jsonData.put("message", "Tournament found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments", method="GET", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findTournaments(RequestData request) throws ResponseException {
		Map<String, String> params = request.getParams();
		Map<String, String[]> queryParams = request.getQueryParams();
		
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
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id", method="PUT", allowedRoles={UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN})
	public ResponseData updateOrganization(RequestData request) throws ResponseException {
		JSONObject reqBody = request.getBody();
		Map<String, String> params = request.getParams();

		TournamentModel tournament = (TournamentModel) JsonModelParser.parse(reqBody, TournamentModel.class);
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
	public ResponseData deleteOrganization(RequestData request) throws ResponseException {
		Map<String, String> params = request.getParams();
		
		TournamentModel deletedTournament = TournamentService.deleteTournamentById(params);
		
		JSONObject tournamentData = ModelJsonParser.parse(deletedTournament);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", tournamentData);
		jsonData.put("message", "Tournament deleted successfully");
		
		return new ResponseData(StatusCodes.NO_CONTENT, jsonData);
	}
}
