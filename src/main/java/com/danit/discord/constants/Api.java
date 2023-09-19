package com.danit.discord.constants;

public class Api {
    public static final String PREFIX = "/api/v1";
    // AUTH
    public static final String AUTH = "/auth";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String REFRESH = "/refresh";
    public static final String ME = "/me";
    public static final String API_AUTH = PREFIX + AUTH;
    public static final String API_AUTH_WILDCARD = API_AUTH + "/**";
    public static final String API_AUTH_LOGIN = API_AUTH + LOGIN;
    public static final String API_AUTH_REGISTER = API_AUTH + REGISTER;
    public static final String API_AUTH_REFRESH = API_AUTH + REFRESH;
    public static final String API_AUTH_ME = API_AUTH + ME;

    // USERS
    public static final String USERS = "/users";
}

