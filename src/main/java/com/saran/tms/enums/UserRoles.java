package com.saran.tms.enums;

public enum UserRoles {
	APP_ADMIN(2),
	ORGANIZATION_ADMIN(1),
	ORGANIZATION_MEMBER(0),
	USER(-1);
	
	private int rolePriority;
	
	private UserRoles(int rolePriority) {
		this.rolePriority = rolePriority;
	}
	
	public int getRolePriority() {
		return this.rolePriority;
	}
	
	public static UserRoles getUserRole(int role) {
		switch(role) {
			case 0:
				return ORGANIZATION_MEMBER;
			case 1:
				return ORGANIZATION_ADMIN;
			case 2:
				return APP_ADMIN;
			default:
				return USER;
		}
	}
}
