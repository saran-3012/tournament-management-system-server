package com.saran.tms.models;

public class TournamentEventModel implements Model {

	private Long tournamentEventId;
	private Long tournamentEventDate;
	private String tournamentEventVenue;
	private Short tournamentEventRound;
	private Long tournamentId;
	private Long tournamentEventCreatedAt;
	private Short tournamentEventStatus;
	private Long tournamentEventWinnerId;

	public Long getTournamentEventId() {
		return tournamentEventId;
	}

	public void setTournamentEventId(Long tournamentEventId) {
		this.tournamentEventId = tournamentEventId;
	}

	public Long getTournamentEventDate() {
		return tournamentEventDate;
	}

	public void setTournamentEventDate(Long tournamentEventDate) {
		this.tournamentEventDate = tournamentEventDate;
	}

	public String getTournamentEventVenue() {
		return tournamentEventVenue;
	}

	public void setTournamentEventVenue(String tournamentEventVenue) {
		this.tournamentEventVenue = tournamentEventVenue;
	}

	public Short getTournamentEventRound() {
		return tournamentEventRound;
	}

	public void setTournamentEventRound(Short tournamentEventRound) {
		this.tournamentEventRound = tournamentEventRound;
	}

	public Long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}

	public Long getTournamentEventCreatedAt() {
		return tournamentEventCreatedAt;
	}

	public void setTournamentEventCreatedAt(Long tournamentEventCreatedAt) {
		this.tournamentEventCreatedAt = tournamentEventCreatedAt;
	}
	

	public Short getTournamentEventStatus() {
		return tournamentEventStatus;
	}

	public void setTournamentEventStatus(Short tournamentEventStatus) {
		this.tournamentEventStatus = tournamentEventStatus;
	}

	public Long getTournamentEventWinnerId() {
		return tournamentEventWinnerId;
	}

	public void setTournamentEventWinnerId(Long tournamentEventWinnerId) {
		this.tournamentEventWinnerId = tournamentEventWinnerId;
	}

	@Override
	public String toString() {
		return "TournamentEventModel [tournamentEventId=" + tournamentEventId + ", tournamentEventDate="
				+ tournamentEventDate + ", tournamentEventVenue=" + tournamentEventVenue + ", tournamentEventRound="
				+ tournamentEventRound + ", tournamentId=" + tournamentId + ", tournamentEventCreatedAt="
				+ tournamentEventCreatedAt + ", tournamentEventStatus=" + tournamentEventStatus
				+ ", tournamentEventWinnerId=" + tournamentEventWinnerId + "]";
	}

}
