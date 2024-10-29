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

public class TournamentTeamService {
	public static TournamentTeamModel saveTeam(TournamentTeamModel team) throws ResponseException {
		Dao teamDao = new Dao(TournamentTeamModel.class);
		TournamentTeamModel newTeam = (TournamentTeamModel) teamDao.saveAndReturn(team, Arrays.asList("*"));
		return newTeam;
	}
	
	public static List<Model> findTeamById(Map<String, String> params) throws ResponseException {
		Dao teamDao = new Dao(TournamentTeamModel.class);
		
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
						new ConditionEntry(null, "team_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("team_id"))),
						new ConditionEntry(Arrays.asList(Operators.AND), "tournament_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("tournament_id")))
					)	
				)
			)
		);
		
		return teamDetails;
	}
	
	
	public static List<List<Model>> findTeams(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		
		String needWinCount[] = queryParams.get("include_wincount");
		if(needWinCount != null && needWinCount.length > 0 && needWinCount[0].equals("true")) {
			return findTeamsWithWinCount(params, queryParams);
		}
		
		
		Dao teamDao = new Dao(TournamentTeamModel.class);
		
		Operators operator = null;
		
		List<ConditionEntry> conditions = new ArrayList<>();
		
		String teamNames[] = queryParams.get("filter_teamname");
		if(teamNames != null && teamNames.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "team_name", Arrays.asList(Operators.ILIKE), '%' + teamNames[0] + '%'));
			operator = Operators.AND;
		}
		
		String tournamentId = params.get("tournament_id");
		if(tournamentId != null) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "tournament_id", Arrays.asList(Operators.EQUAL), Long.parseLong(tournamentId)));
			operator = Operators.AND;
		}
		
		String teamStatuses[] = queryParams.get("filter_teamstatus");
		if(teamStatuses != null && teamStatuses.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "team_status", Arrays.asList(Operators.EQUAL), Short.parseShort(teamStatuses[0])));
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
	
	private static List<List<Model>> findTeamsWithWinCount(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		
		Dao teamDao = new Dao(TournamentTeamModel.class);
		
		Operators operator = null;
		
		List<ConditionEntry> conditions = new ArrayList<>();
		
		String teamNames[] = queryParams.get("filter_teamname");
		if(teamNames != null && teamNames.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "team_name", Arrays.asList(Operators.ILIKE), '%' + teamNames[0] + '%'));
			operator = Operators.AND;
		}
		
		String tournamentId = params.get("tournament_id");
		if(tournamentId != null) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "tournament_id", Arrays.asList(Operators.EQUAL), Long.parseLong(tournamentId)));
			operator = Operators.AND;
		}
		
		String teamStatuses[] = queryParams.get("filter_teamstatus");
		if(teamStatuses != null && teamStatuses.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "team_status", Arrays.asList(Operators.EQUAL), Short.parseShort(teamStatuses[0])));
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
	
	public static List<Model> findUserTeam(Map<String, String> params) throws ResponseException {
		Dao teamDao = new Dao(TournamentTeamModel.class);
		
		Long userId = Long.parseLong(params.get("user_id"));
		Long tournamentId = Long.parseLong(params.get("tournament_id"));
		
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
	
	public static Long getTeamCount(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		Dao teamDao = new Dao(TournamentTeamModel.class);
		
		Operators operator = null;
		
		List<ConditionEntry> conditions = new ArrayList<>();
		
		String teamNames[] = queryParams.get("filter_teamname");
		if(teamNames != null && teamNames.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "team_name", Arrays.asList(Operators.ILIKE), '%' + teamNames[0] + '%'));
			operator = Operators.AND;
		}
		
		String tournamentId = params.get("tournament_id");
		if(tournamentId != null) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "tournament_id", Arrays.asList(Operators.EQUAL), Long.parseLong(tournamentId)));
			operator = Operators.AND;
		}
		
		String teamStatuses[] = queryParams.get("filter_teamstatus");
		if(teamStatuses != null && teamStatuses.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "team_status", Arrays.asList(Operators.EQUAL), Short.parseShort(teamStatuses[0])));
			operator = Operators.AND;
		}
		
		Map<GroupEntry, Functions> fieldFunctions = new HashMap<>();
		fieldFunctions.put(new GroupEntry(TableNames.TOURNAMENT_TEAMS, "*"), Functions.COUNT);

		List<List<Model>> teamAggregateList = teamDao.findAll(Arrays.asList("*"), conditions, fieldFunctions, null, null);
		
		AggregateModel aggregateModel = (AggregateModel) teamAggregateList.get(0).get(0);
		
		return aggregateModel.getCount();
	}

	public static TournamentTeamModel updateTeamById(Map<String, String> params, TournamentTeamModel team) throws ResponseException {
		Dao teamDao = new Dao(TournamentTeamModel.class);
		List<Model> updatedTeams = teamDao.updateAndReturn(team, Arrays.asList(new ConditionEntry(null, "team_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("team_id")))), Arrays.asList("*"));
		if(updatedTeams == null || updatedTeams.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Team not found");
		}
		return (TournamentTeamModel) updatedTeams.get(0);
	}
	
	public static TournamentTeamModel deleteTeamById(Map<String, String> params) throws ResponseException {
		Dao teamDao = new Dao(TournamentTeamModel.class);
		List<Model> deletedTeams = teamDao.deleteAndReturn(Arrays.asList(new ConditionEntry(null, "team_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("team_id")))), Arrays.asList("*"));
		if(deletedTeams == null || deletedTeams.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Team not found");
		}
		return (TournamentTeamModel) deletedTeams.get(0);
	}
}
