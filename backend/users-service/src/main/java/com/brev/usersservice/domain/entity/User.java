package com.brev.usersservice.domain.entity;

import com.brev.core.domain.Role;
import com.brev.core.jwt.TokenInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users", indexes = @Index(columnList = "username"))
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "last_login")
    @LastModifiedDate
    private OffsetDateTime lastLogin;

    @Version
    @Column(name = "version")
    private Integer version;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = OffsetDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }


    @Override
    public boolean isAccountNonExpired() {
        return false; // Not supported
    }

    @Override
    public boolean isAccountNonLocked() {
        return false; // Not supported
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false; // Not supported
    }

    @Override
    public boolean isEnabled() {
        return false; // Not supported
    }

    public TokenInfo toTokenInfo() {
        return new TokenInfo(getId(), getUsername(), getRole());
    }
}
