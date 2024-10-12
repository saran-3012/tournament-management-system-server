package com.saran.tms.routers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.saran.tms.enums.StatusCodes;
import com.saran.tms.enums.UserRoles;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;

public class RequestData {
	
	private HttpServletRequest request;
	
	private Map<String, String[]> queryParams;
	private Map<String, String> headers;
	private Map<String, String> params;
	private JSONObject body;
	
	public RequestData(HttpServletRequest request, Map<String, String> params) throws ResponseException {
		this.request = request;
		this.queryParams = request.getParameterMap();
		this.headers = getHeaders(request);
		this.params = params;
		try {
			String methodType = request.getMethod();
			if(!methodType.equals("GET")) {
				this.body = parseBody(request.getReader());
			}
		} catch (IOException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Error while parsing request body", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the request");
		}
	}

	private static Map<String, String> getHeaders(HttpServletRequest request) {
		
		Map<String, String> reqHeaders = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            reqHeaders.put(headerName, headerValue);
        }
        
        return reqHeaders;
	}
	
	private static JSONObject parseBody(BufferedReader bodyReader) throws IOException {
		StringBuilder jsonBuilder = new StringBuilder("");
		
		String line;
		
		while ((line = bodyReader.readLine()) != null) {
		    jsonBuilder.append(line);
		}
		    
		bodyReader.close();

		String body = jsonBuilder.toString();
		if(body == null || body.isEmpty()) {
			return new JSONObject();
		}
		return new JSONObject(body); 
	}
	
	public Map<String, String[]> getQueryParams() {
		return queryParams;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public JSONObject getBody() {
		return body;
	}
	
	public UserRoles getUserRole() {
		HttpSession session = getSession(false);
		if(session == null) {
			return UserRoles.USER;
		}
		return (UserRoles) session.getAttribute("userRole");
	}
	
	public Long getUserId() {
		HttpSession session = getSession(false);
		if(session == null) {
			return null;
		}
		return (Long) session.getAttribute("userId");
	}

	public HttpSession getSession(boolean createdIfNotExists) {
		return request.getSession(createdIfNotExists);
	}
}
