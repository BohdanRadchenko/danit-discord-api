package com.danit.discord.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Data
@ToString
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity implements UserDetails {
    @Column(unique = true, nullable = false, updatable = false)
    @Email(regexp = ".*@.*\\..*", message = "Email should be valid")
    private String email;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String name;
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    @Column
    private String avatar;
    @OneToMany(mappedBy = "owner")
    private List<Server> owneredServers = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "text-user",
            inverseJoinColumns = @JoinColumn(name = "channel_id",
                    nullable = false,
                    updatable = false),
            joinColumns = @JoinColumn(name = "user_id",
                    nullable = false,
                    updatable = false),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private List<TextChannel> textChannels = new ArrayList<>();
    @OneToMany(mappedBy = "from")
    private List<Message> messages;
    @ManyToMany
    @JoinTable(name = "friends",
            inverseJoinColumns = @JoinColumn(name = "user_1",
                    nullable = false,
                    updatable = false),
            joinColumns = @JoinColumn(name = "user_2",
                    nullable = false,
                    updatable = false),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private List<User> friends = new ArrayList<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getUserName() {
        return username;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
