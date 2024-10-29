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

public class OrganizationService {
	public static OrganizationModel saveOrganization(OrganizationModel org) throws ResponseException {
		Dao orgDao = new Dao(OrganizationModel.class);
		OrganizationModel newOrg = (OrganizationModel) orgDao.saveAndReturn(org, Arrays.asList("*"));
		return newOrg;
	}

	public static List<Model> findOrganizationById(Map<String, String> params) throws ResponseException {
		Dao orgDao = new Dao(OrganizationModel.class);

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
										new ConditionEntry(null, "organization_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("org_id")))
								)
						)
				)
		);

		if (organizationDetails == null || organizationDetails.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Organization not found");
		}

		return organizationDetails;
	}

	public static List<List<Model>> findOrganizations(Map<String, String> params, Map<String, String[]> queryParams)
			throws ResponseException {
		Dao orgDao = new Dao(OrganizationModel.class);

		List<ConditionEntry> conditions = new ArrayList<>();

		Operators operator = null;

		String orgNames[] = queryParams.get("filter_orgname");
		if (orgNames != null && orgNames.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "organization_name",
					Arrays.asList(Operators.ILIKE), '%' + orgNames[0] + '%'));
			operator = Operators.AND;
		}

		String startedYears[] = queryParams.get("filter_startyear");
		if (startedYears != null && startedYears.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "started_year", Arrays.asList(Operators.EQUAL),
					Short.parseShort(startedYears[0])));
			operator = Operators.AND;
		}

		String organizationStatuses[] = queryParams.get("filter_organizationstatus");
		if (organizationStatuses != null && organizationStatuses.length > 0) {
			conditions.add(new ConditionEntry(Arrays.asList(operator), "organization_status",
					Arrays.asList(Operators.EQUAL), Short.parseShort(organizationStatuses[0])));
			operator = Operators.AND;
		}

		Integer limit = 20;
		Integer page = 0;

		String limits[] = queryParams.get("limit");
		String pages[] = queryParams.get("page");

		if (limits != null && limits.length > 0) {
			limit = Integer.parseInt(limits[0]);
		}

		if (pages != null && pages.length > 0) {
			page = Integer.parseInt(pages[0]);
		}

		Integer offset = limit * page;

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

	public static OrganizationModel updateOrganizationById(Map<String, String> params, OrganizationModel org)
			throws ResponseException {
		Dao orgDao = new Dao(OrganizationModel.class);
		List<Model> updatedOrgs = orgDao.updateAndReturn(
				org, 
				Arrays.asList(
						new ConditionEntry(null, "organization_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("org_id")))
				), 
				Arrays.asList("*")
		);
		if (updatedOrgs == null || updatedOrgs.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Organization not found");
		}
		return (OrganizationModel) updatedOrgs.get(0);
	}

	public static OrganizationModel deleteOrganizationById(Map<String, String> params) throws ResponseException {
		Dao orgDao = new Dao(OrganizationModel.class);
		List<Model> deletedOrgs = orgDao.deleteAndReturn(
				Arrays.asList(
						new ConditionEntry(null, "organization_id", Arrays.asList(Operators.EQUAL), Long.parseLong(params.get("org_id")))
				), 
				Arrays.asList("*"));
		if (deletedOrgs == null || deletedOrgs.isEmpty()) {
			throw new ResponseException(StatusCodes.NOT_FOUND, "Organization not found");
		}
		return (OrganizationModel) deletedOrgs.get(0);
	}
}
