package com.saran.tms.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
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

import com.saran.tms.enums.StatusCodes;
import com.saran.tms.logger.ApplicationLogger;


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
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);
		
		if(session == null) {
			
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setHeader("Content-Type", "Application/json");
			
			expireSessionCookie(httpResponse);
			
			PrintWriter out = null;
	    	
			try {
				out = httpResponse.getWriter();
				httpResponse.setStatus(StatusCodes.UNAUTHORIZED.getStatusCode());
				JSONObject errorJson = new JSONObject();
				errorJson.put("message", "User is not authorized");
				out.println(errorJson.toString());
			} 
			catch (IOException e) {
				e.printStackTrace();
				ApplicationLogger.log(Level.SEVERE, "Unable to get the writer", e);
			}
			
			return;
		}
		
		for(final String headerName : new String[]{"user-agent", "sec-ch-ua", "sec-ch-ua-platform", "accept-language"}) {
			if(httpRequest.getHeader(headerName) == null && session.getAttribute(headerName) == null) {
				continue;
			}
			if(httpRequest.getHeader(headerName) == null || session.getAttribute(headerName) == null || !httpRequest.getHeader(headerName).equals((String) session.getAttribute(headerName))) {
				
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.setHeader("Content-Type", "Application/json");
				
				expireSessionCookie(httpResponse);
				
				PrintWriter out = null;
		    	
				try {
					out = httpResponse.getWriter();
					httpResponse.setStatus(StatusCodes.UNAUTHORIZED.getStatusCode());
					JSONObject errorJson = new JSONObject();
					errorJson.put("message", "User is not authorized");
					out.println(errorJson.toString());
				} 
				catch (IOException e) {
					e.printStackTrace();
					ApplicationLogger.log(Level.SEVERE, "Unable to get the writer", e);
				}
				
				return;
			}
		}
		
		chain.doFilter(request, response);
	}


}
