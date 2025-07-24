package io.github.orionlibs.user;

import io.github.orionlibs.core.data.DuplicateRecordException;
import io.github.orionlibs.user.model.UserDAO;
import io.github.orionlibs.user.model.UserModel;
import io.github.orionlibs.user.registration.api.UserRegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserRegistrationService
{
    @Autowired
    private UserDAO userDAO;


    @Transactional
    public void registerUser(UserRegistrationRequest request) throws DuplicateRecordException
    {
        UserModel newUser = new UserModel(request.getUsername(), request.getPassword(), request.getAuthority());
        try
        {
            userDAO.saveAndFlush(newUser);
            log.info("User saved");
        }
        catch(DataIntegrityViolationException | UnexpectedRollbackException e)
        {
            throw new DuplicateRecordException(e, "This user already exists");
        }
    }
}
