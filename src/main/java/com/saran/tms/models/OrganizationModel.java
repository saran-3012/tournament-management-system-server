package com.saran.tms.models;

public class OrganizationModel implements Model {

	private Long organizationId;
	private String organizationName;
	private String organizationAddress;
	private Short startedYear;
	private Short organizationStatus;
	private Long organizationCreatedAt;
	
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getOrganizationAddress() {
		return organizationAddress;
	}
	public void setOrganizationAddress(String organizationAddress) {
		this.organizationAddress = organizationAddress;
	}
	public Short getStartedYear() {
		return startedYear;
	}
	public void setStartedYear(Short startedYear) {
		this.startedYear = startedYear;
	}
	public Short getOrganizationStatus() {
		return organizationStatus;
	}
	public void setOrganizationStatus(Short organizationStatus) {
		this.organizationStatus = organizationStatus;
	}
	public Long getOrganizationCreatedAt() {
		return organizationCreatedAt;
	}
	public void setOrganizationCreatedAt(Long organizationCreatedAt) {
		this.organizationCreatedAt = organizationCreatedAt;
	}
	
	@Override
	public String toString() {
		return "OrganizationModel [organizationId=" + organizationId + ", organizationName=" + organizationName
				+ ", organizationAddress=" + organizationAddress + ", startedYear=" + startedYear
				+ ", organizationStatus=" + organizationStatus + ", organizationCreatedAt=" + organizationCreatedAt
				+ "]";
	}
	
}
