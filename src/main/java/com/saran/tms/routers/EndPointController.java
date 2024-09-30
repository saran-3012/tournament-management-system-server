package com.saran.tms.routers;

public class EndPointController {
	
	private Class<?> controllerClass;
	private String methodName;
	private String url;
	
	public EndPointController(Class<?> controllerClass, String methodName, String url) {
		this.controllerClass = controllerClass;
		this.methodName = methodName;
		this.url = url;
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

	@Override
	public String toString() {
		return "EndPointController [controllerClass=" + controllerClass + ", methodName=" + methodName + ", url=" + url
				+ "]";
	}
	
	
}
