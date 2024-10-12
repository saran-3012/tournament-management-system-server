package com.saran.tms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.routers.Router;


public class AuthenticationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       

    public AuthenticationServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		response.setHeader("Content-Type", "Application/json");
		response.setHeader("Cache-Control", "private,no-cache,no-store,max-age=0,must-revalidate");
		response.setHeader("Pragma", "no-cache");

    	PrintWriter out = null;
    	
		try {
			out = response.getWriter();
		} 
		catch (IOException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Unable to get the writer", e);
		}
  
    	
		try {
			ResponseData responseData = Router.route(request, response);
			response.setStatus(responseData.getStatusCode().getStatusCode());
				
			if(responseData.getData() != null) {
				out.println(responseData.getData());
			}
			
			if(responseData.getCookies() != null) {
				for(final Cookie cookie : responseData.getCookies()) {
					response.addCookie(cookie);
				}
			}
		} 
		catch (ResponseException e) {
			
			e.printStackTrace();
			response.setStatus(e.getErrorCode().getStatusCode());
			JSONObject errorJson = new JSONObject();
			errorJson.put("message", e.getMessage());
			out.println(errorJson.toString());
			ApplicationLogger.log(Level.SEVERE, e.getMessage(), e);
			
		}
	}

}
