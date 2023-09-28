package com.danit.discord.config;

import com.danit.discord.constants.Api;
import com.danit.discord.services.ChatService;
import com.danit.discord.sockets.handlers.WebSocketChatHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocket
@AllArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    @Lazy
    private final ChatService chatService;
    @Lazy
    private final WebSocketChatHandler WebSocketChatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(WebSocketChatHandler, Api.WS_ROOM_LINK)
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
                try {
                    String path = request.getURI().getPath();
                    String room = path.substring(path.lastIndexOf('/') + 1);
                    chatService.existByLink(room);
                    attributes.put("room", room);
                    return true;
                } catch (Exception ex) {
                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    response.flush();
                    return false;
                }
            }

            @Override
            public void afterHandshake(org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

            }
        };
    }

}