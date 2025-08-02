package io.github.orionlibs.user.setting;

import io.github.orionlibs.user.setting.model.UserSettingsDAORepository;
import io.github.orionlibs.user.setting.model.UserSettingsModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserSettingsServiceTest
{
    @Autowired UserSettingsDAORepository dao;
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
