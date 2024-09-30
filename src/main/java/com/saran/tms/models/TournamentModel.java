package com.saran.tms.models;

public class TournamentModel implements Model {

	private Long tournamentId;
	private String tournamentName;
	private Short maxParticipation;
	private Long registrationStartDate;
	private Long registrationEndDate;
	private String venue;
	private Long tournamentDate;
	private Short tournamentStatus;
	private Long sportId;
	private Long organizationId;
	private Long tournamentCreatedAt;

	public Long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}

	public Short getMaxParticipation() {
		return maxParticipation;
	}

	public void setMaxParticipation(Short maxParticipation) {
		this.maxParticipation = maxParticipation;
	}

	public Long getRegistrationStartDate() {
		return registrationStartDate;
	}

	public void setRegistrationStartDate(Long registrationStartDate) {
		this.registrationStartDate = registrationStartDate;
	}

	public Long getRegistrationEndDate() {
		return registrationEndDate;
	}

	public void setRegistrationEndDate(Long registrationEndDate) {
		this.registrationEndDate = registrationEndDate;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public Long getTournamentDate() {
		return tournamentDate;
	}

	public void setTournamentDate(Long tournamentDate) {
		this.tournamentDate = tournamentDate;
	}

	public Short getTournamentStatus() {
		return tournamentStatus;
	}

	public void setTournamentStatus(Short tournamentStatus) {
		this.tournamentStatus = tournamentStatus;
	}

	public Long getSportId() {
		return sportId;
	}

	public void setSportId(Long sportId) {
		this.sportId = sportId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getTournamentCreatedAt() {
		return tournamentCreatedAt;
	}

	public void setTournamentCreatedAt(Long tournamentCreatedAt) {
		this.tournamentCreatedAt = tournamentCreatedAt;
	}

	@Override
	public String toString() {
		return "TournamentModel [tournamentId=" + tournamentId + ", tournamentName=" + tournamentName
				+ ", maxParticipation=" + maxParticipation + ", registrationStartDate=" + registrationStartDate
				+ ", registrationEndDate=" + registrationEndDate + ", venue=" + venue + ", tournamentDate="
				+ tournamentDate + ", tournamentStatus=" + tournamentStatus + ", sportId=" + sportId
				+ ", organizationId=" + organizationId + ", tournamentCreatedAt=" + tournamentCreatedAt + "]";
	}
}
