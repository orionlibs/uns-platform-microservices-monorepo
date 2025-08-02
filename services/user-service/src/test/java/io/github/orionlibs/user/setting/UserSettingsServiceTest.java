package io.github.orionlibs.user.setting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.github.orionlibs.core.cryptology.HMACSHAEncryptionKeyProvider;
import io.github.orionlibs.user.model.UserDAO;
import io.github.orionlibs.user.model.UserModel;
import io.github.orionlibs.user.setting.model.UserSettingsDAORepository;
import io.github.orionlibs.user.setting.model.UserSettingsModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserSettingsServiceTest
{
    @Autowired UserDAO userDAO;
    @Autowired UserSettingsDAORepository dao;
    @Autowired UserSettingsService userSettingsService;
    @Autowired HMACSHAEncryptionKeyProvider hmacSHAEncryptionKeyProvider;


    @BeforeEach
    void setup()
    {
        dao.deleteAll();
        userDAO.deleteAll();
    }


    @Test
    void saveUserSetting()
    {
        UserModel newUser = userDAO.save(new UserModel(hmacSHAEncryptionKeyProvider, "me@email.com", "4528", "USER"));
        UserSettingsModel setting = new UserSettingsModel(newUser, "setting1", "yes");
        setting = userSettingsService.save(setting);
        assertThat(setting).isNotNull();
        assertThat(setting.getSettingName()).isEqualTo("setting1");
        assertThat(setting.getSettingValue()).isEqualTo("yes");
        assertThat(setting.getUser().getUsername()).isEqualTo("me@email.com");
    }


    @Test
    void saveUserSetting_noUserInDatabase()
    {
        UserModel newUser = new UserModel(hmacSHAEncryptionKeyProvider, "me@email.com", "4528", "USER");
        UserSettingsModel setting = new UserSettingsModel(newUser, "setting1", "yes");
        assertThatThrownBy(() -> userSettingsService.save(setting)).isInstanceOf(InvalidDataAccessApiUsageException.class)
                        .hasMessageContaining("TransientPropertyValueException: Not-null property references a transient value");
    }
}
