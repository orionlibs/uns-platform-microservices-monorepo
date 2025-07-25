package io.github.orionlibs.user.model;

import io.github.orionlibs.core.cryptology.SHAEncodingKeyProvider;
import io.github.orionlibs.core.cryptology.HMACSHAEncryptionKeyProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
                @Index(name = "idx_uns_users", columnList = "id,username")
},
                uniqueConstraints = @UniqueConstraint(name = "uq_users_username", columnNames = "username_hash"))
public class UserModel implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false)
    @Convert(converter = AesGcmColumnConverter.class)
    private String username;
    @Column(name = "username_hash", length = 64, nullable = false, updatable = false)
    private String usernameHash;
    @Column(nullable = false)
    @Convert(converter = PasswordColumnConverter.class)
    private String password;
    @Column(nullable = false)
    private String authority;
    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public UserModel()
    {
        isEnabled = true;
    }


    public UserModel(String username, String password, String authority)
    {
        this();
        this.username = username;
        this.usernameHash = HMACSHAEncryptionKeyProvider.getNewHMACBase64(username, SHAEncodingKeyProvider.loadKey());
        this.password = password;
        this.authority = authority;
    }


    public String getAuthority()
    {
        return authority;
    }


    public void setUsername(String username)
    {
        this.username = username;
        this.usernameHash = HMACSHAEncryptionKeyProvider.getNewHMACBase64(username, SHAEncodingKeyProvider.loadKey());
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
                        .map(SimpleGrantedAuthority::new)
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
        return isEnabled;
    }


    public void setPassword(String password)
    {
        this.password = password;
    }


    public void setAuthority(String authority)
    {
        this.authority = authority;
    }


    public void setEnabled(boolean enabled)
    {
        isEnabled = enabled;
    }
}
