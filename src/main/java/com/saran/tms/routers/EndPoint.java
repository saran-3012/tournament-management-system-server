package com.saran.tms.routers;

import java.util.Objects;
import java.util.regex.Pattern;

import com.saran.tms.utils.RequestParser;

public class EndPoint {
	
	private String rootPath;
	private String url;
	private String regexUrl;
	private String method;
	
	public EndPoint(String rootPath, String url) {
		this.rootPath = rootPath;
		this.url = url;
		this.regexUrl = RequestParser.buildUrlRegex(url);
		this.method = "GET";
	}
	
	public EndPoint(String rootPath, String url, String method) {
		this.rootPath = rootPath;
		this.url = url;
		this.regexUrl = RequestParser.buildUrlRegex(url);
		this.method = method;
	}

	
	public String getRootPath() {
		return rootPath;
	}

	public String getUrl() {
		return url;
	}
	
	public String getRegexUrl() {
		return regexUrl;
	}

	public String getMethod() {
		return method;
	}

	@Override
	public int hashCode() {
		return Objects.hash(rootPath, method, url.split("/")[1]);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EndPoint other = (EndPoint) obj;
		return Objects.equals(rootPath, other.rootPath) && Objects.equals(method, other.method) && Pattern.compile(other.regexUrl).matcher(url).matches();
	}

	@Override
	public String toString() {
		return "EndPoint [rootPath=" + rootPath + ", url=" + url + ", regexUrl=" + regexUrl + ", method=" + method
				+ "]";
	}
	
}
