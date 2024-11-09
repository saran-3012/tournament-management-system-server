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
import com.saran.tms.models.SportModel;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.utils.Utilities;

public class SportService {
	public static SportModel saveSport(SportModel sport) throws ResponseException {
		Dao sportDao = new Dao(SportModel.class);
		SportModel newSport = (SportModel) sportDao.saveAndReturn(sport, Arrays.asList("*"));
		return newSport;
	}
	
	public static SportModel findSportById(Params params) throws ResponseException {
		Dao sportDao = new Dao(SportModel.class);
		Long sportId;
		try {
			sportId = params.getLong("sport_id");
			if(sportId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Sport id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sport id");
		}
		SportModel sport = (SportModel) sportDao.findOne(
				Arrays.asList("*"), 
				Arrays.asList(
						new ConditionEntry(null, "sport_id", Arrays.asList(Operators.EQUAL), sportId)
				)
			);
		if(sport == null) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Sport not found");
		}
		return sport;
	}
	
	public static SportModel findSport(SportModel sport) throws ResponseException {
		Dao sportDao = new Dao(SportModel.class);
		SportModel reqSport = (SportModel) sportDao.findOne(
				Arrays.asList("*"), 
				Arrays.asList(
					new ConditionEntry(null, "sport_name", Arrays.asList(Operators.ILIKE), sport.getSportName()),
					new ConditionEntry(Arrays.asList(Operators.AND), "sport_type", Arrays.asList(Operators.EQUAL), sport.getSportType()),
					new ConditionEntry(Arrays.asList(Operators.AND), "team_size", Arrays.asList(Operators.EQUAL), sport.getTeamSize())
				)
			);
		
		return reqSport;
	}
	
	public static List<Model> findSports(Params params, QueryParams queryParams) throws ResponseException {
		Dao sportDao = new Dao(SportModel.class);
		
		List<ConditionEntry> conditions = new ArrayList<>();
		
		Operators operator = null;
		
		String sportName = queryParams.get("filter_sportname");
		if(sportName != null) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "sport_name", Arrays.asList(Operators.ILIKE), '%' + sportName + '%'));
			operator = Operators.AND;
		}
		
		try {
			Short sportType = queryParams.getShort("filter_sporttype");
			if(sportType != null) {
				conditions.add(new ConditionEntry(Arrays.asList(operator), "sport_type", Arrays.asList(Operators.EQUAL), sportType));
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sport type");
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
		
		List<Model> sports = sportDao.findAll(Arrays.asList("*"), conditions, limit, offset);
		
		if(sports == null || sports.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Sports not found");
		}
		return sports;
	}

	public static SportModel updateSportById(Params params, SportModel sport) throws ResponseException {
		Dao sportDao = new Dao(SportModel.class);
		
		Long sportId;
		try {
			sportId = params.getLong("sport_id");
			if(sportId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Sport id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sport id");
		}
		
		List<Model> updatedSports = sportDao.updateAndReturn(sport, Arrays.asList(new ConditionEntry(null, "sport_id", Arrays.asList(Operators.EQUAL), sportId)), Arrays.asList("*"));
		if(updatedSports == null || updatedSports.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Sport not found");
		}
		return (SportModel) updatedSports.get(0);
	}
	
	public static SportModel deleteSportById(Params params) throws ResponseException {
		Dao sportDao = new Dao(SportModel.class);
		
		Long sportId;
		try {
			sportId = params.getLong("sport_id");
			if(sportId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Sport id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid sport id");
		}
		
		List<Model> deletedSports = sportDao.deleteAndReturn(Arrays.asList(new ConditionEntry(null, "sport_id", Arrays.asList(Operators.EQUAL), sportId)), Arrays.asList("*"));
		if(deletedSports == null || deletedSports.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Sport not found");
		}
		return (SportModel) deletedSports.get(0);
	}
}
