package io.github.orionlibs.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.github.orionlibs.core.data.DuplicateRecordException;
import io.github.orionlibs.user.model.UserDAO;
import io.github.orionlibs.user.model.UserModel;
import io.github.orionlibs.user.registration.api.UserRegistrationRequest;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserRegistrationServiceTest
{
    @Autowired UserDAO userDAO;
    @Autowired UserRegistrationService userRegistrationService;


    @BeforeEach
    void setup()
    {
        userDAO.deleteAll();
    }


    @Test
    void registerUser()
    {
        UserRegistrationRequest request = UserRegistrationRequest.builder()
                        .username("me@email.com")
                        .password("4528")
                        .authority("USER")
                        .build();
        userRegistrationService.registerUser(request);
        UserModel user = userDAO.findByUsername("me@email.com").get();
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("me@email.com");
        assertThat(user.getPassword().isEmpty()).isFalse();
        assertThat(user.getAuthority()).isEqualTo("USER");
        assertThat(user.getAuthorities()).isEqualTo(Set.of(new SimpleGrantedAuthority("USER")));
    }


    @Test
    void registerUser_duplicateUser()
    {
        UserRegistrationRequest request = UserRegistrationRequest.builder()
                        .username("me@email.com")
                        .password("4528")
                        .authority("USER")
                        .build();
        userRegistrationService.registerUser(request);
        assertThatThrownBy(() -> userRegistrationService.registerUser(request)).isInstanceOf(DuplicateRecordException.class)
                        .hasMessage("This user already exists");
    }
}
