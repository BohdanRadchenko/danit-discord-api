package com.danit.discord.dto.user;

import com.danit.discord.entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserMessageResponse {
    private Long id;
    private String userName;
    private String avatar;

    public static UserMessageResponse of(User user) {
        return UserMessageResponse
                .builder()
                .id(user.getId())
                .userName(user.getUserName())
                .avatar(user.getAvatar())
                .build();
    }
}
