package com.saran.tms.enums;

public enum StatusCodes {
	OK(200, "Ok"),
	CREATED(201, "Created"),
	ACCEPTED(202, "Accepted"),
	NO_CONTENT(204, "No content"),
	BAD_REQUEST(400, "Bad request"),
	UNAUTHORIZED(401, "Unauthorized"),
	FORBIDDEN(403, "Forbidden"),
	NOT_FOUND(404, "Not found"),
	METHOD_NOT_ALLOWED(405, "Method not allowed"),
	REQUEST_TIMEOUT(408, "Request Timeout"),
	CONFLICT(409, "Conflict"),
	GONE(410, "Requested content deleted or gone permanently"),
	PRECONDITION_FAILED(412, "Preconditions failed"),
	UNSUPPORTED_MEDIA_TYPE(415, "Unsupported media format"),
	UNPROCESSABLE_CONTENT(422, "Unprocessable content"),
	TOO_MANY_REQUESTS(429, "Too many requests"),
	INTERNAL_SERVER_ERROR(500, "Internal server error"),
	SERVICE_UNAVAILABLE(503, "Service is unavailable");
	
	private int statusCode;
	private String message;

	private StatusCodes(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public int getStatusCode() {
		return this.statusCode;
	}
	
	public String getStatusMessage() {
		return this.message;
	}
	
	
}
