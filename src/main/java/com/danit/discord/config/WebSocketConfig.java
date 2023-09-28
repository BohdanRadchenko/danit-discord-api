package com.danit.discord.config;

import com.danit.discord.constants.Api;
import com.danit.discord.sockets.handlers.WebSocketChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(webSocketTextHandler(), Api.WS_CHAT_LINK)
                .setAllowedOriginPatterns("*")
                .addInterceptors(chatInterceptor());
    }

    @Bean
    public HandshakeInterceptor chatInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(
                    org.springframework.http.server.ServerHttpRequest request,
                    org.springframework.http.server.ServerHttpResponse response,
                    WebSocketHandler wsHandler, Map<String, Object> attributes
            ) throws Exception {
                String path = request.getURI().getPath();
                String chat = path.substring(path.lastIndexOf('/') + 1);
                attributes.put("chat", chat);
                return true;
            }

            @Override
            public void afterHandshake(org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

            }
        };
    }

    @Bean
    public WebSocketHandler webSocketTextHandler() {
        return new WebSocketChatHandler();
    }
}