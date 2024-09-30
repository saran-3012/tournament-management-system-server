package com.saran.tms.models;

public class TournamentTeamModel implements Model {

	private Long teamId;
	private String teamName;
	private Long teamLeaderId;
	private Long tournamentId;
	private Short teamStatus;

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Long getTeamLeaderId() {
		return teamLeaderId;
	}

	public void setTeamLeaderId(Long teamLeaderId) {
		this.teamLeaderId = teamLeaderId;
	}

	public Long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}

	public Short getTeamStatus() {
		return teamStatus;
	}

	public void setTeamStatus(Short teamStatus) {
		this.teamStatus = teamStatus;
	}

	@Override
	public String toString() {
		return "TournamentTeamModel [teamId=" + teamId + ", teamName=" + teamName + ", teamLeaderId=" + teamLeaderId
				+ ", tournamentId=" + tournamentId + ", teamStatus=" + teamStatus + "]";
	}
}
