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
import com.saran.tms.models.TeamMemberModel;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.GroupEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.utils.Utilities;

public class TeamMemberService {
	public static TeamMemberModel saveTeamMember(TeamMemberModel teamMember) throws ResponseException {
		Dao teamMemberDao = new Dao(TeamMemberModel.class);
		TeamMemberModel newTeamMember = (TeamMemberModel) teamMemberDao.saveAndReturn(teamMember, Arrays.asList("*"));
		return newTeamMember;
	}
	
	public static List<Model> findTeamMemberById(Params params) throws ResponseException{
		Dao teamMemberDao = new Dao(TeamMemberModel.class);
		
		Long teamMemberId = null;
		try {
			teamMemberId = params.getLong("member_id");
			if(teamMemberId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Team Member id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid Team member id");
		}
		
		Long teamId = null;
		try {
			teamId = params.getLong("team_id");
			if(teamId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Team id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid Team id");
		}
		
		List<Model> teamMemberDetails = teamMemberDao.findOneWithJoin(
				Arrays.asList(
					new TableColumnEntry(TableNames.TEAM_MEMBERS, Arrays.asList("*")),
					new TableColumnEntry(TableNames.USERS, Arrays.asList("user_name")),
					new TableColumnEntry(TableNames.TOURNAMENT_TEAMS, Arrays.asList("team_leader_id"))
				), 
				Arrays.asList(
					new JoinEntry(TableNames.TEAM_MEMBERS, TableNames.USERS, "user_id", "user_id", JoinTypes.JOIN),
					new JoinEntry(TableNames.TEAM_MEMBERS, TableNames.TOURNAMENT_TEAMS, "team_id", "team_id", JoinTypes.JOIN)
				), 
				Arrays.asList(
					new TableConditionEntry(TableNames.TEAM_MEMBERS, 
						Arrays.asList(
							new ConditionEntry(null, "team_member_id", Arrays.asList(Operators.EQUAL), teamMemberId),
							new ConditionEntry(Arrays.asList(Operators.AND), "team_id", Arrays.asList(Operators.EQUAL), teamId)
						)
					)
				)
			);
	
		if(teamMemberDetails == null) {
			return new ArrayList<>();
		}
		
		return teamMemberDetails;
	}
	
	public static List<List<Model>> findTeamMembers(Params params, QueryParams queryParams) throws ResponseException {
		Dao teamMemberDao = new Dao(TeamMemberModel.class);
		
		Operators operator = null;
		
		List<ConditionEntry> teamMemberConditions = new ArrayList<>();
		List<ConditionEntry> userConditions = new ArrayList<>();
		
		Long teamId = null;
		try {
			teamId = params.getLong("team_id");
			if(teamId != null) {
				teamMemberConditions.add(new ConditionEntry(Arrays.asList(operator), "team_id", Arrays.asList(Operators.EQUAL), teamId));
				operator = Operators.AND;
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid Team id");
		}
		
		Long userId;
		try {
			userId = queryParams.getLong("filter_userid");
			if(userId != null) {
				teamMemberConditions.add(new ConditionEntry(Arrays.asList(operator), "user_id", Arrays.asList(Operators.EQUAL), userId));
				operator = Operators.AND;
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid user id");
		}
		
		
		
		String userName = queryParams.get("filter_username");
		if(userName != null) {
			userConditions.add(new ConditionEntry(Arrays.asList(operator), "user_name", Arrays.asList(Operators.ILIKE), '%' + userName + '%'));
			operator = Operators.AND;
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
		
		List<List<Model>> teamMemberDetailsList = teamMemberDao.findAllWithJoin(
				Arrays.asList(
					new TableColumnEntry(TableNames.TEAM_MEMBERS, Arrays.asList("*")),
					new TableColumnEntry(TableNames.USERS, Arrays.asList("user_name"))
				), 
				Arrays.asList(
					new JoinEntry(TableNames.TEAM_MEMBERS, TableNames.USERS, "user_id", "user_id", JoinTypes.JOIN)
				), 
				Arrays.asList(
					new TableConditionEntry(TableNames.TEAM_MEMBERS, teamMemberConditions),
					new TableConditionEntry(TableNames.USERS, userConditions)
				), limit, offset);
		
		if(teamMemberDetailsList == null) {
			return new ArrayList<>();
		}
		
		return teamMemberDetailsList;
	}
	
	public static Long getTeamMemberCount(Params params, QueryParams queryParams) throws ResponseException {
		Dao teamMemberDao = new Dao(TeamMemberModel.class);
		
		Long teamId = null;
		try {
			teamId = params.getLong("team_id");
			if(teamId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Team id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid Team id");
		}

		Map<GroupEntry, Functions> fieldFunctions = new HashMap<>();
		fieldFunctions.put(new GroupEntry(TableNames.TEAM_MEMBERS, "*"), Functions.COUNT);
		
		List<List<Model>> teamMemberAggregateList = teamMemberDao.findAll(
										Arrays.asList("*"), 
										Arrays.asList(
											new ConditionEntry(null, "team_id", Arrays.asList(Operators.EQUAL), teamId)	
										), fieldFunctions, null, null);
		
		AggregateModel aggregateModel = (AggregateModel) teamMemberAggregateList.get(0).get(0);
		
		return aggregateModel.getCount();
		
	}
	
	public static TeamMemberModel deleteTeamMemberById(Params params) throws ResponseException {
		
		Dao teamMemberDao = new Dao(TeamMemberModel.class);
		
		Long teamMemberId = null;
		
		try {
			teamMemberId = params.getLong("member_id");
			if(teamMemberId == null) {
				throw new ResponseException(StatusCodes.PRECONDITION_FAILED, "Team member id is not provided");
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid team member id");
		}
		
		List<Model> deletedTeamMembers = teamMemberDao.deleteAndReturn(
				Arrays.asList(
					new ConditionEntry(null, "team_member_id", Arrays.asList(Operators.EQUAL), teamMemberId)
				), 
				Arrays.asList("*")
			);
		
		if(deletedTeamMembers == null || deletedTeamMembers.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Team Member not found");
		}
		
		return (TeamMemberModel) deletedTeamMembers.get(0);
	}
}
