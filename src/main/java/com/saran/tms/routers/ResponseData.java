package com.saran.tms.routers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import org.json.JSONObject;

import com.saran.tms.enums.StatusCodes;

public class ResponseData {
	private StatusCodes statusCode;
	private JSONObject data;
	private List<Cookie> cookies;
	
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
	
	public ResponseData addCookie(Cookie cookie) {
		if(cookies == null) {
			cookies = new ArrayList<>();
		}
		cookies.add(cookie);
		return this;
	}
	
	public List<Cookie> getCookies(){
		return cookies;
	}
}
