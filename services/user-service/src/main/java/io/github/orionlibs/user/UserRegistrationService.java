package io.github.orionlibs.user;

import io.github.orionlibs.core.Logger;
import io.github.orionlibs.core.cryptology.HMACSHAEncryptionKeyProvider;
import io.github.orionlibs.core.data.DuplicateRecordException;
import io.github.orionlibs.core.event.Publishable;
import io.github.orionlibs.user.event.EventUserRegistered;
import io.github.orionlibs.user.model.UserDAORepository;
import io.github.orionlibs.user.model.UserModel;
import io.github.orionlibs.user.registration.api.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService implements Publishable
{
    @Autowired
    private UserDAORepository dao;
    @Autowired
    private HMACSHAEncryptionKeyProvider hmacSHAEncryptionKeyProvider;


    @Transactional
    public void registerUser(UserRegistrationRequest request) throws DuplicateRecordException
    {
        UserModel newUser = new UserModel(hmacSHAEncryptionKeyProvider, request.getUsername(), request.getPassword(), request.getAuthority());
        try
        {
            dao.saveAndFlush(newUser);
            publish(EventUserRegistered.EVENT_NAME, EventUserRegistered.builder()
                            .username(request.getUsername())
                            .build());
            Logger.info("User saved");
        }
        catch(DataIntegrityViolationException | UnexpectedRollbackException e)
        {
            throw new DuplicateRecordException(e, "This user already exists");
        }
    }
}
