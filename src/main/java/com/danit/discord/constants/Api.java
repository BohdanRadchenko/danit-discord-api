package com.danit.discord.constants;

public class Api {
    // VARIABLES
    public static final String PARAM_LINK = "link";

    // COMMON
    public static final String PREFIX = "/api/v1";
    public static final String PREFIX_WS = "/ws/v1";
    public static final String LINK = "/{link}";
    public static final String LINK_INVITE = "/{link}/invite";
    public static final String ROOM = "/room";

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

    // PROFILE
    public static final String PROFILE = "/profile";

    // USERS
    public static final String USERS = "/users";

    // SERVERS
    public static final String SERVERS = "/servers";

    // CHANNELS
    public static final String CHANNELS = "/channels";

    // MESSAGES
    public static final String MESSAGES = "/messages";
    public static final String TYPES = "/types";
    public static final String MESSAGES_TYPES = MESSAGES + TYPES;

    // TEXT
    public static final String CHANNELS_TEXT = CHANNELS + "/text";
    public static final String CHANNELS_TEXT_LINK = CHANNELS_TEXT + LINK;
    public static final String CHANNELS_TEXT_LINK_INVITE = CHANNELS_TEXT + LINK_INVITE;

    // WS
    public static final String PREFIX_WS_WILDCARD = PREFIX_WS + "/**";
    public static final String WS_ROOM = PREFIX_WS + ROOM;
    public static final String WS_ROOM_LINK = WS_ROOM + "/{link}";
}

