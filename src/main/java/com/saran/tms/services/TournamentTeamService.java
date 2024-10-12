package com.saran.tms.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.saran.tms.dao.Dao;
import com.saran.tms.enums.Operators;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.models.Model;
import com.saran.tms.models.TournamentTeamModel;
import com.saran.tms.pojo.ConditionEntry;

public class TournamentTeamService {
	public static TournamentTeamModel saveTeam(TournamentTeamModel team) throws ResponseException {
		Dao teamDao = new Dao(TournamentTeamModel.class);
		TournamentTeamModel newTeam = (TournamentTeamModel) teamDao.saveAndReturn(team, Arrays.asList("*"));
		return newTeam;
	}
	
	public static TournamentTeamModel findTeamById(Map<String, String> params) throws ResponseException {
		Dao teamDao = new Dao(TournamentTeamModel.class);
		
		TournamentTeamModel team = (TournamentTeamModel) teamDao.findOne(
			Arrays.asList("*"),
			Arrays.asList(
				new ConditionEntry(null, "team_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("team_id"))),
				new ConditionEntry(Arrays.asList(Operators.AND), "tournament_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("tournament_id")))
			)
				
		);
		
		if(team == null) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Team not found");
		}
		
		return team;
	}
	
	
	public static List<Model> findTeams(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		
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
		
		List<Model> teams = teamDao.findAll(Arrays.asList("*"), conditions, limit, offset);
		
		if(teams == null || teams.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "No teams found");
		}
		
		return teams;
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
