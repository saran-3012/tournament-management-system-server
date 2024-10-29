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
import com.saran.tms.models.TournamentEventModel;
import com.saran.tms.pojo.ConditionEntry;

public class TournamentEventService {
	public static TournamentEventModel saveTournamentEvent(TournamentEventModel tournamentEvent) throws ResponseException {
		Dao tournamentEventDao = new Dao(TournamentEventModel.class);
		TournamentEventModel newTournamentEvent = (TournamentEventModel) tournamentEventDao.saveAndReturn(tournamentEvent, Arrays.asList("*"));
		return newTournamentEvent;
	}
	
	public static TournamentEventModel findTournamentEventById(Map<String, String> params) throws ResponseException {
		Dao tournamentEventDao = new Dao(TournamentEventModel.class);
		
		Long tournamentEventId = null;
		try {
			tournamentEventId = Long.parseLong(params.get("event_id"));
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid event id");
		}
		
		Long tournamentId = null;
		try {
			tournamentId = Long.parseLong(params.get("tournament_id"));
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
	
	public static List<Model> findTournamentEvents(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		Dao tournamentEventDao = new Dao(TournamentEventModel.class);
		
		List<ConditionEntry> conditions = new ArrayList<>();
		
		Long tournamentId = null;
		try {
			tournamentId = Long.parseLong(params.get("tournament_id"));
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid tournament id");
		}
		
		conditions.add(new ConditionEntry(null, "tournament_id", Arrays.asList(Operators.EQUAL), tournamentId));
		
		// Add more conditions here
		
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
		
		List<Model> tournamentEvents = tournamentEventDao.findAll(Arrays.asList("*"), conditions, limit, offset);
		
		return tournamentEvents;
		
	}
}
