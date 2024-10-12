package com.saran.tms.routers;

import java.util.Set;

import com.saran.tms.enums.UserRoles;

public class EndPointController {
	
	private Class<?> controllerClass;
	private String methodName;
	private String url;
	private Set<UserRoles> allowedRoles;
	
	public EndPointController(Class<?> controllerClass, String methodName, String url, UserRoles allowedRoles[]) {
		this.controllerClass = controllerClass;
		this.methodName = methodName;
		this.url = url;
		this.allowedRoles = Set.of(allowedRoles);
	}

	public Class<?> getControllerClass() {
		return controllerClass;
	}

	public String getMethodName() {
		return methodName;
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
