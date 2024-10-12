package com.saran.tms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.routers.ResponseData;
import com.saran.tms.routers.Router;


public class ApiServletV1 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) {
    	
    	response.setHeader("Content-Type", "Application/json");
    	
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
			out.println(responseData.getData());
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
