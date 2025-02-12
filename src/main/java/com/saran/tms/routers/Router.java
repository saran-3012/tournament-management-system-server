package com.saran.tms.routers;


import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.saran.tms.annotations.Route;
import com.saran.tms.annotations.RouteGroup;
import com.saran.tms.controllers.Controller;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.enums.UserRoles;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.services.MonitoringService;
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
			if(!controller.isAnnotationPresent(RouteGroup.class)) {
				continue;
			}
			RouteGroup parentRoute = controller.getAnnotation(RouteGroup.class);
			String routeRootPath = parentRoute.path();
			for(Method method : controller.getDeclaredMethods()) {
				if(method.isAnnotationPresent(Route.class)) {
					
					Route route = method.getAnnotation(Route.class);
					
					String routePath = RequestParser.checkSlash(route.path());
					String routeMethod = route.method();
					UserRoles allowedRoles[] = route.allowedRoles();
					
					
					endPointMapping.put(
							new EndPoint(routeRootPath, routePath, routeMethod), 
							new EndPointController(controller, method.getName(), routePath, allowedRoles)
						);
				}
			}
		}
		MonitoringService.registerObject("endPointMapping", endPointMapping);
		isInitialized = true;
	}
	
	public static ResponseData route(HttpServletRequest request, HttpServletResponse response) throws ResponseException {
		
		String rootRoute = request.getServletPath();
		String truncatedUrl = RequestParser.truncateUrl(RequestParser.checkSlash(request.getPathInfo()));
		EndPointController endPointController = endPointMapping.get(new EndPoint(rootRoute, truncatedUrl, request.getMethod()));
		
		if(endPointController == null) {
			ApplicationLogger.log(Level.SEVERE, "Route not found");
			throw new ResponseException(StatusCodes.NOT_FOUND, "Route not found");
		}
		
		String routeUrl = endPointController.getUrl();
		Set<UserRoles> allowedRoles = endPointController.getAllowedRoles();
		
		HttpSession session = request.getSession(false);
		
		UserRoles userRole = null;
		
		if(session == null) {
			userRole = UserRoles.USER;
			if(!allowedRoles.contains(userRole)){ 
				throw new ResponseException(StatusCodes.UNAUTHORIZED , "User is not authorized");
			}
		}
		else {
			
			userRole = (UserRoles) session.getAttribute("userRole");
			if(userRole == null) {
				userRole = UserRoles.USER;
			}
			
			if(!allowedRoles.contains(userRole)) {
				throw new ResponseException(StatusCodes.FORBIDDEN , "User is not allowed to perform this operation");
			}
		}
		
		Map<String, String> params = RequestParser.parseParams(truncatedUrl, routeUrl);
		
		RequestData requestData = new RequestData(request, params);

		Controller controller = endPointController.getController();
		Method method = endPointController.getMethod();

		ResponseData responseData = null;
		
		try {
			responseData = (ResponseData) method.invoke(controller, requestData);
		} 
		catch(InvocationTargetException e) {
		     Throwable cause = e.getCause();
		     if(cause != null && cause instanceof ResponseException) {
			     ApplicationLogger.log(Level.WARNING, "Unable to invoke the method with reflection", cause);
		    	 throw (ResponseException) cause;
		     }
		     ApplicationLogger.log(Level.WARNING, "Unable to invoke the method with reflection", e);
	    	 throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the content");
		}
		catch (Exception e) {
			 Throwable cause = e.getCause();
			 if (cause != null) {
			     ApplicationLogger.log(Level.WARNING, "Unable to invoke the method with reflection", cause);
			 } 
			 else {
			     ApplicationLogger.log(Level.WARNING, "Unable to invoke the method with reflection", e);
			 }
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the content");
		}
	
		return responseData;
	}
	
}
