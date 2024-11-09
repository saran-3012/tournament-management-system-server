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
import com.saran.tms.models.TournamentEventModel;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.JoinConditionEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.OrderEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.utils.Utilities;

public class TournamentEventService {
	public static TournamentEventModel saveTournamentEvent(TournamentEventModel tournamentEvent) throws ResponseException {
		Dao tournamentEventDao = new Dao(TournamentEventModel.class);
		TournamentEventModel newTournamentEvent = (TournamentEventModel) tournamentEventDao.saveAndReturn(tournamentEvent, Arrays.asList("*"));
		return newTournamentEvent;
	}
	
	public static TournamentEventModel findTournamentEventById(Params params) throws ResponseException {
		Dao tournamentEventDao = new Dao(TournamentEventModel.class);
		
		Long tournamentEventId;
		try {
			tournamentEventId = params.getLong("event_id");
			if(tournamentEventId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Event id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid event id");
		}
		
		Long tournamentId;
		try {
			tournamentId = params.getLong("tournament_id");
			if(tournamentId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "tournament id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
		
		TournamentEventModel tournamentEvent = (TournamentEventModel) tournamentEventDao.findOne(
													Arrays.asList("*"), 
													Arrays.asList(
														new ConditionEntry(null, "tournament_event_id", Arrays.asList(Operators.EQUAL), tournamentEventId),
														new ConditionEntry(Arrays.asList(Operators.AND), "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId)
													)
												);
	
		if(tournamentEvent == null) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Event not found");
		}
		
		return tournamentEvent;
	}
	
	public static List<Model> findTournamentEvents(Params params, QueryParams queryParams) throws ResponseException {
		Dao tournamentEventDao = new Dao(TournamentEventModel.class);
		
		List<ConditionEntry> conditions = new ArrayList<>();
		
		Long tournamentId;
		try {
			tournamentId = params.getLong("tournament_id");
			if(tournamentId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "tournament id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
		
		conditions.add(new ConditionEntry(null, "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId));
		
		// Add more conditions here
		
		
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
		
		List<Model> tournamentEvents = tournamentEventDao.findAll(Arrays.asList("*"), conditions, limit, offset);
		
		return tournamentEvents;
		
	}
	
	public static List<List<Model>> findUserEvents(Params params, QueryParams queryParams) throws ResponseException {
		Dao tournamentEventDao = new Dao(TournamentEventModel.class);
		
		Long userId;
		try {
			userId = params.getLong("user_id");
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid user id");
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
		
		List<List<Model>> tournamentEvents = tournamentEventDao.findAllWithJoin(
				Arrays.asList(
					new TableColumnEntry(TableNames.TOURNAMENT_EVENTS, Arrays.asList("*"))
				), 
				Arrays.asList(
					new JoinEntry(TableNames.TOURNAMENT_EVENTS, TableNames.TOURNAMENT_EVENT_PARTICIPANTS, "tournament_event_id", "tournament_event_id", JoinTypes.LEFT_JOIN),
					new JoinEntry(TableNames.TOURNAMENT_EVENT_PARTICIPANTS, TableNames.TOURNAMENT_PARTICIPANTS, "participant_id", "participant_id", JoinTypes.LEFT_JOIN),
					new JoinEntry(TableNames.TOURNAMENT_EVENTS, TableNames.TOURNAMENT_EVENT_TEAMS, "tournament_event_id", "tournament_event_id", JoinTypes.LEFT_JOIN),
					new JoinEntry(TableNames.TOURNAMENT_EVENT_TEAMS, TableNames.TEAM_MEMBERS, "team_id", "team_id", JoinTypes.LEFT_JOIN),
					new JoinConditionEntry(Operators.AND, TableNames.TOURNAMENT_EVENTS, "tournament_event_status", Operators.EQUAL, (short) 0)
				),
				Arrays.asList(
					new TableConditionEntry(TableNames.TOURNAMENT_PARTICIPANTS, 
						Arrays.asList(
							new ConditionEntry(null, "user_id", Arrays.asList(Operators.EQUAL), userId)
						)
					),
					new TableConditionEntry(TableNames.TEAM_MEMBERS, 
						Arrays.asList(
							new ConditionEntry(Arrays.asList(Operators.OR), "user_id", Arrays.asList(Operators.EQUAL), userId)
						)
					)
				),
				Arrays.asList(
					new OrderEntry(TableNames.TOURNAMENT_EVENTS, "tournament_event_date", SortOrder.ASC)
				), limit, offset);
		
		return tournamentEvents;
		
	}
}
