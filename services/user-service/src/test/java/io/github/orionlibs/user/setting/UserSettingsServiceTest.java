package io.github.orionlibs.user.setting;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.user.UserRegistrationService;
import io.github.orionlibs.user.UserService;
import io.github.orionlibs.user.model.UserDAO;
import io.github.orionlibs.user.registration.api.UserRegistrationRequest;
import io.github.orionlibs.user.setting.model.UserSettingsDAO;
import io.github.orionlibs.user.setting.model.UserSettingsModel;
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
    @Autowired UserSettingsDAO dao;
    @Autowired UserSettingsService userSettingsService;


    @BeforeEach
    void setup()
    {
        dao.deleteAll();
    }


    @Test
    void saveUserSetting()
    {
        UserSettingsModel setting = new UserSettingsModel();
        //userSettingsService.save();
        //assertThat(user).isNotNull();
        //assertThat(user.getUsername()).isEqualTo("me@email.com");
    }
}
