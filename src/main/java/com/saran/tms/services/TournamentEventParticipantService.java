package com.saran.tms.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
	public static List<List<Model>> findTournamentEventParticipants(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		Dao tournamentEventParticipantDao = new Dao(TournamentEventParticipantModel.class);
		
		List<ConditionEntry> eventParticipantConditions = new ArrayList<>();
		List<ConditionEntry> userConditions = new ArrayList<>();
		List<ConditionEntry> participantConditions = new ArrayList<>();
		
		Long eventId = null;
		try {
			eventId = Long.parseLong(params.get("event_id"));
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid event id");
		}
		
		eventParticipantConditions.add(new ConditionEntry(null, "tournament_event_id", Arrays.asList(Operators.EQUAL), eventId));
		Operators operator = Operators.AND;
		
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
