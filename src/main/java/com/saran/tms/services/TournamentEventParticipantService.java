package com.saran.tms.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.saran.tms.dao.Dao;
import com.saran.tms.enums.JoinTypes;
import com.saran.tms.enums.Operators;
import com.saran.tms.enums.SortOrder;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.enums.TableNames;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.models.Model;
import com.saran.tms.models.TournamentEventParticipantModel;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.OrderEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.utils.Utilities;

public class TournamentEventParticipantService {
	
	public static TournamentEventParticipantModel saveTournamentEventParticipant(TournamentEventParticipantModel tournamentEventParticipant) throws ResponseException {
		Dao tournamentEventParticipantDao = new Dao(TournamentEventParticipantModel.class);
		TournamentEventParticipantModel newTournamentEventParticipant = (TournamentEventParticipantModel) tournamentEventParticipantDao.saveAndReturn(tournamentEventParticipant, Arrays.asList("*"));
		return newTournamentEventParticipant;
	}
	
	public static List<Model> saveAllTournamentEventParticipant(List<Model> tournamentEventParticipants) throws ResponseException {
		Dao tournamentEventParticipantDao = new Dao(TournamentEventParticipantModel.class);
		List<Model> newTournamentEventParticipants = tournamentEventParticipantDao.saveAllAndReturn(tournamentEventParticipants, Arrays.asList("*"));
		return newTournamentEventParticipants;
	}
	
	public static List<List<Model>> findTournamentEventParticipants(Params params, QueryParams queryParams) throws ResponseException {
		Dao tournamentEventParticipantDao = new Dao(TournamentEventParticipantModel.class);
		
		List<ConditionEntry> eventParticipantConditions = new ArrayList<>();
		List<ConditionEntry> userConditions = new ArrayList<>();
		List<ConditionEntry> participantConditions = new ArrayList<>();
		
		Long eventId;
		try {
			eventId = params.getLong("event_id");
			if(eventId == null) {				
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Event id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid event id");
		}
		
		eventParticipantConditions.add(new ConditionEntry(null, "tournament_event_id", Arrays.asList(Operators.EQUAL), eventId));
		Operators operator = Operators.AND;
		
		String userName = queryParams.get("filter_participantname");
		if(userName != null) {
			userConditions.add(new ConditionEntry(Arrays.asList(operator), "user_name", Arrays.asList(Operators.ILIKE), '%' + userName + '%'));
		}
		
		
		try {
			Long userId = queryParams.getLong("filter_userid");
			if(userId != null) {
				userConditions.add(new ConditionEntry(Arrays.asList(operator), "user_id", Arrays.asList(Operators.EQUAL), userId));
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid user id");
		}
		
		
		try {
			Long tournamentId = params.getLong("tournament_id");
			if(tournamentId != null) {
				participantConditions.add(new ConditionEntry(Arrays.asList(operator), "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId));
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
	
		
		try {
			Short participantStatus = queryParams.getShort("filter_participantstatus");
			if(participantStatus != null) {				
				participantConditions.add(new ConditionEntry(Arrays.asList(operator), "participant_status", Arrays.asList(Operators.EQUAL), participantStatus));
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid participant status");
		}
		
		Integer limit;
		Integer page;
		
		try {
			limit = (int) Utilities.nullFallback(queryParams.getInt("limit"), 20);
			if(limit < 0) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Limit cannot be negative");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid limit value");
		}
		
		try {
			page = (int) Utilities.nullFallback(queryParams.getInt("page"), 0);
			if(page < 0) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Page cannot be negative");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid page value");
		}
		
		Integer offset = limit * page;
		
		Boolean excludeLimit = queryParams.getBoolean("exclude_limit");
		if(excludeLimit != null && excludeLimit) {
			limit = null;
			offset = null;
		}
		
		List<List<Model>> tournamentEventParticipantsDetails = tournamentEventParticipantDao.findAllWithJoin(
								Arrays.asList(
									new TableColumnEntry(TableNames.TOURNAMENT_EVENT_PARTICIPANTS, Arrays.asList("*")),
									new TableColumnEntry(TableNames.TOURNAMENT_PARTICIPANTS, Arrays.asList("*")),
									new TableColumnEntry(TableNames.USERS, Arrays.asList("user_name"))
								),
								Arrays.asList(
									new JoinEntry(TableNames.TOURNAMENT_EVENT_PARTICIPANTS, TableNames.TOURNAMENT_PARTICIPANTS, "participant_id", "participant_id", JoinTypes.JOIN),
									new JoinEntry(TableNames.TOURNAMENT_PARTICIPANTS, TableNames.USERS, "user_id", "user_id", JoinTypes.JOIN)
								), 
								Arrays.asList(
									new TableConditionEntry(TableNames.TOURNAMENT_EVENT_PARTICIPANTS, eventParticipantConditions),
									new TableConditionEntry(TableNames.TOURNAMENT_PARTICIPANTS, participantConditions),
									new TableConditionEntry(TableNames.USERS, userConditions)
								),
								Arrays.asList(
									new OrderEntry(TableNames.USERS, "user_name", SortOrder.ASC)
								),limit, offset);
		
		if(tournamentEventParticipantsDetails == null) {
			return new ArrayList<>();
		}
		
		return tournamentEventParticipantsDetails;
	}
} 
