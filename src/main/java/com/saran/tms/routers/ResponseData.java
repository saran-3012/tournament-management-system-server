package com.saran.tms.routers;

import org.json.JSONObject;

import com.saran.tms.enums.StatusCodes;

public class ResponseData {
	private StatusCodes statusCode;
	private JSONObject data;
	
	public ResponseData() {}

	public ResponseData(StatusCodes statusCode, JSONObject data) {
		this.statusCode = statusCode;
		this.data = data;
	}

	public StatusCodes getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(StatusCodes statusCode) {
		this.statusCode = statusCode;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}
}
