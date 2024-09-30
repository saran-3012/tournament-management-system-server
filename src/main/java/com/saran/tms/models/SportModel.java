package com.saran.tms.models;

public class SportModel implements Model {

	private Long sportId;
	private String sportName;
	private Short sportType;
	private Short teamSize;

	public Long getSportId() {
		return sportId;
	}

	public void setSportId(Long sportId) {
		this.sportId = sportId;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public Short getSportType() {
		return sportType;
	}

	public void setSportType(Short sportType) {
		this.sportType = sportType;
	}

	public Short getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(Short teamSize) {
		this.teamSize = teamSize;
	}

	@Override
	public String toString() {
		return "SportModel [sportId=" + sportId + ", sportName=" + sportName + ", sportType=" + sportType + ", teamSize=" + teamSize + "]";
	}
}
