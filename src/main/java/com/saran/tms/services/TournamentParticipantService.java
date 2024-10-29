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

public class TournamentParticipantService {
	
	public static TournamentParticipantModel saveParticipant(TournamentParticipantModel participant) throws ResponseException {
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		TournamentParticipantModel newParticipant = (TournamentParticipantModel) participantDao.saveAndReturn(participant, Arrays.asList("*"));
		return newParticipant;
	}
	
	public static List<Model> findParticipantById(Map<String, String> params) throws ResponseException {
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		
		Long participantId = null; 
		try {
			participantId = Long.parseLong(params.get("participant_id"));
		}
		catch(NumberFormatException e) {
			ApplicationLogger.log(Level.SEVERE, "Invalid participant id", e);
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid participant id");
		}
		
		Long tournamentId = null;
		try {
			tournamentId = Long.parseLong(params.get("tournament_id"));
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
	
	public static List<List<Model>> findParticipants(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		
		String needWinCount[] = queryParams.get("include_wincount");
		if(needWinCount != null && needWinCount.length > 0 && needWinCount[0].equals("true")) {
			return findParticipantsWithWinCount(params, queryParams);
		}
		
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		
		Operators operator = null;
		
		List<ConditionEntry> userConditions = new ArrayList<>();
		List<ConditionEntry> participantConditions = new ArrayList<>();
		
		String userNames[] = queryParams.get("filter_participantname");
		if(userNames != null && userNames.length > 0) {
			userConditions.add(new ConditionEntry(Arrays.asList(operator), "user_name", Arrays.asList(Operators.ILIKE), '%' + userNames[0] + '%'));
			operator = Operators.AND;
		}
		
		String userIds[] = queryParams.get("filter_userid");
		if(userIds != null && userIds.length > 0) {
			Long userId = null;
			try {
				userId = Long.parseLong(userIds[0]);
			}
			catch(NumberFormatException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid user id");
			}
			if(userId != null) {
				userConditions.add(new ConditionEntry(Arrays.asList(operator), "user_id", Arrays.asList(Operators.EQUAL), userId));
				operator = Operators.AND;
			}
		}
		
		String tournamentId = params.get("tournament_id");
		if(tournamentId != null) {
			participantConditions.add(new ConditionEntry(Arrays.asList(operator), "tournament_id", Arrays.asList(Operators.EQUAL), Long.parseLong(tournamentId)));
			operator = Operators.AND;
		}
		
		
		String participantStatuses[] = queryParams.get("filter_participantstatus");
		if(participantStatuses != null && participantStatuses.length > 0) {
			participantConditions.add(new ConditionEntry(Arrays.asList(operator), "participant_status", Arrays.asList(Operators.EQUAL), Short.parseShort(participantStatuses[0])));
			operator = Operators.AND;
		}
		
		Integer limit = 20;
		Integer page = 0;
		
		String limits[] = queryParams.get("limit");
		String pages[] = queryParams.get("page");
		
		if(limits != null && limits.length > 0) {
			limit = Integer.parseInt(limits[0]);
		}
		
		if(pages != null && pages.length > 0) {
			page = Integer.parseInt(pages[0]);
		}
		
		Integer offset = limit * page;
		
		String noLimit[] = queryParams.get("exclude_limit");
		if(noLimit != null && noLimit.length > 0 && noLimit[0].equals("true")) {
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
	
	private static List<List<Model>> findParticipantsWithWinCount(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		
		Operators operator = null;
		
		List<ConditionEntry> userConditions = new ArrayList<>();
		List<ConditionEntry> participantConditions = new ArrayList<>();
		
		String userNames[] = queryParams.get("filter_participantname");
		if(userNames != null && userNames.length > 0) {
			userConditions.add(new ConditionEntry(Arrays.asList(operator), "user_name", Arrays.asList(Operators.ILIKE), '%' + userNames[0] + '%'));
			operator = Operators.AND;
		}
		
		String userIds[] = queryParams.get("filter_userid");
		if(userIds != null && userIds.length > 0) {
			Long userId = null;
			try {
				userId = Long.parseLong(userIds[0]);
			}
			catch(NumberFormatException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid user id");
			}
			if(userId != null) {
				userConditions.add(new ConditionEntry(Arrays.asList(operator), "user_id", Arrays.asList(Operators.EQUAL), userId));
				operator = Operators.AND;
			}
		}
		
		Long tournamentId = null;
		try {
			tournamentId = Long.parseLong(params.get("tournament_id"));
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
		
		participantConditions.add(new ConditionEntry(Arrays.asList(operator), "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId));
		operator = Operators.AND;
		
		
		
		String participantStatuses[] = queryParams.get("filter_participantstatus");
		if(participantStatuses != null && participantStatuses.length > 0) {
			participantConditions.add(new ConditionEntry(Arrays.asList(operator), "participant_status", Arrays.asList(Operators.EQUAL), Short.parseShort(participantStatuses[0])));
			operator = Operators.AND;
		}
		
		Integer limit = 20;
		Integer page = 0;
		
		String limits[] = queryParams.get("limit");
		String pages[] = queryParams.get("page");
		
		if(limits != null && limits.length > 0) {
			limit = Integer.parseInt(limits[0]);
		}
		
		if(pages != null && pages.length > 0) {
			page = Integer.parseInt(pages[0]);
		}
		
		Integer offset = limit * page;
		
		String noLimit[] = queryParams.get("exclude_limit");
		if(noLimit != null && noLimit.length > 0 && noLimit[0].equals("true")) {
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
	
	
	
	public static List<Model> findUserParticipant(Map<String, String> params) throws ResponseException {
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		
		Long userId = Long.parseLong(params.get("user_id"));
		Long tournamentId = Long.parseLong(params.get("tournament_id"));
		
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
	
	public static Long getParticipantCount(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		
		Operators operator = null;
		
		List<ConditionEntry> conditions = new ArrayList<>();
		
		String tournamentId = params.get("tournament_id");
		if(tournamentId != null) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "tournament_id", Arrays.asList(Operators.EQUAL), Long.parseLong(tournamentId)));
			operator = Operators.AND;
		}
		
		String participantStatuses[] = queryParams.get("filter_participantstatus");
		if(participantStatuses != null && participantStatuses.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "participant_status", Arrays.asList(Operators.EQUAL), Short.parseShort(participantStatuses[0])));
			operator = Operators.AND;
		}
		
		Map<GroupEntry, Functions> fieldFunctions = new HashMap<>();
		fieldFunctions.put(new GroupEntry(TableNames.TOURNAMENT_PARTICIPANTS, "*"), Functions.COUNT);

		List<List<Model>> participantAggregateList = participantDao.findAll(Arrays.asList("*"), conditions, fieldFunctions, null, null);
		
		AggregateModel aggregateModel = (AggregateModel) participantAggregateList.get(0).get(0);
		
		return aggregateModel.getCount();
	}
	
	public static TournamentParticipantModel updateParticipantById(Map<String, String> params, TournamentParticipantModel participant) throws ResponseException {
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		
		List<Model> updatedParticipants = participantDao.updateAndReturn(
				participant, 
				Arrays.asList(
					new ConditionEntry(null, "participant_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("participant_id")))
				), 
				Arrays.asList("*")
			);
		
		if(updatedParticipants == null || updatedParticipants.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Participant not found");
		}
		
		return (TournamentParticipantModel) updatedParticipants.get(0);
	}
	
	public static TournamentParticipantModel deleteParticipantById(Map<String, String> params) throws NumberFormatException, ResponseException {
		Dao participantDao = new Dao(TournamentParticipantModel.class);
		
		List<Model> deletedParticipants = participantDao.deleteAndReturn(
				Arrays.asList(
					new ConditionEntry(null, "participant_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("participant_id")))
				), 
				Arrays.asList("*")
			);
		
		if(deletedParticipants == null || deletedParticipants.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Participant not found");
		}
		
		return (TournamentParticipantModel) deletedParticipants.get(0);	
	}
}
