package com.saran.tms.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.saran.tms.dao.Dao;
import com.saran.tms.enums.Functions;
import com.saran.tms.enums.JoinTypes;
import com.saran.tms.enums.Operators;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.enums.TableNames;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.models.AggregateModel;
import com.saran.tms.models.Model;
import com.saran.tms.models.TournamentTeamModel;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.GroupEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.utils.Utilities;

public class TournamentTeamService {
	public static TournamentTeamModel saveTeam(TournamentTeamModel team) throws ResponseException {
		Dao teamDao = new Dao(TournamentTeamModel.class);
		TournamentTeamModel newTeam = (TournamentTeamModel) teamDao.saveAndReturn(team, Arrays.asList("*"));
		return newTeam;
	}
	
	public static List<Model> findTeamById(Params params) throws ResponseException {
		Dao teamDao = new Dao(TournamentTeamModel.class);
		
		Long teamId;
		try {
			teamId = params.getLong("team_id");
			if(teamId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Team id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid team id");
		}
		
		Long tournamentId;
		try {
			tournamentId = params.getLong("tournament_id");
			if(tournamentId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Tournament id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
		
		List<Model> teamDetails = teamDao.findOneWithJoin(
			Arrays.asList(
				new TableColumnEntry(TableNames.TOURNAMENT_TEAMS, Arrays.asList("*")),
				new TableColumnEntry(TableNames.USERS, Arrays.asList("user_name"))
			), 
			Arrays.asList(
				new JoinEntry(TableNames.TOURNAMENT_TEAMS, TableNames.USERS, "team_leader_id", "user_id", JoinTypes.JOIN)
			),
			Arrays.asList(
				new TableConditionEntry(TableNames.TOURNAMENT_TEAMS, Arrays.asList(
						new ConditionEntry(null, "team_id", Arrays.asList(Operators.EQUAL), teamId),
						new ConditionEntry(Arrays.asList(Operators.AND), "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId)
					)	
				)
			)
		);
		
		return teamDetails;
	}
	
	
	public static List<List<Model>> findTeams(Params params, QueryParams queryParams) throws ResponseException {
		
		Boolean needWinCount = queryParams.getBoolean("include_wincount");
		if(needWinCount != null && needWinCount) {
			return findTeamsWithWinCount(params, queryParams);
		}
		
		
		Dao teamDao = new Dao(TournamentTeamModel.class);
		
		List<ConditionEntry> conditions = new ArrayList<>();
		
		Long tournamentId;
		try {
			tournamentId = params.getLong("tournament_id");
			if(tournamentId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Tournament id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
		conditions.add(new ConditionEntry(null, "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId));
		Operators operator = Operators.AND;
		
		
		String teamName = queryParams.get("filter_teamname");
		if(teamName != null) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "team_name", Arrays.asList(Operators.ILIKE), '%' + teamName + '%'));
		}
	
		
		try {
			Short teamStatus = queryParams.getShort("filter_teamstatus");
			if(teamStatus != null) {
				conditions.add(new ConditionEntry(Arrays.asList(operator), "team_status", Arrays.asList(Operators.EQUAL), teamStatus));
			}			
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid team status");
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
		
		
		List<List<Model>> teamDetailsList = teamDao.findAllWithJoin(
			Arrays.asList(
				new TableColumnEntry(TableNames.TOURNAMENT_TEAMS, Arrays.asList("*")),
				new TableColumnEntry(TableNames.USERS, Arrays.asList("user_name"))
			), 
			Arrays.asList(
				new JoinEntry(TableNames.TOURNAMENT_TEAMS, TableNames.USERS, "team_leader_id", "user_id", JoinTypes.JOIN)
			), 
			Arrays.asList(
				new TableConditionEntry(TableNames.TOURNAMENT_TEAMS, conditions)
			), 
			limit, offset);
		
		if(teamDetailsList == null) {
			return new ArrayList<>();
		}
		
		return teamDetailsList;
	}
	
	private static List<List<Model>> findTeamsWithWinCount(Params params, QueryParams queryParams) throws ResponseException {
		
		Dao teamDao = new Dao(TournamentTeamModel.class);
		
		List<ConditionEntry> conditions = new ArrayList<>();
		
		Long tournamentId;
		try {
			tournamentId = params.getLong("tournament_id");
			if(tournamentId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Tournament id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
		conditions.add(new ConditionEntry(null, "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId));
		Operators operator = Operators.AND;
		
		
		String teamName = queryParams.get("filter_teamname");
		if(teamName != null) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "team_name", Arrays.asList(Operators.ILIKE), '%' + teamName + '%'));
		}
	
		
		try {
			Short teamStatus = queryParams.getShort("filter_teamstatus");
			if(teamStatus != null) {
				conditions.add(new ConditionEntry(Arrays.asList(operator), "team_status", Arrays.asList(Operators.EQUAL), teamStatus));
			}			
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid team status");
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
		
		List<List<Model>> teamDetailsList = teamDao.findAllWithJoin(
			Arrays.asList(
				new TableColumnEntry(TableNames.TOURNAMENT_TEAMS, Arrays.asList("*")),
				new TableColumnEntry(TableNames.USERS, Arrays.asList("user_name")),
				new TableColumnEntry(TableNames.TOURNAMENT_EVENTS, Arrays.asList("*"))
			), 
			Arrays.asList(
				new JoinEntry(TableNames.TOURNAMENT_TEAMS, TableNames.USERS, "team_leader_id", "user_id", JoinTypes.JOIN),
				new JoinEntry(TableNames.TOURNAMENT_TEAMS, TableNames.TOURNAMENT_EVENTS, "team_id", "tournament_event_winner_id", JoinTypes.LEFT_JOIN)
			), 
			Arrays.asList(
				new TableConditionEntry(TableNames.TOURNAMENT_TEAMS, conditions),
				new TableConditionEntry(TableNames.TOURNAMENT_EVENTS,
					Arrays.asList(
						new ConditionEntry(Arrays.asList(operator), "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId)	
					)
				)
			), 
			fieldFunctions,
			Arrays.asList(
				new GroupEntry(TableNames.TOURNAMENT_TEAMS, "team_id")
			), limit, offset);
		
		if(teamDetailsList == null) {
			return new ArrayList<>();
		}
		
		return teamDetailsList;
	}
	
	public static List<Model> findUserTeam(Params params) throws ResponseException {
		Dao teamDao = new Dao(TournamentTeamModel.class);
		
		Long userId = Long.parseLong(params.get("user_id"));
		try {
			userId = params.getLong("user_id");
			if(userId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "User id is not provided");
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
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
		
		List<Model> teamDetails = teamDao.findOneWithJoin(
				Arrays.asList(
					new TableColumnEntry(TableNames.TOURNAMENT_TEAMS, Arrays.asList("*")),
					new TableColumnEntry(TableNames.USERS, Arrays.asList("user_name"))
				), 
				Arrays.asList(
					new JoinEntry(TableNames.TOURNAMENT_TEAMS, TableNames.TEAM_MEMBERS, "team_id", "team_id", JoinTypes.JOIN),
					new JoinEntry(TableNames.TOURNAMENT_TEAMS, TableNames.USERS, "team_leader_id", "user_id", JoinTypes.JOIN)
				), 
				Arrays.asList(
					new TableConditionEntry(TableNames.TOURNAMENT_TEAMS,
						Arrays.asList(
							new ConditionEntry(null, "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId)
						)
					),
					new TableConditionEntry(TableNames.TEAM_MEMBERS, 
						Arrays.asList(
							new ConditionEntry(Arrays.asList(Operators.AND), "user_id", Arrays.asList(Operators.EQUAL), userId)
						)
					)
				));
		
		return teamDetails;
		
	}
	
	public static Long getTeamCount(Params params, QueryParams queryParams) throws ResponseException {
		Dao teamDao = new Dao(TournamentTeamModel.class);
		
		List<ConditionEntry> conditions = new ArrayList<>();
		Long tournamentId;
		try {
			tournamentId = params.getLong("tournament_id");
			if(tournamentId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Tournament id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
		conditions.add(new ConditionEntry(null, "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId));
		Operators operator = Operators.AND;
		
		
		String teamName = queryParams.get("filter_teamname");
		if(teamName != null) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "team_name", Arrays.asList(Operators.ILIKE), '%' + teamName + '%'));
		}
	
		
		try {
			Short teamStatus = queryParams.getShort("filter_teamstatus");
			if(teamStatus != null) {
				conditions.add(new ConditionEntry(Arrays.asList(operator), "team_status", Arrays.asList(Operators.EQUAL), teamStatus));
			}			
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid team status");
		}
		
		Map<GroupEntry, Functions> fieldFunctions = new HashMap<>();
		fieldFunctions.put(new GroupEntry(TableNames.TOURNAMENT_TEAMS, "*"), Functions.COUNT);

		List<List<Model>> teamAggregateList = teamDao.findAll(Arrays.asList("*"), conditions, fieldFunctions, null, null);
		
		AggregateModel aggregateModel = (AggregateModel) teamAggregateList.get(0).get(0);
		
		return aggregateModel.getCount();
	}

	public static TournamentTeamModel updateTeamById(Params params, TournamentTeamModel team) throws ResponseException {
		Dao teamDao = new Dao(TournamentTeamModel.class);
		
		Long teamId;
		try {
			teamId = params.getLong("team_id");
			if(teamId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Team id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid team id");
		}
		
		List<Model> updatedTeams = teamDao.updateAndReturn(team, Arrays.asList(new ConditionEntry(null, "team_id", Arrays.asList(Operators.EQUAL), teamId)), Arrays.asList("*"));
		if(updatedTeams == null || updatedTeams.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Team not found");
		}
		return (TournamentTeamModel) updatedTeams.get(0);
	}
	
	public static TournamentTeamModel deleteTeamById(Params params) throws ResponseException {
		Dao teamDao = new Dao(TournamentTeamModel.class);
		
		Long teamId;
		try {
			teamId = params.getLong("team_id");
			if(teamId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Team id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid team id");
		}
		
		List<Model> deletedTeams = teamDao.deleteAndReturn(Arrays.asList(new ConditionEntry(null, "team_id", Arrays.asList(Operators.EQUAL), teamId)), Arrays.asList("*"));
		if(deletedTeams == null || deletedTeams.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Team not found");
		}
		return (TournamentTeamModel) deletedTeams.get(0);
	}
}
