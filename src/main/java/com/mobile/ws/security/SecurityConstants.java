package com.mobile.ws.security;

import com.mobile.ws.SpringApplicationContext;

public class SecurityConstants {

	public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 10; // 10 Days
	public static final String SIGN_UP_URL = "/users";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

	public static String getTokenSecret() {
		AppProperties properties = (AppProperties) SpringApplicationContext.getBean("appProperties");
		return properties.getSecretToken();
	}

}
