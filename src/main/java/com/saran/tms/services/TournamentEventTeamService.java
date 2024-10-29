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
import com.saran.tms.models.TournamentEventTeamModel;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.OrderEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;

public class TournamentEventTeamService {

	public static TournamentEventTeamModel saveTournamentEventTeam(TournamentEventTeamModel tournamentEventTeam) throws ResponseException {
		Dao tournamentEventTeamDao = new Dao(TournamentEventTeamModel.class);
		TournamentEventTeamModel newTournamentEventTeam = (TournamentEventTeamModel) tournamentEventTeamDao.saveAndReturn(tournamentEventTeam, Arrays.asList("*"));
		return newTournamentEventTeam;
	}
	
	public static List<Model> saveAllTournamentEventTeam(List<Model> tournamentEventTeams) throws ResponseException {
		Dao tournamentEventTeamDao = new Dao(TournamentEventTeamModel.class);
		List<Model> newTournamentEventTeams = tournamentEventTeamDao.saveAllAndReturn(tournamentEventTeams, Arrays.asList("*"));
		return newTournamentEventTeams;
	}
	
	public static List<List<Model>> findTournamentEventTeams(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		Dao tournamentEventTeamDao = new Dao(TournamentEventTeamModel.class);
		
		List<ConditionEntry> eventTeamConditions = new ArrayList<>();
		List<ConditionEntry> teamConditions = new ArrayList<>();
		
		Long eventId = null;
		try {
			eventId = Long.parseLong(params.get("event_id"));
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid event id");
		}
		
		eventTeamConditions.add(new ConditionEntry(null, "tournament_event_id", Arrays.asList(Operators.EQUAL), eventId));
		
		String teamNames[] = queryParams.get("filter_teamname");
		if(teamNames != null && teamNames.length > 0) {
			teamConditions.add(new ConditionEntry(Arrays.asList(Operators.AND), "team_name", Arrays.asList(Operators.ILIKE), '%' + teamNames[0] + '%'));
		}
		
		String tournamentId = params.get("tournament_id");
		if(tournamentId != null) {
			teamConditions.add(new ConditionEntry(Arrays.asList(Operators.AND), "tournament_id", Arrays.asList(Operators.EQUAL), Long.parseLong(tournamentId)));
		}
		
		String teamStatuses[] = queryParams.get("filter_teamstatus");
		if(teamStatuses != null && teamStatuses.length > 0) {
			teamConditions.add(new ConditionEntry(Arrays.asList(Operators.AND), "team_status", Arrays.asList(Operators.EQUAL), Short.parseShort(teamStatuses[0])));
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
}
