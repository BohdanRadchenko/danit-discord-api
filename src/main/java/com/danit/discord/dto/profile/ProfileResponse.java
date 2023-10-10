package com.danit.discord.dto.profile;

import com.danit.discord.entities.UserProfile;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ProfileResponse extends UserProfileResponse {
    private Map<Long, UserProfileServerResponse> servers;

    public ProfileResponse(UserProfileResponse userProfileResponse, Map<Long, UserProfileServerResponse> servers) {
        super(userProfileResponse);
        this.servers = servers;
    }

    public static ProfileResponse of(List<UserProfile> profiles) {
        UserProfile mainProfile = profiles.stream().filter(UserProfile::isMainProfile).findFirst().orElse(profiles.get(0));
        List<UserProfile> serversProfile = profiles.stream().filter(p -> !p.isMainProfile()).toList();
        HashMap<Long, UserProfileServerResponse> servers = new HashMap<>();
        for (UserProfile profile : serversProfile) {
            servers.put(profile.getServer(), UserProfileServerResponse.of(profile));
        }
        return new ProfileResponse(UserProfileResponse.of(mainProfile), servers);
    }

}
