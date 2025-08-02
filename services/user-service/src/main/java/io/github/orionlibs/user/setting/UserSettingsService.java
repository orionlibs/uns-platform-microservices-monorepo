package io.github.orionlibs.user.setting;

import io.github.orionlibs.core.Logger;
import io.github.orionlibs.user.setting.model.UserSettingsDAO;
import io.github.orionlibs.user.setting.model.UserSettingsModel;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserSettingsService
{
    @Autowired
    private UserSettingsDAO dao;


    public Optional<UserSettingsModel> getByID(UUID settingID)
    {
        return dao.findById(settingID);
    }


    @Transactional
    public UserSettingsModel save(UserSettingsModel model)
    {
        UserSettingsModel saved = dao.saveAndFlush(model);
        Logger.info("User setting saved");
        return saved;
    }


    @Transactional
    public boolean update(UserSettingsModel model)
    {
        UserSettingsModel updated = save(model);
        Logger.info("Updated user setting");
        return true;
    }
}
