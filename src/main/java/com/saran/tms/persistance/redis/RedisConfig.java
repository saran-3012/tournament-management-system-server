package com.saran.tms.persistance.redis;

import com.saran.tms.persistance.DataStoreConfig;

public class RedisConfig implements DataStoreConfig {
	private String host;
	private String user;
	private String password;
	private int port;
	private String url;
	
	public RedisConfig(String _host, int _port) {
		this.host = _host;
		this.port = _port;
	}
	
	public RedisConfig(String _url) {
		this.url = _url;
	}

	public String getHost() {
		return host;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	public String getUrl() {
		return url;
	}

}
