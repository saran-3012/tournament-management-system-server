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
import com.saran.tms.enums.SortOrder;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.enums.TableNames;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.models.Model;
import com.saran.tms.models.TournamentModel;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.GroupEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.OrderEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.utils.Utilities;

public class TournamentService {
	public static TournamentModel saveTournament(TournamentModel tournament) throws ResponseException {
		Dao tournamentDao = new Dao(TournamentModel.class);
		TournamentModel newTournament = (TournamentModel) tournamentDao.saveAndReturn(tournament, Arrays.asList("*"));
		return newTournament;
	}
	
	public static List<Model> findTournamentById(Params params) throws ResponseException {
		Dao tournamentDao = new Dao(TournamentModel.class);
		
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
		
		Long organizationId;
		try {
			organizationId = params.getLong("org_id");
			if(organizationId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Organization id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid organization id");
		}
		
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
						new ConditionEntry(null, "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId),
						new ConditionEntry(Arrays.asList(Operators.AND), "organization_id", Arrays.asList(Operators.EQUAL), organizationId)
					)
				)
			)	
		);
		
		if(tournamentDetails == null || tournamentDetails.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Tournament not found");
		}
		
		return tournamentDetails;
	}
	
	public static List<Model> findTournamentByIdWithRegiteredCount(Params params) throws ResponseException {
	
		
		Dao tournamentDao = new Dao(TournamentModel.class);
		
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
		
		Long organizationId;
		try {
			organizationId = params.getLong("org_id");
			if(organizationId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Organization id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid organization id");
		}
		
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
				new JoinEntry(TableNames.TOURNAMENTS, TableNames.TOURNAMENT_PARTICIPANTS, "tournament_id", "tournament_id", JoinTypes.LEFT_JOIN),
				new JoinEntry(TableNames.TOURNAMENTS, TableNames.TOURNAMENT_TEAMS, "tournament_id", "tournament_id", JoinTypes.LEFT_JOIN)
					
			),
			Arrays.asList(
				new TableConditionEntry(TableNames.TOURNAMENTS, Arrays.asList(
						new ConditionEntry(null, "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId),
						new ConditionEntry(Arrays.asList(Operators.AND), "organization_id", Arrays.asList(Operators.EQUAL), organizationId)
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
	
	private static List<List<Model>> findTournamentsWithUserFilter(Params params, QueryParams queryParams) throws ResponseException {
		
		Dao tournamentDao = new Dao(TournamentModel.class);
		
		List<ConditionEntry> teamMemberConditions = new ArrayList<>();
		List<ConditionEntry> participantConditions = new ArrayList<>();
		
		Long userId;
		try {
			userId = queryParams.getLong("filter_userid");
			if(userId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "User id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid user id");
		}
		
		teamMemberConditions.add(new ConditionEntry(null, "user_id", Arrays.asList(Operators.EQUAL), userId));
		participantConditions.add(new ConditionEntry(Arrays.asList(Operators.OR), "user_id", Arrays.asList(Operators.EQUAL), userId));
		
		List<TableConditionEntry> tableConditionEntries = new ArrayList<>();
		
		if(!teamMemberConditions.isEmpty()) {
			tableConditionEntries.add(new TableConditionEntry(TableNames.TEAM_MEMBERS, teamMemberConditions));
		}
		
		if(!participantConditions.isEmpty()) {
			tableConditionEntries.add(new TableConditionEntry(TableNames.TOURNAMENT_PARTICIPANTS, participantConditions));
		}
		
		List<OrderEntry> sortEntries = new ArrayList<>();
		
		String sortCreatedAt = queryParams.get("sort_createdat");
		if(sortCreatedAt != null) {
			try {
				SortOrder sortOrder = SortOrder.getSortOrder(sortCreatedAt);
				sortEntries.add(new OrderEntry(TableNames.TOURNAMENTS, "tournament_created_at", sortOrder));
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
		}
		
		String sortRegistrationStart = queryParams.get("sort_registrationstart");
		if(sortRegistrationStart != null) {
			try {
				SortOrder sortOrder = SortOrder.getSortOrder(sortRegistrationStart);
				sortEntries.add(new OrderEntry(TableNames.TOURNAMENTS, "registration_start_date", sortOrder));
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
		}
		
		String sortRegistrationEnd = queryParams.get("sort_registrationend");
		if(sortRegistrationEnd != null) {
			try {
				SortOrder sortOrder = SortOrder.getSortOrder(sortRegistrationEnd);
				sortEntries.add(new OrderEntry(TableNames.TOURNAMENTS, "registration_end_date", sortOrder));
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
		}
		
		String sortTournamentDate = queryParams.get("sort_tournamentdate");
		if(sortTournamentDate != null) {
			try {
				SortOrder sortOrder = null;				
				sortEntries.add(new OrderEntry(TableNames.TOURNAMENTS, "tournament_date", sortOrder));
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
		}
		
		String sortTournamentName = queryParams.get("sort_tournamentname");
		if(sortTournamentName != null) {
			try {
				SortOrder sortOrder = SortOrder.getSortOrder(sortTournamentName);
				sortEntries.add(new OrderEntry(TableNames.TOURNAMENTS, "tournament_name", sortOrder));				
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
		}
		
		String sortSportName = queryParams.get("sort_sportname");
		if(sortSportName != null) {
			try {
				SortOrder sortOrder = SortOrder.getSortOrder(sortSportName);			
				sortEntries.add(new OrderEntry(TableNames.SPORTS, "sport_name", sortOrder));
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
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
		
		List<List<Model>> tournamentDetailsList = tournamentDao.findAllWithJoin(
				Arrays.asList(
					new TableColumnEntry(TableNames.TOURNAMENTS, Arrays.asList("*")),
					new TableColumnEntry(TableNames.SPORTS, Arrays.asList("*"))
				),
				Arrays.asList(
					new JoinEntry(TableNames.TOURNAMENTS, TableNames.SPORTS, "sport_id", "sport_id", JoinTypes.JOIN),
					new JoinEntry(TableNames.TOURNAMENTS, TableNames.TOURNAMENT_PARTICIPANTS, "tournament_id", "tournament_id", JoinTypes.LEFT_JOIN),
					new JoinEntry(TableNames.TOURNAMENTS, TableNames.TOURNAMENT_TEAMS, "tournament_id", "tournament_id", JoinTypes.LEFT_JOIN),
					new JoinEntry(TableNames.TOURNAMENT_TEAMS, TableNames.TEAM_MEMBERS, "team_id", "team_id", JoinTypes.JOIN)
				),
				tableConditionEntries,
				sortEntries,
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
	
	private static List<List<Model>> searchTournaments(Params params, QueryParams queryParams) throws ResponseException {
		Dao tournamentDao = new Dao(TournamentModel.class);

		
		Long organizationId;
		try {
			organizationId = params.getLong("org_id");
			if(organizationId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Organization id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Organiation id is not valid");
		}
		
		
		String tournamentSearchValue = queryParams.get("filter_tournament");
		
		
		List<OrderEntry> sortEntries = new ArrayList<>();
		
		String sortCreatedAt = queryParams.get("sort_createdat");
		if(sortCreatedAt != null) {
			try {
				SortOrder sortOrder = SortOrder.getSortOrder(sortCreatedAt);
				sortEntries.add(new OrderEntry(TableNames.TOURNAMENTS, "tournament_created_at", sortOrder));
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
		}
		
		String sortRegistrationStart = queryParams.get("sort_registrationstart");
		if(sortRegistrationStart != null) {
			try {
				SortOrder sortOrder = SortOrder.getSortOrder(sortRegistrationStart);
				sortEntries.add(new OrderEntry(TableNames.TOURNAMENTS, "registration_start_date", sortOrder));
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
		}
		
		String sortRegistrationEnd = queryParams.get("sort_registrationend");
		if(sortRegistrationEnd != null) {
			try {
				SortOrder sortOrder = SortOrder.getSortOrder(sortRegistrationEnd);
				sortEntries.add(new OrderEntry(TableNames.TOURNAMENTS, "registration_end_date", sortOrder));
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
		}
		
		String sortTournamentDate = queryParams.get("sort_tournamentdate");
		if(sortTournamentDate != null) {
			try {
				SortOrder sortOrder = null;				
				sortEntries.add(new OrderEntry(TableNames.TOURNAMENTS, "tournament_date", sortOrder));
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
		}
		
		String sortTournamentName = queryParams.get("sort_tournamentname");
		if(sortTournamentName != null) {
			try {
				SortOrder sortOrder = SortOrder.getSortOrder(sortTournamentName);
				sortEntries.add(new OrderEntry(TableNames.TOURNAMENTS, "tournament_name", sortOrder));				
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
		}
		
		String sortSportName = queryParams.get("sort_sportname");
		if(sortSportName != null) {
			try {
				SortOrder sortOrder = SortOrder.getSortOrder(sortSportName);			
				sortEntries.add(new OrderEntry(TableNames.SPORTS, "sport_name", sortOrder));
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
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
							new ConditionEntry(null, "organization_id", Arrays.asList(Operators.EQUAL), organizationId),
							// tournament conditions
							
							new ConditionEntry(Arrays.asList(Operators.AND, Operators.OPEN_BRACKET), "tournament_name", Arrays.asList(Operators.ILIKE), '%' + tournamentSearchValue + '%')
						)
					),
					new TableConditionEntry(
						TableNames.SPORTS,
						Arrays.asList(
							new ConditionEntry(Arrays.asList(Operators.OR), "sport_name", Arrays.asList(Operators.ILIKE), '%' + tournamentSearchValue + '%'),
							new ConditionEntry(Arrays.asList(Operators.CLOSED_BRACKET), null, null, null) // Replace this for sport conditions
						)
					)
					
			),
			sortEntries,
			limit,
			offset
		);

		return tournamentDetailsList;
	}
	
	public static List<List<Model>> findTournaments(Params params, QueryParams queryParams) throws ResponseException {
	
		String userId = queryParams.get("filter_userid");
		
		if(userId != null) {
			return findTournamentsWithUserFilter(params, queryParams);
		}
		
		String tournament = queryParams.get("filter_tournament");
		if(tournament != null) {
			return searchTournaments(params, queryParams);
		}
		
		Dao tournamentDao = new Dao(TournamentModel.class);

		
		
		List<ConditionEntry> tournamentConditions = new ArrayList<>();
		
		Long organizationId;
		try {
			organizationId = params.getLong("org_id");
			if(organizationId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Organization id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Organiation id is not valid");
		}
		tournamentConditions.add(new ConditionEntry(null, "organization_id", Arrays.asList(Operators.EQUAL), organizationId));
		
		Operators operator = Operators.AND;
		
		String tournamentName = queryParams.get("filter_tournamentname");
		if(tournamentName != null) {
			tournamentConditions.add(new ConditionEntry(Arrays.asList(operator), "tournament_name", Arrays.asList(Operators.ILIKE), '%' + tournamentName + '%'));
		}
		
		try {
			Short tournamentStatus = queryParams.getShort("filter_tournamentstatus");
			if(tournamentStatus != null) {
				tournamentConditions.add(new ConditionEntry(Arrays.asList(operator), "tournament_status", Arrays.asList(Operators.EQUAL), tournamentStatus));
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament status");
		}
		
		List<ConditionEntry> sportConditions = new ArrayList<>();
		
		String sportName = queryParams.get("filter_sportname");
		if(sportName != null) {
			sportConditions.add(new ConditionEntry(Arrays.asList(operator), "sport_name", Arrays.asList(Operators.ILIKE), '%' + sportName + '%'));
		}
		
		try {
			Short sportType = queryParams.getShort("filter_sporttype");
			if(sportType != null) {
				sportConditions.add(new ConditionEntry(Arrays.asList(operator), "sport_type", Arrays.asList(Operators.EQUAL), sportType));
			}			
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament status");
		}

		List<TableConditionEntry> tableConditionEntries = new ArrayList<>();
		if(!tournamentConditions.isEmpty()) {
			tableConditionEntries.add(new TableConditionEntry(TableNames.TOURNAMENTS, tournamentConditions));
		}
		
		if(!sportConditions.isEmpty()) {
			tableConditionEntries.add(new TableConditionEntry(TableNames.SPORTS, sportConditions));
		}
		
		List<OrderEntry> sortEntries = new ArrayList<>();
		
		String sortCreatedAt = queryParams.get("sort_createdat");
		if(sortCreatedAt != null) {
			try {
				SortOrder sortOrder = SortOrder.getSortOrder(sortCreatedAt);
				sortEntries.add(new OrderEntry(TableNames.TOURNAMENTS, "tournament_created_at", sortOrder));
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
		}
		
		String sortRegistrationStart = queryParams.get("sort_registrationstart");
		if(sortRegistrationStart != null) {
			try {
				SortOrder sortOrder = SortOrder.getSortOrder(sortRegistrationStart);
				sortEntries.add(new OrderEntry(TableNames.TOURNAMENTS, "registration_start_date", sortOrder));
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
		}
		
		String sortRegistrationEnd = queryParams.get("sort_registrationend");
		if(sortRegistrationEnd != null) {
			try {
				SortOrder sortOrder = SortOrder.getSortOrder(sortRegistrationEnd);
				sortEntries.add(new OrderEntry(TableNames.TOURNAMENTS, "registration_end_date", sortOrder));
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
		}
		
		String sortTournamentDate = queryParams.get("sort_tournamentdate");
		if(sortTournamentDate != null) {
			try {
				SortOrder sortOrder = null;				
				sortEntries.add(new OrderEntry(TableNames.TOURNAMENTS, "tournament_date", sortOrder));
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
		}
		
		String sortTournamentName = queryParams.get("sort_tournamentname");
		if(sortTournamentName != null) {
			try {
				SortOrder sortOrder = SortOrder.getSortOrder(sortTournamentName);
				sortEntries.add(new OrderEntry(TableNames.TOURNAMENTS, "tournament_name", sortOrder));				
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
		}
		
		String sortSportName = queryParams.get("sort_sportname");
		if(sortSportName != null) {
			try {
				SortOrder sortOrder = SortOrder.getSortOrder(sortSportName);			
				sortEntries.add(new OrderEntry(TableNames.SPORTS, "sport_name", sortOrder));
			}
			catch(IllegalArgumentException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sorting option");
			}
			catch(NullPointerException e) {}
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
		
		List<List<Model>> tournamentDetailsList = tournamentDao.findAllWithJoin(
			Arrays.asList(
				new TableColumnEntry(TableNames.TOURNAMENTS, Arrays.asList("*")),
				new TableColumnEntry(TableNames.SPORTS, Arrays.asList("*"))
			),
			Arrays.asList(
				new JoinEntry(TableNames.TOURNAMENTS, TableNames.SPORTS, "sport_id", "sport_id", JoinTypes.JOIN)
			),
			tableConditionEntries,
			sortEntries,
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

	public static TournamentModel updateTournamentById(Params params, TournamentModel tournament) throws ResponseException {
		Dao tournamentDao = new Dao(TournamentModel.class);
		
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
		
		List<Model> updatedTournaments = tournamentDao.updateAndReturn(
											tournament, 
											Arrays.asList(
													new ConditionEntry(null, "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId)
											), 
											Arrays.asList("*")
										);
		if(updatedTournaments == null || updatedTournaments.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Tournament not found");
		}
		return (TournamentModel) updatedTournaments.get(0);
	}
	
	public static TournamentModel deleteTournamentById(Params params) throws ResponseException {
		Dao tournamentDao = new Dao(TournamentModel.class);
		
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
		
		List<Model> deletedTournaments = tournamentDao.deleteAndReturn(Arrays.asList(new ConditionEntry(null, "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId)), Arrays.asList("*"));
		if(deletedTournaments == null || deletedTournaments.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Tournament not found");
		}
		return (TournamentModel) deletedTournaments.get(0);
	}
}
