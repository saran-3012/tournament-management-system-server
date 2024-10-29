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

public class TeamMemberService {
	public static TeamMemberModel saveTeamMember(TeamMemberModel teamMember) throws ResponseException {
		Dao teamMemberDao = new Dao(TeamMemberModel.class);
		TeamMemberModel newTeamMember = (TeamMemberModel) teamMemberDao.saveAndReturn(teamMember, Arrays.asList("*"));
		return newTeamMember;
	}
	
	public static List<Model> findTeamMemberById(Map<String, String> params) throws ResponseException{
		Dao teamMemberDao = new Dao(TeamMemberModel.class);
		
		Long teamMemberId = null;
		try {
			teamMemberId = Long.parseLong(params.get("member_id"));
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid Team member id");
		}
		
		Long teamId = null;
		try {
			teamId = Long.parseLong(params.get("team_id"));
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
	
	public static List<List<Model>> findTeamMembers(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		Dao teamMemberDao = new Dao(TeamMemberModel.class);
		
		Operators operator = null;
		
		List<ConditionEntry> teamMemberConditions = new ArrayList<>();
		List<ConditionEntry> userConditions = new ArrayList<>();
		
		Long teamId = null;
		try {
			teamId = Long.parseLong(params.get("team_id"));
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid team id");
		}
		if(teamId != null) {
			teamMemberConditions.add(new ConditionEntry(Arrays.asList(operator), "team_id", Arrays.asList(Operators.EQUAL), teamId));
			operator = Operators.AND;
		}
		
		String userIds[] = queryParams.get("filter_userid");
		Long userId = null;
		if(userIds != null && userIds.length > 0) {
			try {
				userId = Long.parseLong(userIds[0]);
			}
			catch(NumberFormatException e) {
				throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid user id");
			}
		}
		
		if(userId != null) {
			teamMemberConditions.add(new ConditionEntry(Arrays.asList(operator), "user_id", Arrays.asList(Operators.EQUAL), userId));
			operator = Operators.AND;
		}
		
		String userNames[] = queryParams.get("filter_username");
		if(userNames != null && userNames.length > 0) {
			userConditions.add(new ConditionEntry(Arrays.asList(operator), "user_name", Arrays.asList(Operators.ILIKE), '%' + userNames[0] + '%'));
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
	
	public static Long getTeamMemberCount(Map<String, String> params, Map<String, String[]> queryParams) throws ResponseException {
		Dao teamMemberDao = new Dao(TeamMemberModel.class);
		
		Long teamId = null;
		try {
			teamId = Long.parseLong(params.get("team_id"));
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
	
	public static TeamMemberModel deleteTeamMemberById(Map<String, String> params) throws ResponseException {
		
		Dao teamMemberDao = new Dao(TeamMemberModel.class);
		
		Long teamMemberId = null;
		
		try {
			teamMemberId = Long.parseLong(params.get("member_id"));
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
