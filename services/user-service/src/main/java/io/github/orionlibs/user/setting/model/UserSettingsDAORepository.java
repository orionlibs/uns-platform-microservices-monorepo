package io.github.orionlibs.user.setting.model;

import io.github.orionlibs.user.model.UserDAO;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingsDAORepository extends JpaRepository<UserSettingsModel, UUID>, UserDAO
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();
}
