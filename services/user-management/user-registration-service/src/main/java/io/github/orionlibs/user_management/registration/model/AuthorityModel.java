package io.github.orionlibs.user_management.registration.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "authorities", schema = "uns", indexes = {
                @Index(name = "idx_id", columnList = "user_id")
})
public class AuthorityModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", length = 40, nullable = false)
    private String userID;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "authority", nullable = false)
    private String authority;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public AuthorityModel()
    {
    }


    public AuthorityModel(String username, String authority)
    {
        this.username = username;
        this.authority = authority;
    }


    public String getUserID()
    {
        return userID;
    }


    public void setUserID(String userID)
    {
        this.userID = userID;
    }


    public String getUsername()
    {
        return username;
    }


    public void setUsername(String username)
    {
        this.username = username;
    }


    public String getAuthority()
    {
        return authority;
    }


    public void setAuthority(String authority)
    {
        this.authority = authority;
    }


    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }


    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }


    public LocalDateTime getUpdatedAt()
    {
        return updatedAt;
    }


    public void setUpdatedAt(LocalDateTime updatedAt)
    {
        this.updatedAt = updatedAt;
    }
}
