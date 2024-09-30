package com.saran.tms.models;

public class TournamentParticipantModel implements Model {

	private Long participantId;
	private Long userId;
	private Long tournamentId;
	private Short participantStatus;

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}

	public Short getParticipantStatus() {
		return participantStatus;
	}

	public void setParticipantStatus(Short participantStatus) {
		this.participantStatus = participantStatus;
	}

	@Override
	public String toString() {
		return "TournamentParticipantModel [participantId=" + participantId + ", userId=" + userId
				+ ", tournamentId=" + tournamentId + ", participantStatus=" + participantStatus + "]";
	}
}
