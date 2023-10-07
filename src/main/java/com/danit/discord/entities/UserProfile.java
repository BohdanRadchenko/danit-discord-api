package com.danit.discord.entities;

import com.danit.discord.dto.profile.UserProfileRequest;
import com.danit.discord.utils.Palette;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity(name = "users_profile")
@Data
@ToString
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column()
    private Long server;
    @Column(nullable = false)
    private String name;
    @Column
    private String avatar;
    @Column
    private String description;
    @Column
    private String banner;

    public boolean isMainProfile() {
        return server == null;
    }

    public static UserProfile create(User user, UserProfileRequest request) {
        String bannerColor = request.getBanner() != null ? request.getBanner() : Palette.getRandomColor();
        return UserProfile
                .builder()
                .user(user)
                .name(request.getName())
                .banner(bannerColor)
                .build();
    }
}
