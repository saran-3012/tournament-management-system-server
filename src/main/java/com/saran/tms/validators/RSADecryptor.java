package com.saran.tms.validators;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

import com.saran.tms.enums.StatusCodes;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.logging.Level;

public class RSADecryptor {

	private static PrivateKey getPrivateKey(String filePath) throws Exception {
		String privateKeyPEM = new String(Files.readAllBytes(Paths.get(filePath)));

		privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "")
									 .replace("-----END PRIVATE KEY-----", "")
									 .replaceAll("\\s+", "");

		byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}

	public static String decrypt(String encryptedData, String privateKeyFilePath) throws ResponseException {
		try {
			PrivateKey privateKey = getPrivateKey(privateKeyFilePath);

			byte encryptedBytes[] = Base64.getDecoder().decode(encryptedData);

			Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

			OAEPParameterSpec oaepParams = new OAEPParameterSpec(
					"SHA-256",
					"MGF1", 
					MGF1ParameterSpec.SHA256,
					PSource.PSpecified.DEFAULT 
			);
			cipher.init(Cipher.DECRYPT_MODE, privateKey, oaepParams);

			byte decryptedBytes[] = cipher.doFinal(encryptedBytes);

			String decryptedData = new String(decryptedBytes, StandardCharsets.UTF_8);

			return decryptedData;
		} catch (Exception e) {
			ApplicationLogger.log(Level.SEVERE, "Error occured during RSA decryption", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Something went wrong");
		}
	}
}
