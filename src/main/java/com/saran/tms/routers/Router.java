package com.saran.tms.routers;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.saran.tms.annotations.ParentRoute;
import com.saran.tms.annotations.Route;
import com.saran.tms.controllers.Controller;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.utils.RequestParser;

public class Router {
	public static final Map<EndPoint, EndPointController> endPointMapping = new HashMap<>();
	private static boolean isInitialized = false;
	
	public static List<Class<?>> getControllerClasses(String packageName) throws ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        URL packageURL = classLoader.getResource(path);

        List<Class<?>> classes = new ArrayList<>();
        if (packageURL != null) {
            File directory = new File(packageURL.getFile());

            for (File file : directory.listFiles()) {
                String fileName = file.getName();
                if (fileName.endsWith(".class")) {
                    String className = packageName + '.' + fileName.substring(0, fileName.length() - 6);
                    classes.add(Class.forName(className));
                }
            }
        }
        
        return classes;
    }
	
	public static void initailizeRouter(String packageName) throws ClassNotFoundException {
		if(isInitialized) return;
		
		List<Class<?>> controllers = getControllerClasses(packageName);
		
		for(Class<?> controller : controllers) {
			if(!controller.isAnnotationPresent(ParentRoute.class)) {
				continue;
			}
			ParentRoute parentRoute = controller.getAnnotation(ParentRoute.class);
			String routeRootPath = parentRoute.path();
			for(Method method : controller.getDeclaredMethods()) {
				if(method.isAnnotationPresent(Route.class)) {
					
					Route route = method.getAnnotation(Route.class);
					
					String routePath = RequestParser.checkSlash(route.path());
					String routeMethod = route.method();
					
					endPointMapping.put(new EndPoint(routeRootPath, routePath, routeMethod), new EndPointController(controller, method.getName(), routePath));
				}
			}
		}
		isInitialized = true;
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
		return new JSONObject(body);
	}
	
	public static ResponseData route(HttpServletRequest request, HttpServletResponse response) throws ResponseException {
		
		String rootRoute = request.getServletPath();
		String url = RequestParser.checkSlash(request.getPathInfo());
		String truncatedUrl = RequestParser.truncateUrl(url);
		EndPointController endPointController = endPointMapping.get(new EndPoint(rootRoute, truncatedUrl, request.getMethod()));
		
		if(endPointController == null) {
			ApplicationLogger.log(Level.SEVERE, "Route not found");
			throw new ResponseException(StatusCodes.NOT_FOUND, "Route not found");
		}
		
		Class<?> controllerClass = endPointController.getControllerClass();
		String methodName = endPointController.getMethodName();
		String routeUrl = endPointController.getUrl();
		
		Map<String, String[]> queryParams = request.getParameterMap();
		Map<String, String> reqHeaders = getHeaders(request);
		Map<String, String> params = RequestParser.parseParams(url, routeUrl);
		JSONObject reqBody = null;
		

		try {
			String methodType = ((HttpServletRequest) request).getMethod();
			if(!methodType.equals("GET")) {
				reqBody = parseBody(request.getReader());
			}
		} catch (IOException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Error while parsing request body", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the request");
		}
		
		
		RequestData requestData = new RequestData(queryParams, reqHeaders, params, reqBody);

		Controller controller = null;
		
		try {
			controller = (Controller) controllerClass.getDeclaredConstructor().newInstance();
		} 
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			 Throwable cause = e.getCause();
			 if (cause != null) {
				 cause.printStackTrace();
			     ApplicationLogger.log(Level.WARNING, "Unable to create controller instance with reflection", cause);
			 } 
			 else {
			     e.printStackTrace();
			     ApplicationLogger.log(Level.WARNING, "Unable to create controller instance with reflection", e);
			 }
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the request");
		}

		ResponseData responseData = null;
		
		try {
			responseData = (ResponseData) controllerClass.getDeclaredMethod(methodName, RequestData.class).invoke(controller, requestData);
		} 
		catch(InvocationTargetException e) {
		     Throwable cause = e.getCause();

		     if(cause != null && cause instanceof ResponseException) {
				 cause.printStackTrace();
			     ApplicationLogger.log(Level.WARNING, "Unable to invoke the method with reflection", cause);
		    	 throw (ResponseException) cause;
		     }
		     e.printStackTrace();
		     ApplicationLogger.log(Level.WARNING, "Unable to invoke the method with reflection", e);
	    	 throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the content");
		}
		catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException e) {
			 Throwable cause = e.getCause();
			 if (cause != null) {
				 cause.printStackTrace();
			     ApplicationLogger.log(Level.WARNING, "Unable to invoke the method with reflection", cause);
			 } 
			 else {
			     e.printStackTrace();
			     ApplicationLogger.log(Level.WARNING, "Unable to invoke the method with reflection", e);
			 }
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the content");
		}
	
		return responseData;
	}
	
}
