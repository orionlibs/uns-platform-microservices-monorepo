package io.github.orionlibs.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users", schema = "uns", indexes = {
                @Index(name = "idx_id", columnList = "id"),
                @Index(name = "idx_username", columnList = "username")
})
public class UserModel implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "authority", nullable = false)
    private String authority;
    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled = true;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public UserModel()
    {
    }


    public UserModel(String username, String password)
    {
        this.username = username;
        this.password = password;
    }


    public String getAuthority()
    {
        return authority;
    }


    public LocalDateTime getUpdatedAt()
    {
        return updatedAt;
    }


    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }


    public UUID getId()
    {
        return id;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Arrays.stream(authority.split(","))
                        .map(authority -> new SimpleGrantedAuthority(authority))
                        .collect(Collectors.toSet());
    }


    @Override
    public String getPassword()
    {
        return "";
    }


    @Override
    public String getUsername()
    {
        return username;
    }


    @Override
    public boolean isAccountNonExpired()
    {
        return UserDetails.super.isAccountNonExpired();
    }


    @Override
    public boolean isAccountNonLocked()
    {
        return UserDetails.super.isAccountNonLocked();
    }


    @Override
    public boolean isCredentialsNonExpired()
    {
        return UserDetails.super.isCredentialsNonExpired();
    }


    @Override
    public boolean isEnabled()
    {
        return isEnabled();
    }
}
