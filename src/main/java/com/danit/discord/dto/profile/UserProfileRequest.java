package com.danit.discord.dto.profile;

import com.danit.discord.annotations.ValidAvatar;
import com.danit.discord.utils.Palette;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
@Builder
@ToString
public class UserProfileRequest {
    private String name;
    private String pronouns;
    private String description;
    private String banner;
    private MultipartFile avatar;

    public Optional<@NotBlank(message = "Invalid name: empty name") String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getPronouns() {
        return Optional.ofNullable(pronouns);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<String> getBanner() {
        return Palette.isValidHexCode(banner)
                ? Optional.ofNullable(banner)
                : Optional.empty();
    }

    public Optional<@ValidAvatar MultipartFile> getAvatar() {
        return Optional.ofNullable(avatar);
    }
}
