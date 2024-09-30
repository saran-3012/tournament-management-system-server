package com.saran.tms.utils;

import java.util.HashMap;
import java.util.Map;


public class RequestParser {
	
	public static String checkSlash(String url) {
		if(url.charAt(url.length() - 1) != '/') {
			url += '/';
		}
		return url;
	}
	
	public static String truncateUrl(String url) {
		String parts[] = url.split("\\?");
		
		return checkSlash(parts[0]);
	}
	
	public static String buildUrlRegex(String url) {
		
        String pattern = "[^/]+";
        
        String[] parts = url.split("/");

        StringBuilder urlBuilder = new StringBuilder("");

        for (String part : parts) {
            if (!part.isEmpty()) {
                urlBuilder.append("/");
                
                if (part.charAt(0) == ':') {
                    urlBuilder.append(pattern);
                } 
                else {
                    urlBuilder.append(part);
                }
            }
        }

        return checkSlash(urlBuilder.toString());
	}
	
	public static Map<String, String> parseParams(String reqUrl, String routeUrl) {
		
		Map<String, String> params = new HashMap<>();
		
		String reqUrlParts[] = reqUrl.split("/");
		String routeUrlParts[] = routeUrl.split("/");

		int n = reqUrlParts.length;
		
		for(int i=0; i<n; i++) {
			String reqUrlPart = reqUrlParts[i];
			String routeUrlPart = routeUrlParts[i];
			if(reqUrlPart.isEmpty()) {
				continue;
			}
			if(routeUrlPart.charAt(0) == ':') {
				params.put(routeUrlPart.substring(1), reqUrlPart);
			}
		}
		
		return params;
	}

}
