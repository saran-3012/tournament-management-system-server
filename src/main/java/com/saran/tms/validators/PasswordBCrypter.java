package com.saran.tms.validators;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordBCrypter {
	private static final String PEPPER = System.getenv("PEPPER");
	
	public static String encryptPassword(String password) {
		String pepperedPassword = PEPPER + password;
		return BCrypt.hashpw(pepperedPassword, BCrypt.gensalt());
	}
	
	public static boolean verifyPassword(String password, String encryptedPassword) {
		String pepperedPassword = PEPPER + password;
		return BCrypt.checkpw(pepperedPassword, encryptedPassword);
	}
}
