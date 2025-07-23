package io.github.orionlibs.user.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserDAOTest
{
    @Autowired
    private UserDAO userDAO;


    @Test
    void saveUser()
    {
        // given
        UserModel user = saveUser("me@email.com", "4528");
        // then
        assertThat(user).isNotNull();
        assertThat(user.getId().toString().length()).isGreaterThan(20);
        assertThat(user.getUsername()).isEqualTo("me@email.com");
        assertThat(user.getPassword()).isEqualTo("");
        assertThat(user.getAuthorities()).isEqualTo("ADMINISTRATOR");
        assertThat(user.getAuthority()).isNull();
    }


    private UserModel saveUser(String username, String password)
    {
        UserModel userModel = new UserModel(username, password);
        return userDAO.save(userModel);
    }
}
