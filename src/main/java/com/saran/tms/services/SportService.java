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

public class SportService {
	public static SportModel saveSport(SportModel sport) throws ResponseException {
		Dao sportDao = new Dao(SportModel.class);
		SportModel newSport = (SportModel) sportDao.saveAndReturn(sport, Arrays.asList("*"));
		return newSport;
	}
	
	public static SportModel findSportById(Map<String, String> params) throws ResponseException {
		Dao sportDao = new Dao(SportModel.class);
		SportModel sport = (SportModel) sportDao.findOne(
				Arrays.asList("*"), 
				Arrays.asList(
						new ConditionEntry(null, "sport_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("sport_id")))
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
	
	public static List<Model> findSports(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		Dao sportDao = new Dao(SportModel.class);
		
		List<ConditionEntry> conditions = new ArrayList<>();
		
		Operators operator = null;
		
		String sportNames[] = queryParams.get("filter_sportname");
		if(sportNames != null && sportNames.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "sport_name", Arrays.asList(Operators.ILIKE), '%' + sportNames[0] + '%'));
			operator = Operators.AND;
		}
		
		String sportTypes[] = queryParams.get("filter_sporttype");
		if(sportTypes != null && sportTypes.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "sport_type", Arrays.asList(Operators.EQUAL), Short.parseShort(sportTypes[0])));
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
		
		List<Model> sports = sportDao.findAll(Arrays.asList("*"), conditions, limit, offset);
		if(sports == null || sports.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Sports not found");
		}
		return sports;
	}

	public static SportModel updateSportById(Map<String, String> params, SportModel sport) throws ResponseException {
		Dao sportDao = new Dao(SportModel.class);
		List<Model> updatedSports = sportDao.updateAndReturn(sport, Arrays.asList(new ConditionEntry(null, "sport_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("sport_id")))), Arrays.asList("*"));
		if(updatedSports == null || updatedSports.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Sport not found");
		}
		return (SportModel) updatedSports.get(0);
	}
	
	public static SportModel deleteSportById(Map<String, String> params) throws ResponseException {
		Dao sportDao = new Dao(SportModel.class);
		List<Model> deletedSports = sportDao.deleteAndReturn(Arrays.asList(new ConditionEntry(null, "sport_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("sport_id")))), Arrays.asList("*"));
		if(deletedSports == null || deletedSports.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Sport not found");
		}
		return (SportModel) deletedSports.get(0);
	}
}
