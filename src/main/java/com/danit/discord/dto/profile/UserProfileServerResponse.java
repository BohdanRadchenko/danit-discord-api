package com.danit.discord.dto.profile;

import com.danit.discord.entities.UserProfile;
import lombok.Data;

@Data
public class UserProfileServerResponse extends UserProfileResponse {
    private Long server;

    public UserProfileServerResponse(UserProfileResponse userProfileResponse, Long server) {
        super(userProfileResponse);
        this.server = server;
    }

    public static UserProfileServerResponse of(UserProfile profile) {
        return new UserProfileServerResponse(UserProfileResponse.of(profile), profile.getServer());
    }
}
