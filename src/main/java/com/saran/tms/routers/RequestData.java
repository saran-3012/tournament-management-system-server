package com.saran.tms.routers;

import java.util.Map;

import org.json.JSONObject;

public class RequestData {
	private Map<String, String[]> queryParams;
	private Map<String, String> headers;
	private Map<String, String> params;
	private JSONObject body;
	
	public RequestData(Map<String, String[]> queryParams, Map<String, String> headers, Map<String, String> params,
			JSONObject body) {
		this.queryParams = queryParams;
		this.headers = headers;
		this.params = params;
		this.body = body;
	}

	public Map<String, String[]> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, String[]> queryParams) {
		this.queryParams = queryParams;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public JSONObject getBody() {
		return body;
	}

	public void setBody(JSONObject body) {
		this.body = body;
	}
	
}
