package io.github.orionlibs.user.model;

import io.github.orionlibs.core.cryptology.HMACSHAEncryptionKeyProvider;
import io.github.orionlibs.core.cryptology.SHAEncodingKeyProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDAOCustomImpl implements UserDAOCustom
{
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private HMACSHAEncryptionKeyProvider hmacSHAEncryptionKeyProvider;


    @Override
    public Optional<UserModel> findByUsername(String username)
    {
        return findByUsernameHash(hmacSHAEncryptionKeyProvider.getNewHMACBase64(username, SHAEncodingKeyProvider.shaKey));
    }


    @Override
    public Optional<UserModel> findByUsernameHash(String usernameHash)
    {
        String jpql = String.format("SELECT m FROM %s m WHERE m.usernameHash = :usernameHash", UserModel.class.getName());
        try
        {
            return Optional.of(entityManager.createQuery(jpql, UserModel.class)
                            .setParameter("usernameHash", usernameHash)
                            .getSingleResult());
        }
        catch(NoResultException e)
        {
            return Optional.empty();
        }
    }
}
