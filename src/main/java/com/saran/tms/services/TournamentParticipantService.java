package com.saran.tms.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.saran.tms.dao.Dao;
import com.saran.tms.enums.Functions;
import com.saran.tms.enums.JoinTypes;
import com.saran.tms.enums.Operators;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.enums.TableNames;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.models.AggregateModel;
import com.saran.tms.models.Model;
import com.saran.tms.models.TournamentParticipantModel;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.GroupEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.utils.Utilities;

public class TournamentParticipantService {
	
	public static TournamentParticipantModel saveParticipant(TournamentParticipantModel participant) throws ResponseException {
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		TournamentParticipantModel newParticipant = (TournamentParticipantModel) participantDao.saveAndReturn(participant, Arrays.asList("*"));
		return newParticipant;
	}
	
	public static List<Model> findParticipantById(Params params) throws ResponseException {
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		
		Long participantId; 
		try {
			participantId = params.getLong("participant_id");
			if(participantId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Participant id is not provided");
			}
		}
		catch(NumberFormatException e) {
			ApplicationLogger.log(Level.SEVERE, "Invalid participant id", e);
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid participant id");
		}
		
		Long tournamentId;
		try {
			tournamentId = params.getLong("tournament_id");
			if(tournamentId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Tournament id is not provided");
			}
		}
		catch(NumberFormatException e) {
			ApplicationLogger.log(Level.SEVERE, "Invalid tournament id", e);
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
		
		List<Model> participantDetails = participantDao.findOneWithJoin(
			Arrays.asList(
				new TableColumnEntry(TableNames.TOURNAMENT_PARTICIPANTS, Arrays.asList("*")),
				new TableColumnEntry(TableNames.USERS, Arrays.asList("user_name"))
			), 
			Arrays.asList(
				new JoinEntry(TableNames.TOURNAMENT_PARTICIPANTS, TableNames.USERS, "user_id", "user_id", JoinTypes.JOIN)
			), 
			Arrays.asList(
				new TableConditionEntry(TableNames.TOURNAMENT_PARTICIPANTS, 
					Arrays.asList(
						new ConditionEntry(null, "participant_id", Arrays.asList(Operators.EQUAL), participantId),
						new ConditionEntry(Arrays.asList(Operators.AND), "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId)
					)
				)
			)
		);
		
		if(participantDetails == null) {
			return new ArrayList<>();
		}
		
		return participantDetails;

		
	}
	
	public static List<List<Model>> findParticipants(Params params, QueryParams queryParams) throws ResponseException {
		
		Boolean needWinCount = queryParams.getBoolean("include_wincount");
		if(needWinCount != null && needWinCount) {
			return findParticipantsWithWinCount(params, queryParams);
		}
		
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		
		Operators operator = null;
		
		List<ConditionEntry> userConditions = new ArrayList<>();
		List<ConditionEntry> participantConditions = new ArrayList<>();
		
		String userName = queryParams.get("filter_participantname");
		if(userName != null) {
			userConditions.add(new ConditionEntry(Arrays.asList(operator), "user_name", Arrays.asList(Operators.ILIKE), '%' + userName + '%'));
			operator = Operators.AND;
		}
		

		try {
			Long userId = queryParams.getLong("filter_userid");
			if(userId != null) {
				userConditions.add(new ConditionEntry(Arrays.asList(operator), "user_id", Arrays.asList(Operators.EQUAL), userId));
				operator = Operators.AND;
				}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid user id");
		}
		
		
		try{
			Long tournamentId = params.getLong("tournament_id");
			if(tournamentId != null) {
				participantConditions.add(new ConditionEntry(Arrays.asList(operator), "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId));
				operator = Operators.AND;
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "tournament user id");
		}
		
		
		try {
			Short participantStatus = queryParams.getShort("filter_participantstatus");
			if(participantStatus != null) {
				participantConditions.add(new ConditionEntry(Arrays.asList(operator), "participant_status", Arrays.asList(Operators.EQUAL), participantStatus));
				operator = Operators.AND;
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
		
		List<List<Model>> participantDetailsList = participantDao.findAllWithJoin(
				Arrays.asList(
					new TableColumnEntry(TableNames.TOURNAMENT_PARTICIPANTS, Arrays.asList("*")),
					new TableColumnEntry(TableNames.USERS, Arrays.asList("user_name"))
					
				), 
				Arrays.asList(
					new JoinEntry(TableNames.TOURNAMENT_PARTICIPANTS, TableNames.USERS, "user_id", "user_id", JoinTypes.JOIN)
				), 
				Arrays.asList(
					new TableConditionEntry(TableNames.USERS, userConditions),
					new TableConditionEntry(TableNames.TOURNAMENT_PARTICIPANTS, participantConditions)
				), limit, offset);
	
		return participantDetailsList;
	}
	
	private static List<List<Model>> findParticipantsWithWinCount(Params params, QueryParams queryParams) throws ResponseException {
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		
		Operators operator = null;
		
		List<ConditionEntry> userConditions = new ArrayList<>();
		List<ConditionEntry> participantConditions = new ArrayList<>();
		
		String userName = queryParams.get("filter_participantname");
		if(userName != null) {
			userConditions.add(new ConditionEntry(Arrays.asList(operator), "user_name", Arrays.asList(Operators.ILIKE), '%' + userName + '%'));
			operator = Operators.AND;
		}
		
		
		try {
			Long userId = queryParams.getLong("filter_userid");
			if(userId != null) {
				userConditions.add(new ConditionEntry(Arrays.asList(operator), "user_id", Arrays.asList(Operators.EQUAL), userId));
				operator = Operators.AND;
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid user id");
		}
		
		
		Long tournamentId = null;
		try {
			tournamentId = params.getLong("tournament_id");
			if(tournamentId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Tournament id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
		
		participantConditions.add(new ConditionEntry(Arrays.asList(operator), "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId));
		operator = Operators.AND;
		
		
		try {			
			Short participantStatus = queryParams.getShort("filter_participantstatus");
			if(participantStatus != null) {
				participantConditions.add(new ConditionEntry(Arrays.asList(operator), "participant_status", Arrays.asList(Operators.EQUAL), participantStatus));
				operator = Operators.AND;
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
		
		Map<GroupEntry, Functions> fieldFunctions = new HashMap<>();
		fieldFunctions.put(new GroupEntry(TableNames.TOURNAMENT_EVENTS, "*"), Functions.COUNT);
		
		
		List<List<Model>> participantDetailsList = participantDao.findAllWithJoin(
				Arrays.asList(
					new TableColumnEntry(TableNames.TOURNAMENT_PARTICIPANTS, Arrays.asList("*")),
					new TableColumnEntry(TableNames.USERS, Arrays.asList("user_name")),
					new TableColumnEntry(TableNames.TOURNAMENT_EVENTS, Arrays.asList("*"))
					
				), 
				Arrays.asList(
					new JoinEntry(TableNames.TOURNAMENT_PARTICIPANTS, TableNames.USERS, "user_id", "user_id", JoinTypes.JOIN),
					new JoinEntry(TableNames.TOURNAMENT_PARTICIPANTS, TableNames.TOURNAMENT_EVENTS, "user_id", "tournament_event_winner_id", JoinTypes.LEFT_JOIN)
				), 
				Arrays.asList(
					new TableConditionEntry(TableNames.TOURNAMENT_PARTICIPANTS, participantConditions),
					new TableConditionEntry(TableNames.USERS, userConditions),
					new TableConditionEntry(TableNames.TOURNAMENT_EVENTS,
						Arrays.asList(
							new ConditionEntry(Arrays.asList(operator), "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId)	
						)
					)
				),
				fieldFunctions,
				Arrays.asList(
					new GroupEntry(TableNames.TOURNAMENT_PARTICIPANTS, "participant_id")
				), limit, offset);
	
		return participantDetailsList;
		
	}
	
	
	
	public static List<Model> findUserParticipant(Params params) throws ResponseException {
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		
		Long userId;
		try {
			userId = params.getLong("user_id");
			if(userId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "U id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid user id");
		}
		
		Long tournamentId;
		try {
			tournamentId = params.getLong("tournament_id");
			if(tournamentId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Tournament id is not provided");
			}
		}
		catch(NumberFormatException e) {
			ApplicationLogger.log(Level.SEVERE, "Invalid tournament id", e);
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
		
		List<Model> participantDetails = participantDao.findOneWithJoin(
				Arrays.asList(
					new TableColumnEntry(TableNames.TOURNAMENT_PARTICIPANTS, Arrays.asList("*")),
					new TableColumnEntry(TableNames.USERS, Arrays.asList("user_name"))
				), 
				Arrays.asList(
					new JoinEntry(TableNames.TOURNAMENT_PARTICIPANTS, TableNames.USERS, "user_id", "user_id", JoinTypes.JOIN)
				), 
				Arrays.asList(
					new TableConditionEntry(TableNames.TOURNAMENT_PARTICIPANTS, 
						Arrays.asList(
							new ConditionEntry(null, "user_id", Arrays.asList(Operators.EQUAL), userId),
							new ConditionEntry(Arrays.asList(Operators.AND), "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId)
						)
					)
				)
			);
		
		if(participantDetails == null) {
			participantDetails = new ArrayList<>();
		}
		
		return participantDetails;
	}
	
	public static Long getParticipantCount(Params params, QueryParams queryParams) throws ResponseException {
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		
		Operators operator = null;
		
		List<ConditionEntry> conditions = new ArrayList<>();
		

	
		try {
			Long tournamentId = params.getLong("tournament_id");
			if(tournamentId != null) {
				conditions.add(new ConditionEntry(Arrays.asList(operator), "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId));
				operator = Operators.AND;
			}
		}
		catch(NumberFormatException e) {
			ApplicationLogger.log(Level.SEVERE, "Invalid tournament id", e);
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
		

		try {			
			Short participantStatus = queryParams.getShort("filter_participantstatus");
			if(participantStatus != null) {
				conditions.add(new ConditionEntry(Arrays.asList(operator), "participant_status", Arrays.asList(Operators.EQUAL), participantStatus));
				operator = Operators.AND;
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid participant status");
		}
		
		Map<GroupEntry, Functions> fieldFunctions = new HashMap<>();
		fieldFunctions.put(new GroupEntry(TableNames.TOURNAMENT_PARTICIPANTS, "*"), Functions.COUNT);

		List<List<Model>> participantAggregateList = participantDao.findAll(Arrays.asList("*"), conditions, fieldFunctions, null, null);
		
		AggregateModel aggregateModel = (AggregateModel) participantAggregateList.get(0).get(0);
		
		return aggregateModel.getCount();
	}
	
	public static TournamentParticipantModel updateParticipantById(Params params, TournamentParticipantModel participant) throws ResponseException {
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		
		Long participantId; 
		try {
			participantId = params.getLong("participant_id");
			if(participantId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Participant id is not provided");
			}
		}
		catch(NumberFormatException e) {
			ApplicationLogger.log(Level.SEVERE, "Invalid participant id", e);
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid participant id");
		}
		
		List<Model> updatedParticipants = participantDao.updateAndReturn(
				participant, 
				Arrays.asList(
					new ConditionEntry(null, "participant_id", Arrays.asList(Operators.EQUAL), participantId)
				), 
				Arrays.asList("*")
			);
		
		if(updatedParticipants == null || updatedParticipants.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Participant not found");
		}
		
		return (TournamentParticipantModel) updatedParticipants.get(0);
	}
	
	public static TournamentParticipantModel deleteParticipantById(Params params) throws NumberFormatException, ResponseException {
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		
		Long participantId; 
		try {
			participantId = params.getLong("participant_id");
			if(participantId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Participant id is not provided");
			}
		}
		catch(NumberFormatException e) {
			ApplicationLogger.log(Level.SEVERE, "Invalid participant id", e);
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid participant id");
		}
		
		List<Model> deletedParticipants = participantDao.deleteAndReturn(
				Arrays.asList(
					new ConditionEntry(null, "participant_id", Arrays.asList(Operators.EQUAL), participantId)
				), 
				Arrays.asList("*")
			);
		
		if(deletedParticipants == null || deletedParticipants.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Participant not found");
		}
		
		return (TournamentParticipantModel) deletedParticipants.get(0);	
	}
}
