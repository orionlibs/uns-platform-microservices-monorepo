package io.github.orionlibs.user.setting;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.user.UserRegistrationService;
import io.github.orionlibs.user.UserService;
import io.github.orionlibs.user.model.UserDAO;
import io.github.orionlibs.user.registration.api.UserRegistrationRequest;
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
public class UserSettingsServiceTest
{
    @Autowired UserDAO userDAO;
    @Autowired UserRegistrationService userRegistrationService;
    @Autowired UserService userService;


    @BeforeEach
    void setup()
    {
        userDAO.deleteAll();
    }


    @Test
    void xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx()
    {
        odpodkpd
        UserRegistrationRequest request = UserRegistrationRequest.builder()
                        .username("me@email.com")
                        .password("4528")
                        .authority("USER")
                        .build();
        userRegistrationService.registerUser(request);
        UserDetails user = userService.loadUserByUsername("me@email.com");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("me@email.com");
        assertThat(user.getPassword().isEmpty()).isFalse();
        assertThat(user.getAuthorities()).isEqualTo(Set.of(new SimpleGrantedAuthority("USER")));
    }
}
