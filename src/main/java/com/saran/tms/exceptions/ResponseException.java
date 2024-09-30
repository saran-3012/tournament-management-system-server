package com.saran.tms.exceptions;

import com.saran.tms.enums.StatusCodes;

public class ResponseException extends Exception{

	private static final long serialVersionUID = 1344409583257436248L;
	
	private StatusCodes errorCode;
	
	
	public ResponseException(StatusCodes statusCode, String message) {
		super(message);
		this.errorCode = statusCode;
	}
	
	public StatusCodes getErrorCode() {
		return this.errorCode;
	}

}
