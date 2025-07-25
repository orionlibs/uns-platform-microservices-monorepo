package io.github.orionlibs.user.model;

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


    @Test
    void updateUser()
    {
        user.setEnabled(false);
        user.setPassword("4528");
        user.setAuthority(UserAuthority.ADMINISTRATOR.name() + ",CUSTOMER,SUPPORT");
        userDAO.save(user);
        assertThat(user).isNotNull();
        assertThat(user.getId().toString().length()).isGreaterThan(20);
        assertThat(user.getUsername()).isEqualTo("me@email.com");
        assertThat(user.getPassword()).isEqualTo("4528");
        assertThat(user.getAuthority()).isEqualTo(UserAuthority.ADMINISTRATOR.name() + ",CUSTOMER,SUPPORT");
        assertThat(user.getAuthorities()).isEqualTo(Set.of(new SimpleGrantedAuthority(UserAuthority.ADMINISTRATOR.name()),
                        new SimpleGrantedAuthority("CUSTOMER"),
                        new SimpleGrantedAuthority("SUPPORT")));
        assertThat(user.isEnabled()).isFalse();
    }


    @Test
    void deleteUser()
    {
        userDAO.delete(user);
        Optional<UserModel> user1 = userDAO.findById(user.getId());
        assertThat(user1.isEmpty()).isTrue();
    }


    private UserModel saveUser(String username, String password, String authority)
    {
        UserModel userModel = new UserModel(username, password, authority);
        return userDAO.save(userModel);
    }
}
