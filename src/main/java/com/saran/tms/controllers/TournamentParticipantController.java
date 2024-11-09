package com.saran.tms.controllers;

import com.saran.tms.annotations.RouteGroup;
import com.saran.tms.concurrency.CallBackFunction;
import com.saran.tms.concurrency.ConcurrencyLimiter;
import com.saran.tms.concurrency.ConcurrencyLimiterFactory;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;

import com.saran.tms.adapters.JsonModelParser;
import com.saran.tms.adapters.ModelJsonParser;
import com.saran.tms.annotations.Route;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.enums.UserRoles;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.models.Model;
import com.saran.tms.models.SportModel;
import com.saran.tms.models.TournamentModel;
import com.saran.tms.models.TournamentParticipantModel;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.routers.RequestData;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.services.TournamentParticipantService;
import com.saran.tms.services.TournamentService;

@RouteGroup(path="/api/v1")
public class TournamentParticipantController implements Controller {
	
	
//	EXPERIMENTAL FOR CONCURRENCY CONTROL
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/participants", method="POST", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData saveParticipant(RequestData request) throws ResponseException {
		
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
			
			long registrationCount = TournamentParticipantService.getParticipantCount(params, queryParams);
			if(registrationCount >= maxParticipation) {
				throw new ResponseException(StatusCodes.CONFLICT, "Maximum number of participants registered");
			}
			
			JSONObject reqBody = request.getBody();
			
			TournamentParticipantModel participant = (TournamentParticipantModel) JsonModelParser.parse(reqBody, TournamentParticipantModel.class);
			participant.setTournamentId(tournament.getTournamentId());
			
			return TournamentParticipantService.saveParticipant(participant);
		};
		
		ConcurrencyLimiter concurrencyLimiter = ConcurrencyLimiterFactory.getConcurrencyLimiter("TournamentRegistration:Participants", maxParticipation, organizationId, tournamentId);
		
		TournamentParticipantModel newParticipant;
		try {
			newParticipant = (TournamentParticipantModel) concurrencyLimiter.executeCallBack(callBack);
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
		
		JSONObject participantData = ModelJsonParser.parse(newParticipant);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", participantData);
		jsonData.put("message", "Participant registered successfully");
		
		return new ResponseData(StatusCodes.CREATED, jsonData);
	}
	
	
//	ORIGINAL IMPLEMENTATION
	
