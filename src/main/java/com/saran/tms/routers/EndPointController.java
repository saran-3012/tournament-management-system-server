package com.saran.tms.routers;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.logging.Level;

import com.saran.tms.controllers.Controller;
import com.saran.tms.enums.UserRoles;
import com.saran.tms.logger.ApplicationLogger;

public class EndPointController {
	
	private Class<?> controllerClass;
	private String methodName;
	private String url;
	private Set<UserRoles> allowedRoles;
	private Controller controller;
	private Method method;
	
	public EndPointController(Class<?> controllerClass, String methodName, String url, UserRoles allowedRoles[]) {
		this.controllerClass = controllerClass;
		this.methodName = methodName;
		this.url = url;
		this.allowedRoles = Set.of(allowedRoles);
		
		try {			
			this.controller = (Controller) controllerClass.getDeclaredConstructor().newInstance();
		} catch(Exception e) {
			ApplicationLogger.log(Level.SEVERE, "Controller instantiation failed", e);
		}
		
		try {
			this.method = controllerClass.getDeclaredMethod(methodName, RequestData.class);
		} catch(Exception e) {
			ApplicationLogger.log(Level.SEVERE, "Method instance fetch failed", e);
		}
		
	}

	public Controller getController() {
		return controller;
	}

	public Method getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public Set<UserRoles> getAllowedRoles() {
		return allowedRoles;
	}

	@Override
	public String toString() {
		return "EndPointController [controllerClass=" + controllerClass + ", methodName=" + methodName + ", url=" + url
				+ ", allowedRoles=" + allowedRoles + "]";
	}

}
