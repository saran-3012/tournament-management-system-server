package com.saran.tms.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.saran.tms.dao.Dao;
import com.saran.tms.enums.JoinTypes;
import com.saran.tms.enums.Operators;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.enums.TableNames;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.models.Model;
import com.saran.tms.models.OrganizationModel;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.JoinConditionEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;
import com.saran.tms.routers.Params;
import com.saran.tms.routers.QueryParams;
import com.saran.tms.utils.Utilities;

public class OrganizationService {
	public static OrganizationModel saveOrganization(OrganizationModel org) throws ResponseException {
		Dao orgDao = new Dao(OrganizationModel.class);
		OrganizationModel newOrg = (OrganizationModel) orgDao.saveAndReturn(org, Arrays.asList("*"));
		return newOrg;
	}

	public static List<Model> findOrganizationById(Params params) throws ResponseException {
		Dao orgDao = new Dao(OrganizationModel.class);

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
		
		List<Model> organizationDetails = orgDao.findOneWithJoin(
				Arrays.asList(
						new TableColumnEntry(TableNames.ORGANIZATIONS, Arrays.asList("*")),
						new TableColumnEntry(TableNames.USERS, Arrays.asList("user_name", "user_id"))
				),
				Arrays.asList(
						new JoinEntry(TableNames.ORGANIZATIONS, TableNames.USERS, "organization_id", "organization_id", JoinTypes.LEFT_JOIN),
						new JoinConditionEntry(Operators.AND ,TableNames.USERS, "role", Operators.GREATER_THAN_OR_EQUAL, (short) 1),
						new JoinConditionEntry(Operators.AND ,TableNames.USERS, "role", Operators.LESS_THAN_OR_EQUAL, (short) 2)
				),
				Arrays.asList(
						new TableConditionEntry(TableNames.ORGANIZATIONS,
								Arrays.asList(
										new ConditionEntry(null, "organization_id", Arrays.asList(Operators.EQUAL), organizationId)
								)
						)
				)
		);

		if (organizationDetails == null || organizationDetails.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Organization not found");
		}

		return organizationDetails;
	}

	public static List<List<Model>> findOrganizations(Params params, QueryParams queryParams) throws ResponseException {
		Dao orgDao = new Dao(OrganizationModel.class);

		List<ConditionEntry> conditions = new ArrayList<>();

		Operators operator = null;

		String orgName = queryParams.get("filter_orgname");
		if (orgName != null) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "organization_name", Arrays.asList(Operators.ILIKE), '%' + orgName + '%'));
			operator = Operators.AND;
		}
		
		try {
			Short startedYear = queryParams.getShort("filter_startyear");
			if(startedYear != null) {
				conditions.add(new ConditionEntry(Arrays.asList(operator), "started_year", Arrays.asList(Operators.EQUAL), startedYear));
				operator = Operators.AND;
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid started year");
		}
		
		try {
			Short organizationStatus = queryParams.getShort("filter_organizationstatus");
			if(organizationStatus != null) {
				conditions.add(new ConditionEntry(Arrays.asList(operator), "organization_status", Arrays.asList(Operators.EQUAL), organizationStatus));
				operator = Operators.AND;
			}
		}
		catch(NumberFormatException e) {
			throw new ResponseException(StatusCodes.BAD_REQUEST, "Invalid organization status");
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

		List<List<Model>> orgDetailsList = orgDao.findAllWithJoin(
						Arrays.asList(
								new TableColumnEntry(TableNames.ORGANIZATIONS, Arrays.asList("*")),
								new TableColumnEntry(TableNames.USERS, Arrays.asList("user_name", "user_id"))
						),
						Arrays.asList(
								new JoinEntry(TableNames.ORGANIZATIONS, TableNames.USERS, "organization_id", "organization_id", JoinTypes.LEFT_JOIN),
								new JoinConditionEntry(Operators.AND ,TableNames.USERS, "role", Operators.GREATER_THAN_OR_EQUAL, (short) 1),
								new JoinConditionEntry(Operators.AND ,TableNames.USERS, "role", Operators.LESS_THAN_OR_EQUAL, (short) 2)
						),
						Arrays.asList(
								new TableConditionEntry(TableNames.ORGANIZATIONS, conditions)
						),
						limit, offset);

		return orgDetailsList;
	}

	public static OrganizationModel updateOrganizationById(Params params, OrganizationModel org) throws ResponseException {
		Dao orgDao = new Dao(OrganizationModel.class);
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
		List<Model> updatedOrgs = orgDao.updateAndReturn(
				org, 
				Arrays.asList(
						new ConditionEntry(null, "organization_id", Arrays.asList(Operators.EQUAL), organizationId)
				), 
				Arrays.asList("*")
		);
		if (updatedOrgs == null || updatedOrgs.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Organization not found");
		}
		return (OrganizationModel) updatedOrgs.get(0);
	}

	public static OrganizationModel deleteOrganizationById(Params params) throws ResponseException {
		Dao orgDao = new Dao(OrganizationModel.class);
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
		List<Model> deletedOrgs = orgDao.deleteAndReturn(
				Arrays.asList(
						new ConditionEntry(null, "organization_id", Arrays.asList(Operators.EQUAL), organizationId)
				), 
				Arrays.asList("*"));
		if (deletedOrgs == null || deletedOrgs.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Organization not found");
		}
		return (OrganizationModel) deletedOrgs.get(0);
	}
}
