package com.danit.discord.entities;

import com.danit.discord.enums.MessageType;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "message")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User from;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "message_type", nullable = false, updatable = false)
    private MessageType messageType;
    @Column()
    private String message;
    @Column()
    private String value;

    public static Message create(Chat chat, User user, String message) {
        return Message
                .builder()
                .chat(chat)
                .from(user)
                .messageType(MessageType.TEXT)
                .message(message)
                .build();
    }
}