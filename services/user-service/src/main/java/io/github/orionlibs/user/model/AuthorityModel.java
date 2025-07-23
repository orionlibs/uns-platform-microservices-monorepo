package io.github.orionlibs.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "authorities", schema = "uns", indexes = {
                @Index(name = "idx_id", columnList = "id")
})
public class AuthorityModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "authority", nullable = false)
    private String authority;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;


    public AuthorityModel()
    {
    }


    public AuthorityModel(String authority)
    {
        this.authority = authority;
    }


    public UUID getId()
    {
        return id;
    }


    public String getAuthority()
    {
        return authority;
    }


    public UserModel getUser()
    {
        return user;
    }
}
