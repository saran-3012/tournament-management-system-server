package com.saran.tms.models;

public class TournamentEventParticipantModel implements Model {

	private Long tournamentEventParticipantId;
	private Long tournamentEventId;
	private Long participantId;

	public Long getTournamentEventParticipantId() {
		return tournamentEventParticipantId;
	}

	public void setTournamentEventParticipantId(Long tournamentEventParticipantId) {
		this.tournamentEventParticipantId = tournamentEventParticipantId;
	}

	public Long getTournamentEventId() {
		return tournamentEventId;
	}

	public void setTournamentEventId(Long tournamentEventId) {
		this.tournamentEventId = tournamentEventId;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	@Override
	public String toString() {
		return "TournamentEventParticipantModel [tournamentEventParticipantId=" + tournamentEventParticipantId
				+ ", tournamentEventId=" + tournamentEventId + ", participantId=" + participantId + "]";
	}
}
