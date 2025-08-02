package io.github.orionlibs.user;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.cryptology.HMACSHAEncryptionKeyProvider;
import io.github.orionlibs.user.model.UserDAO;
import io.github.orionlibs.user.model.UserModel;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest
{
    @Autowired UserDAO dao;
    @Autowired UserRegistrationService userRegistrationService;
    @Autowired UserService userService;
    @Autowired HMACSHAEncryptionKeyProvider hmacSHAEncryptionKeyProvider;


    @BeforeEach
    void setup()
    {
        dao.deleteAll();
    }


    @Test
    void loadUserByUsername()
    {
        UserModel newUser = dao.save(new UserModel(hmacSHAEncryptionKeyProvider, "me@email.com", "4528", "USER"));
        assertThat(newUser.getCreatedAt()).isNotNull();
        assertThat(newUser.getUpdatedAt()).isNotNull();
        UserDetails user = userService.loadUserByUsername("me@email.com");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("me@email.com");
        assertThat(user.getPassword().isEmpty()).isFalse();
        assertThat(user.getAuthorities()).isEqualTo(Set.of(new SimpleGrantedAuthority("USER")));
    }
}
