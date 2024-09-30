package com.saran.tms.enums;

public enum Constraints {
	AUTO_ASSIGN,
	PRIMARY_KEY,
	UNIQUE,
	NOT_NULL,
	EMAIL,
	PASSWORD,
	PHONE_NUMBER;
	
	public static Constraints getConstraint(String constraint) throws IllegalArgumentException {
		if(constraint.equals("autoassign")) {
			return AUTO_ASSIGN;
		}
		else if(constraint.equals("primary_key")) {
			return PRIMARY_KEY;
		}
		else if(constraint.equals("unique")) {
			return UNIQUE;
		}
		else if(constraint.equals("not_null")) {
			return NOT_NULL;
		}
		else if(constraint.equals("email")) {
			return EMAIL;
		}
		else if(constraint.equals("password")) {
			return PASSWORD;
		}
		else if(constraint.equals("phone_number")) {
			return PHONE_NUMBER;
		}
		throw new IllegalArgumentException("No such constraint found");
	}
}