//	@Route(path="/orgs/:org_id/tournaments/:tournament_id/participants", method="POST", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
//	public ResponseData saveParticipant(RequestData request) throws ResponseException {
//		
//		Map<String, String> params = request.getParams();
//		
//		Long tournamentId = null;
//		try {
//			tournamentId = Long.parseLong(params.get("tournament_id"));
//		}
//		catch(NumberFormatException e) {
//			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament data");
//		}
//		
//		List<Model> tournamentDetails = TournamentService.findTournamentByIdWithRegiteredCount(params);
//		
//		JSONObject tournamentData = ModelJsonParser.parseAndMerge(tournamentDetails);
//		
//		short tournamentStatus = (short) tournamentData.optInt("tournamentStatus");
//		if(tournamentStatus == 2) {
//			throw new ResponseException(StatusCodes.BAD_REQUEST, "Tournament has already completed");
//		}
//		if(tournamentStatus == 3) {
//			throw new ResponseException(StatusCodes.BAD_REQUEST, "Tournament has been cancelled");
//		}
//		
//		int maxParticipation = (int) tournamentData.optInt("maxParticipation");
//		int registrationCount = (int) tournamentData.optInt("count");
//		
//		long currentTimeMillis = Instant.now().toEpochMilli();
//		
//		long tournamentRegistrationStartDate = tournamentData.optLong("registrationStartDate");
//		long tournamentRegistrationEndDate = tournamentData.optLong("registrationEndDate");
//		
//		if(currentTimeMillis < tournamentRegistrationStartDate) {
//			throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Registration not yet started");
//		}
//		
//		if(currentTimeMillis > tournamentRegistrationEndDate) {
//			throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Registration period has been completed");
//		}
//		
//		if(registrationCount >= maxParticipation) {
//			throw new ResponseException(StatusCodes.CONFLICT, "Maximum number of participants registered");
//		}
//		
//		JSONObject reqBody = request.getBody();
//		
//		TournamentParticipantModel participant = (TournamentParticipantModel) JsonModelParser.parse(reqBody, TournamentParticipantModel.class);
//		participant.setTournamentId(tournamentId);
//		
//		TournamentParticipantModel newParticipant = TournamentParticipantService.saveParticipant(participant);
//		
//		JSONObject participantData = ModelJsonParser.parse(newParticipant);
//		
//		JSONObject jsonData = new JSONObject();
//		jsonData.put("data", participantData);
//		jsonData.put("message", "Participant registered successfully");
//		
//		return new ResponseData(StatusCodes.CREATED, jsonData);
//	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/participants/:participant_id", method="GET", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findParticipant(RequestData request) throws ResponseException {
		Params params = request.getParams();
		
		List<Model> participantDetails = TournamentParticipantService.findParticipantById(params);
		
		JSONObject participantData = ModelJsonParser.parseAndMerge(participantDetails);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", participantData);
		jsonData.put("message", "Participant found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/participants", method="GET", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData findParticipants(RequestData request) throws ResponseException {
		Params params = request.getParams();
		QueryParams queryParams = request.getQueryParams();
		
		List<List<Model>> participantDetailsList = TournamentParticipantService.findParticipants(params, queryParams);
		
		JSONArray participantsData = new JSONArray();
		
		for(List<Model> participantDetails : participantDetailsList) {
			JSONObject participantData = ModelJsonParser.parseAndMerge(participantDetails);
			participantsData.put(participantData);
		}
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", participantsData);
		jsonData.put("message", "Participants found");
		
		return new ResponseData(StatusCodes.OK, jsonData);
	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/participants/:participant_id", method="PUT", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN})
	public ResponseData updateParticipant(RequestData request) throws ResponseException {
		
		JSONObject reqBody = request.getBody();
		Params params = request.getParams();
		
		TournamentParticipantModel participant = (TournamentParticipantModel) JsonModelParser.parse(reqBody, TournamentParticipantModel.class);
		
		participant.setParticipantId(null);
		
		TournamentParticipantModel updatedParticipant = TournamentParticipantService.updateParticipantById(params, participant);
		
		JSONObject participantData = ModelJsonParser.parse(updatedParticipant);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", participantData);
		jsonData.put("message", "Participant updated successfully");
		
		return new ResponseData(StatusCodes.ACCEPTED, jsonData);
 	}
	
	@Route(path="/orgs/:org_id/tournaments/:tournament_id/participants/:participant_id", method="DELETE", allowedRoles= {UserRoles.APP_ADMIN, UserRoles.ORGANIZATION_ADMIN, UserRoles.ORGANIZATION_MEMBER})
	public ResponseData deleteParticipant(RequestData request) throws ResponseException {
		
		if(request.getUserRole() == UserRoles.ORGANIZATION_MEMBER) {
			
			ResponseData participantResponse = this.findParticipant(request);
			JSONObject jsonData = participantResponse.getData();
			JSONObject participantData = (JSONObject) jsonData.remove("data");
			
			if(participantData.optLong("userId") != request.getUserId()) {
				throw new ResponseException(StatusCodes.FORBIDDEN, "You are not allowed to perform this operation");
			}
		}

		Params params = request.getParams();
		
		TournamentParticipantModel deletedParticipant = TournamentParticipantService.deleteParticipantById(params);
		
		JSONObject participantData = ModelJsonParser.parse(deletedParticipant);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", participantData);
		jsonData.put("message", "Participant deleted successfully");
		
		return new ResponseData(StatusCodes.OK, jsonData);
		
	}
	
}
