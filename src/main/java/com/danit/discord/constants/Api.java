package com.danit.discord.constants;

public class Api {
    // COMMON
    public static final String PREFIX = "/api/v1";
    public static final String PREFIX_WS = "/ws/v1";

    // AUTH
    public static final String AUTH = "/auth";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String REFRESH = "/refresh";
    public static final String LOGOUT = "/logout";
    public static final String ME = "/me";
    public static final String API_AUTH = PREFIX + AUTH;
    public static final String API_AUTH_WILDCARD = API_AUTH + "/**";
    public static final String API_AUTH_LOGIN = API_AUTH + LOGIN;
    public static final String API_AUTH_REGISTER = API_AUTH + REGISTER;
    public static final String API_AUTH_REFRESH = API_AUTH + REFRESH;
    public static final String API_AUTH_LOGOUT = API_AUTH + LOGOUT;
    public static final String API_AUTH_ME = API_AUTH + ME;

    // USERS
    public static final String USERS = "/users";

    // SERVERS
    public static final String SERVERS = "/servers";

    // CHANNELS
    public static final String CHANNELS = "/channels";

    // TEXT
    public static final String CHANNELS_TEXT = CHANNELS + "/text";
    public static final String CHANNELS_TEXT_LINK = CHANNELS + "/text/{link}";

    // WS
    public static final String PREFIX_WS_WILDCARD = PREFIX_WS + "/**";
    public static final String WS_CHAT = PREFIX_WS + "/chat";
    public static final String WS_CHAT_LINK = WS_CHAT + "/{link}";
}

