package com.saran.tms.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.saran.tms.adapters.ModelJsonParser;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.models.Model;
import com.saran.tms.routers.Params;
import com.saran.tms.services.OrganizationService;


public class AuthFilter extends HttpFilter implements Filter {
       

    private static final long serialVersionUID = 4301801606351047173L;

	public AuthFilter() {
        super();
    }

    public void init(FilterConfig fConfig) throws ServletException {}

	public void destroy() {}
	
	private void expireSessionCookie(HttpServletResponse httpResponse) {
		Cookie jsessionCookie = new Cookie("JSESSIONID", null);  
		jsessionCookie.setMaxAge(0);  
		jsessionCookie.setHttpOnly(true);
		jsessionCookie.setPath("/tms"); 
		
		httpResponse.addCookie(jsessionCookie);
		httpResponse.setHeader("Tms-Auth-Status", "1");
	}
	
	private void returnErrorResponse(ServletResponse response, StatusCodes statusCode, String errorMessage) {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setHeader("Content-Type", "Application/json");
		
		expireSessionCookie(httpResponse);
		
		PrintWriter out = null;
    	
		try {
			out = httpResponse.getWriter();
			httpResponse.setStatus(statusCode.getStatusCode());
			JSONObject errorJson = new JSONObject();
			errorJson.put("message", errorMessage);
			out.println(errorJson.toString());
		} 
		catch (IOException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Unable to get the writer", e);
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);
		
		if(session == null) {
			
			returnErrorResponse(response, StatusCodes.UNAUTHORIZED, "User is not authorized");
			
			return;
		}
		
		for(final String headerName : new String[]{"user-agent", "sec-ch-ua", "sec-ch-ua-platform"}) {
			if(httpRequest.getHeader(headerName) == null && session.getAttribute(headerName) == null) {
				continue;
			}
			if(httpRequest.getHeader(headerName) == null || session.getAttribute(headerName) == null || !httpRequest.getHeader(headerName).equals((String) session.getAttribute(headerName))) {
				
				returnErrorResponse(response, StatusCodes.UNAUTHORIZED, "User is not authorized");
				return;
			}
		}
		
		// Check organization status ( Increases latency) 
		
		Object organizationId = session.getAttribute("organizationId");
		
		if(organizationId == null || !(organizationId instanceof Long)) {
			
			returnErrorResponse(response, StatusCodes.UNAUTHORIZED, "Session is not valid!");
			
			return;
		}
		
		// Vulnerable should change location
		
		try {
			List<Model> orgDetails = OrganizationService.findOrganizationById(new Params(Map.ofEntries(Map.entry("org_id", organizationId.toString()))));
			JSONObject orgData = ModelJsonParser.parseAndMerge(orgDetails);
			Short orgStatus = (short) orgData.optInt("organizationStatus", 0);
			if(orgStatus != 1) {
				
				returnErrorResponse(response, StatusCodes.UNAUTHORIZED, "Organization do not have any access yet");
				
				return;
			}
		} 
		catch (ResponseException e) {
			e.printStackTrace();
	
			returnErrorResponse(response, e.getErrorCode(), e.getMessage());
			
			return;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			
			returnErrorResponse(response, StatusCodes.INTERNAL_SERVER_ERROR, "Internal server error");
			
			return;
		}
		
		chain.doFilter(request, response);
	}


}
