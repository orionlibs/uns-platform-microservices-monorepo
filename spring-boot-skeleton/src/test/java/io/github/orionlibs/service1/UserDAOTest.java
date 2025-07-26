package io.github.orionlibs.service1;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.user.UserAuthority;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserDAOTest
{
    @Autowired
    private UserDAO userDAO;
    private UserModel user;


    @BeforeEach
    void setup()
    {
        userDAO.deleteAll();
        user = saveUser("me@email.com", "4528", UserAuthority.ADMINISTRATOR.name() + ",CUSTOMER");
    }


    @Test
    void saveUser()
    {
        assertThat(user).isNotNull();
        assertThat(user.getId().toString().length()).isGreaterThan(20);
        assertThat(user.getUsername()).isEqualTo("me@email.com");
        assertThat(user.getPassword()).isEqualTo("4528");
        assertThat(user.getAuthority()).isEqualTo(UserAuthority.ADMINISTRATOR.name() + ",CUSTOMER");
        assertThat(user.getAuthorities()).isEqualTo(Set.of(new SimpleGrantedAuthority(UserAuthority.ADMINISTRATOR.name()), new SimpleGrantedAuthority("CUSTOMER")));
        assertThat(user.isEnabled()).isTrue();
    }


    private UserModel saveUser(String username, String password, String authority)
    {
        UserModel userModel = new UserModel(username, password, authority);
        return userDAO.save(userModel);
    }
}
