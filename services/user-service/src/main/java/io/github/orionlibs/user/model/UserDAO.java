package io.github.orionlibs.user.model;

import java.util.Optional;

public interface UserDAO
{
    Integer testConnection();


    Optional<UserModel> findByUsername(String username);


    Optional<UserModel> findByUsernameHash(String usernameHash);


    void deleteAll();


    UserModel save(UserModel model);
}
