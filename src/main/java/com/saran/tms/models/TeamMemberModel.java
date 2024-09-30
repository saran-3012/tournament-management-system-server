package com.saran.tms.models;

public class TeamMemberModel implements Model {

	private Long teamMemberId;
	private Long userId;
	private Long teamId;

	public Long getTeamMemberId() {
		return teamMemberId;
	}

	public void setTeamMemberId(Long teamMemberId) {
		this.teamMemberId = teamMemberId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	@Override
	public String toString() {
		return "TeamMemberModel [teamMemberId=" + teamMemberId + ", userId=" + userId + ", teamId=" + teamId + "]";
	}
}
