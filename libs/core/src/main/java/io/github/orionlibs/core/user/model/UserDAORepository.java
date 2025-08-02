package io.github.orionlibs.core.user.model;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAORepository extends JpaRepository<UserModel, UUID>
{
}
