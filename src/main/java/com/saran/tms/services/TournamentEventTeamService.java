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
import com.saran.tms.models.TournamentEventTeamModel;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.OrderEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.utils.Utilities;

public class TournamentEventTeamService {

	public static TournamentEventTeamModel saveTournamentEventTeam(TournamentEventTeamModel tournamentEventTeam) throws ResponseException {
		Dao tournamentEventTeamDao = new Dao(TournamentEventTeamModel.class);
		TournamentEventTeamModel newTournamentEventTeam = (TournamentEventTeamModel) tournamentEventTeamDao.saveAndReturn(tournamentEventTeam, Arrays.asList("*"));
		return newTournamentEventTeam;
	}
	
	public static List<Model> saveAllTournamentEventTeams(List<Model> tournamentEventTeams) throws ResponseException {
		Dao tournamentEventTeamDao = new Dao(TournamentEventTeamModel.class);
		List<Model> newTournamentEventTeams = tournamentEventTeamDao.saveAllAndReturn(tournamentEventTeams, Arrays.asList("*"));
		return newTournamentEventTeams;
	}
	
	public static List<List<Model>> findTournamentEventTeams(Params params, QueryParams queryParams) throws ResponseException {
		Dao tournamentEventTeamDao = new Dao(TournamentEventTeamModel.class);
		
		List<ConditionEntry> eventTeamConditions = new ArrayList<>();
		List<ConditionEntry> teamConditions = new ArrayList<>();
		
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
		
		eventTeamConditions.add(new ConditionEntry(null, "tournament_event_id", Arrays.asList(Operators.EQUAL), eventId));
		
		String teamName = queryParams.get("filter_teamname");
		if(teamName != null) {
			teamConditions.add(new ConditionEntry(Arrays.asList(Operators.AND), "team_name", Arrays.asList(Operators.ILIKE), '%' + teamName + '%'));
		}
		
		try {			
			Long tournamentId = params.getLong("tournament_id");
			if(tournamentId != null) {
				teamConditions.add(new ConditionEntry(Arrays.asList(Operators.AND), "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId));
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
		
		try {			
			Short teamStatus = queryParams.getShort("filter_teamstatus");
			if(teamStatus != null) {
				teamConditions.add(new ConditionEntry(Arrays.asList(Operators.AND), "team_status", Arrays.asList(Operators.EQUAL), teamStatus));
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
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
		
		
		List<List<Model>> tournamentEventTeamsDetails = tournamentEventTeamDao.findAllWithJoin(
					Arrays.asList(
						new TableColumnEntry(TableNames.TOURNAMENT_EVENT_TEAMS, Arrays.asList("*")),
						new TableColumnEntry(TableNames.TOURNAMENT_TEAMS, Arrays.asList("*")),
						new TableColumnEntry(TableNames.USERS, Arrays.asList("user_name"))
					), 
					Arrays.asList(
						new JoinEntry(TableNames.TOURNAMENT_EVENT_TEAMS, TableNames.TOURNAMENT_TEAMS, "team_id", "team_id", JoinTypes.JOIN),
						new JoinEntry(TableNames.TOURNAMENT_TEAMS, TableNames.USERS, "team_leader_id", "user_id", JoinTypes.JOIN)
					), 
					Arrays.asList(
						new TableConditionEntry(TableNames.TOURNAMENT_EVENT_TEAMS, eventTeamConditions),
						new TableConditionEntry(TableNames.TOURNAMENT_TEAMS, teamConditions)
					),
					Arrays.asList(
						new OrderEntry(TableNames.TOURNAMENT_TEAMS, "team_name", SortOrder.ASC)
					),
					limit, offset);
		
		if(tournamentEventTeamsDetails == null) {
			return new ArrayList<>();
		}
		
		return tournamentEventTeamsDetails;
	}
	
	public static List<Model> deleteTournamentEventTeamBatch(Params params) throws ResponseException {
		Dao tournamentEventTeamDao = new Dao(TournamentEventTeamModel.class);
		
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
		String eventTeamIds[] = params.getStringArray("event_team_id");
		
		int n = eventTeamIds.length;
		if(n == 0) {
			throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Event team id is not provided");
		}
		
		List<ConditionEntry> conditionEntries = new ArrayList<>();
		
		conditionEntries.add(new ConditionEntry(null, "tournament_event_id", Arrays.asList(Operators.EQUAL), eventId));
		
		boolean isFirstId = true;
		for(String eventTeamId : eventTeamIds) {
			Long eTeamId;
			try {
				eTeamId = Long.parseLong(eventTeamId);
			}
			catch(NumberFormatException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid event team id");
			}
			if(isFirstId) {				
				conditionEntries.add(new ConditionEntry(Arrays.asList(Operators.AND, Operators.OPEN_BRACKET), "tournament_event_team_id", Arrays.asList(Operators.EQUAL), eTeamId));
				isFirstId = false;
			}
			else {
				conditionEntries.add(new ConditionEntry(Arrays.asList(Operators.OR), "tournament_event_team_id", Arrays.asList(Operators.EQUAL), eTeamId));
			}
		}
		conditionEntries.add(new ConditionEntry(Arrays.asList(Operators.CLOSED_BRACKET), null, null, null));
		
		List<Model> deletedEventTeams = tournamentEventTeamDao.deleteAndReturn(conditionEntries, Arrays.asList("*"));
		
		return (deletedEventTeams == null)? new ArrayList<>() : deletedEventTeams;
	}
}
