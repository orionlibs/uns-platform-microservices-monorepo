package io.github.orionlibs.user.model;

import java.util.Optional;

public interface UserDAOCustom
{
    Optional<UserModel> findByUsername(String username);


    Optional<UserModel> findByUsernameHash(String usernameHash);
}
