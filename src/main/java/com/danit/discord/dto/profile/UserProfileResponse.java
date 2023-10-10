package com.danit.discord.dto.profile;

import com.danit.discord.entities.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private String userName;
    private String name;
    private String avatar;
    private String description;
    private String banner;

    public UserProfileResponse(UserProfileResponse profileResponse) {
        this.userName = profileResponse.userName;
        this.name = profileResponse.name;
        this.avatar = profileResponse.avatar;
        this.description = profileResponse.description;
        this.banner = profileResponse.banner;
    }

    public static UserProfileResponse of(UserProfile profile) {
        return UserProfileResponse
                .builder()
                .userName(profile.getUser().getUserName())
                .name(profile.getName())
                .avatar(profile.getAvatar())
                .description(profile.getDescription())
                .banner(profile.getBanner())
                .build();
    }
}
