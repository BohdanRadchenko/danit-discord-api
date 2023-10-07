package com.danit.discord.dto.profile;

import com.danit.discord.entities.UserProfile;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserProfileResponse {
    private String name;
    private String avatar;
    private String description;
    private String banner;

    public static UserProfileResponse of(List<UserProfile> profiles) {
        UserProfile mainProfile = profiles.stream().filter(UserProfile::isMainProfile).findFirst().orElse(profiles.get(0));
        List<UserProfile> serversProfile = profiles.stream().filter(p -> !p.isMainProfile()).toList();
        return UserProfileResponse
                .builder()
                .name(mainProfile.getName())
                .avatar(mainProfile.getAvatar())
                .description(mainProfile.getDescription())
                .banner(mainProfile.getBanner())
                .build();
    }
}
