package com.saran.tms.models;

public class TournamentEventTeamModel implements Model {

	private Long tournamentEventTeamId;
	private Long tournamentEventId;
	private Long teamId;

	public Long getTournamentEventTeamId() {
		return tournamentEventTeamId;
	}

	public void setTournamentEventTeamId(Long tournamentEventTeamId) {
		this.tournamentEventTeamId = tournamentEventTeamId;
	}

	public Long getTournamentEventId() {
		return tournamentEventId;
	}

	public void setTournamentEventId(Long tournamentEventId) {
		this.tournamentEventId = tournamentEventId;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	@Override
	public String toString() {
		return "TournamentEventTeamModel [tournamentEventTeamId=" + tournamentEventTeamId + ", tournamentEventId="
				+ tournamentEventId + ", teamId=" + teamId + "]";
	}
}
