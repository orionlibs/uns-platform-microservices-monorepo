package io.github.orionlibs.user;

import io.github.orionlibs.user.model.UserDAO;
import io.github.orionlibs.user.model.UserModel;
import io.github.orionlibs.user.registration.api.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService
{
    @Autowired
    private UserDAO userDAO;


    @Transactional
    public void registerUser(UserRegistrationRequest request)
    {
        UserModel newUser = new UserModel(request.getUsername(), request.getPassword(), request.getAuthority());
        userDAO.save(newUser);
    }
}
