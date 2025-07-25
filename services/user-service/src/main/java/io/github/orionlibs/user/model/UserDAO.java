package io.github.orionlibs.user.model;

import io.github.orionlibs.core.cryptology.HMACSHAEncryptionKeyProvider;
import io.github.orionlibs.core.cryptology.SHAEncodingKeyProvider;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<UserModel, UUID>
{
    default Optional<UserModel> findByUsername(String username)
    {
        return findByUsernameHash(HMACSHAEncryptionKeyProvider.getNewHMACBase64(username, SHAEncodingKeyProvider.loadKey()));
    }


    Optional<UserModel> findByUsernameHash(String usernameHash);
}
