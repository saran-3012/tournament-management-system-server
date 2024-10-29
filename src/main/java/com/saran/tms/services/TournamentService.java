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
import com.saran.tms.models.Model;
import com.saran.tms.models.TournamentModel;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.GroupEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;
import com.saran.tms.routers.ResponseData;

public class TournamentService {
	public static TournamentModel saveTournament(TournamentModel tournament) throws ResponseException {
		Dao tournamentDao = new Dao(TournamentModel.class);
		TournamentModel newTournament = (TournamentModel) tournamentDao.saveAndReturn(tournament, Arrays.asList("*"));
		return newTournament;
	}
	
	public static List<Model> findTournamentById(Map<String, String> params) throws ResponseException {
		Dao tournamentDao = new Dao(TournamentModel.class);
		
		List<Model> tournamentDetails = tournamentDao.findOneWithJoin(
			Arrays.asList(
				new TableColumnEntry(TableNames.TOURNAMENTS, Arrays.asList("*")),
				new TableColumnEntry(TableNames.SPORTS, Arrays.asList("*"))
			),
			Arrays.asList(
				new JoinEntry(TableNames.TOURNAMENTS, TableNames.SPORTS, "sport_id", "sport_id", JoinTypes.JOIN)
			),
			Arrays.asList(
				new TableConditionEntry(TableNames.TOURNAMENTS, Arrays.asList(
						new ConditionEntry(null, "tournament_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("tournament_id"))),
						new ConditionEntry(Arrays.asList(Operators.AND), "organization_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("org_id")))
					)
				)
			)	
		);
		
		if(tournamentDetails == null || tournamentDetails.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Tournament not found");
		}
		
		return tournamentDetails;
	}
	
	public static List<Model> findTournamentByIdWithRegiteredCount(Map<String, String> params) throws ResponseException {
	
		
		Dao tournamentDao = new Dao(TournamentModel.class);
		
		Map<GroupEntry, Functions> fieldFunctions = new HashMap<>();
		fieldFunctions.put(new GroupEntry(TableNames.TOURNAMENT_PARTICIPANTS, "*") , Functions.COUNT);
		fieldFunctions.put(new GroupEntry(TableNames.TOURNAMENT_TEAMS, "*"), Functions.COUNT);

		
		List<List<Model>> tournamentDetails = tournamentDao.findAllWithJoin(
			Arrays.asList(
				new TableColumnEntry(TableNames.TOURNAMENTS, Arrays.asList("*")),
				new TableColumnEntry(TableNames.SPORTS, Arrays.asList("*")),
				new TableColumnEntry(TableNames.TOURNAMENT_PARTICIPANTS, Arrays.asList("*")),
				new TableColumnEntry(TableNames.TOURNAMENT_TEAMS, Arrays.asList("*"))
			),
			Arrays.asList(
				new JoinEntry(TableNames.TOURNAMENTS, TableNames.SPORTS, "sport_id", "sport_id", JoinTypes.JOIN),
				new JoinEntry(TableNames.TOURNAMENTS, TableNames.TOURNAMENT_TEAMS, "tournament_id", "tournament_id", JoinTypes.LEFT_JOIN),
				new JoinEntry(TableNames.TOURNAMENTS, TableNames.TOURNAMENT_PARTICIPANTS, "tournament_id", "tournament_id", JoinTypes.LEFT_JOIN)
					
			),
			Arrays.asList(
				new TableConditionEntry(TableNames.TOURNAMENTS, Arrays.asList(
						new ConditionEntry(null, "tournament_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("tournament_id"))),
						new ConditionEntry(Arrays.asList(Operators.AND), "organization_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("org_id")))
					)
				)
			),
			fieldFunctions,
			Arrays.asList(
				new GroupEntry(TableNames.TOURNAMENTS, "tournament_id"),
				new GroupEntry(TableNames.SPORTS, "sport_id")
			),
			null, null
		);
		
		if(tournamentDetails == null || tournamentDetails.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Tournament not found");
		}
		
		return tournamentDetails.get(0);
	}
	
	private static List<List<Model>> findTournamentsWithUserFilter(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		
		Dao tournamentDao = new Dao(TournamentModel.class);
		
		List<ConditionEntry> teamMemberConditions = new ArrayList<>();
		List<ConditionEntry> participantConditions = new ArrayList<>();
		
		String userIds[] = queryParams.get("filter_userid");
		if(userIds == null || userIds.length == 0) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "User id is not provided");
		}
		
		Long userId = Long.parseLong(userIds[0]);
		teamMemberConditions.add(new ConditionEntry(null, "user_id", Arrays.asList(Operators.EQUAL), userId));
		participantConditions.add(new ConditionEntry(Arrays.asList(Operators.OR), "user_id", Arrays.asList(Operators.EQUAL), userId));
		
		List<TableConditionEntry> tableConditionEntries = new ArrayList<>();
		
		if(!teamMemberConditions.isEmpty()) {
			tableConditionEntries.add(new TableConditionEntry(TableNames.TEAM_MEMBERS, teamMemberConditions));
		}
		
		if(!participantConditions.isEmpty()) {
			tableConditionEntries.add(new TableConditionEntry(TableNames.TOURNAMENT_PARTICIPANTS, participantConditions));
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
		
		List<List<Model>> tournamentDetailsList = tournamentDao.findAllWithJoin(
				Arrays.asList(
					new TableColumnEntry(TableNames.TOURNAMENTS, Arrays.asList("*")),
					new TableColumnEntry(TableNames.SPORTS, Arrays.asList("*"))
				),
				Arrays.asList(
					new JoinEntry(TableNames.TOURNAMENTS, TableNames.SPORTS, "sport_id", "sport_id", JoinTypes.JOIN),
					new JoinEntry(TableNames.TOURNAMENTS, TableNames.TOURNAMENT_PARTICIPANTS, "tournament_id", "tournament_id", JoinTypes.JOIN),
					new JoinEntry(TableNames.TOURNAMENTS, TableNames.TOURNAMENT_TEAMS, "tournament_id", "tournament_id", JoinTypes.JOIN),
					new JoinEntry(TableNames.TOURNAMENT_TEAMS, TableNames.TEAM_MEMBERS, "team_id", "team_id", JoinTypes.JOIN)
				),
				tableConditionEntries,
				limit,
				offset
			);
		
		if(tournamentDetailsList == null || tournamentDetailsList.isEmpty()) {
			return new ArrayList<>();
		}
		
		if(tournamentDetailsList.get(0) == null || tournamentDetailsList.get(0).isEmpty()) {
			return new ArrayList<>();
		}
		
		return tournamentDetailsList;
	}
	
	private static List<List<Model>> searchTournaments(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		Dao tournamentDao = new Dao(TournamentModel.class);

		
		String organizationId = params.get("org_id");
		Long orgId = null;
		try {
			orgId = Long.parseLong(organizationId);
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Organiation id is not valid");
		}
		
		
		String tournamentSearchValues[] = queryParams.get("filter_tournament");
		String tournamentSearchValue = null;
		
		if(tournamentSearchValues != null && tournamentSearchValues.length > 0) {
			tournamentSearchValue = tournamentSearchValues[0];
		}
		
		if(tournamentSearchValue == null) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Organiation id cannot be null");
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
		
		List<List<Model>> tournamentDetailsList = tournamentDao.findAllWithJoin(
			Arrays.asList(
				new TableColumnEntry(TableNames.TOURNAMENTS, Arrays.asList("*")),
				new TableColumnEntry(TableNames.SPORTS, Arrays.asList("*"))
			),
			Arrays.asList(
				new JoinEntry(TableNames.TOURNAMENTS, TableNames.SPORTS, "sport_id", "sport_id", JoinTypes.JOIN)
			),
			Arrays.asList(
					new TableConditionEntry(
						TableNames.TOURNAMENTS, 
						Arrays.asList(
							new ConditionEntry(null, "organization_id", Arrays.asList(Operators.EQUAL), orgId),
							new ConditionEntry(Arrays.asList(Operators.AND), "tournament_name", Arrays.asList(Operators.ILIKE), '%' + tournamentSearchValue + '%'),
							new ConditionEntry(Arrays.asList(Operators.OR), "organization_id", Arrays.asList(Operators.EQUAL), orgId)
						)
					),
					new TableConditionEntry(
						TableNames.SPORTS,
						Arrays.asList(
							new ConditionEntry(Arrays.asList(Operators.AND), "sport_name", Arrays.asList(Operators.ILIKE), '%' + tournamentSearchValue + '%')
						)
					)			
			),
			limit,
			offset
		);

		return tournamentDetailsList;
	}
	
	public static List<List<Model>> findTournaments(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
	
		String userIds[] = queryParams.get("filter_userid");
		
		if(userIds != null && userIds.length > 0) {
			return findTournamentsWithUserFilter(params, queryParams);
		}
		
		String tournaments[] = queryParams.get("filter_tournament");
		if(tournaments != null && tournaments.length > 0) {
			return searchTournaments(params, queryParams);
		}
		
		Dao tournamentDao = new Dao(TournamentModel.class);

		Operators operator = null;
		
		List<ConditionEntry> tournamentConditions = new ArrayList<>();
		
		String organizationId = params.get("org_id");
		if(organizationId != null) {
			tournamentConditions.add(new ConditionEntry(Arrays.asList(operator), "organization_id", Arrays.asList(Operators.EQUAL), Long.parseLong(organizationId)));
			operator = Operators.AND;
		}
		
		String tournamentNames[] = queryParams.get("filter_tournamentname");
		if(tournamentNames != null && tournamentNames.length > 0) {
			tournamentConditions.add(new ConditionEntry(Arrays.asList(operator), "tournament_name", Arrays.asList(Operators.ILIKE), '%' + tournamentNames[0] + '%'));
			operator = Operators.AND;
		}
		
		List<ConditionEntry> sportConditions = new ArrayList<>();
		
		String sportNames[] = queryParams.get("filter_sportname");
		if(sportNames != null && sportNames.length > 0) {
			sportConditions.add(new ConditionEntry(Arrays.asList(operator), "sport_name", Arrays.asList(Operators.ILIKE), '%' + sportNames[0] + '%'));
			operator = Operators.AND;
		}
		
		String sportTypes[] = queryParams.get("filter_sporttype");
		if(sportTypes != null && sportTypes.length > 0) {
			sportConditions.add(new ConditionEntry(Arrays.asList(operator), "sport_type", Arrays.asList(Operators.EQUAL), Short.parseShort(sportTypes[0])));
		}

		List<TableConditionEntry> tableConditionEntries = new ArrayList<>();
		if(!tournamentConditions.isEmpty()) {
			tableConditionEntries.add(new TableConditionEntry(TableNames.TOURNAMENTS, tournamentConditions));
		}
		
		if(!sportConditions.isEmpty()) {
			tableConditionEntries.add(new TableConditionEntry(TableNames.SPORTS, sportConditions));
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
		
		List<List<Model>> tournamentDetailsList = tournamentDao.findAllWithJoin(
			Arrays.asList(
				new TableColumnEntry(TableNames.TOURNAMENTS, Arrays.asList("*")),
				new TableColumnEntry(TableNames.SPORTS, Arrays.asList("*"))
			),
			Arrays.asList(
				new JoinEntry(TableNames.TOURNAMENTS, TableNames.SPORTS, "sport_id", "sport_id", JoinTypes.JOIN)
			),
			tableConditionEntries,
			limit,
			offset
		);
		
		if(tournamentDetailsList == null || tournamentDetailsList.isEmpty()) {
			return new ArrayList<>();
		}
		
		if(tournamentDetailsList.get(0) == null || tournamentDetailsList.get(0).isEmpty()) {
			return new ArrayList<>();
		}
		
		return tournamentDetailsList;
	}

	public static TournamentModel updateTournamentById(Map<String, String> params, TournamentModel tournament) throws ResponseException {
		Dao tournamentDao = new Dao(TournamentModel.class);
		List<Model> updatedTournaments = tournamentDao.updateAndReturn(
											tournament, 
											Arrays.asList(
													new ConditionEntry(null, "tournament_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("tournament_id")))
											), 
											Arrays.asList("*")
										);
		if(updatedTournaments == null || updatedTournaments.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Tournament not found");
		}
		return (TournamentModel) updatedTournaments.get(0);
	}
	
	public static TournamentModel deleteTournamentById(Map<String, String> params) throws ResponseException {
		Dao tournamentDao = new Dao(TournamentModel.class);
		List<Model> deletedTournaments = tournamentDao.deleteAndReturn(Arrays.asList(new ConditionEntry(null, "tournament_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("tournament_id")))), Arrays.asList("*"));
		if(deletedTournaments == null || deletedTournaments.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Tournament not found");
		}
		return (TournamentModel) deletedTournaments.get(0);
	}
}
