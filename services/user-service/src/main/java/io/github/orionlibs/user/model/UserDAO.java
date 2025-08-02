package io.github.orionlibs.user.model;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<UserModel, UUID>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();
}
