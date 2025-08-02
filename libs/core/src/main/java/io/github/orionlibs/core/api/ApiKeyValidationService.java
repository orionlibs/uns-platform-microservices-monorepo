package io.github.orionlibs.core.api;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyValidationService
{
    @Autowired
    private ApiKeyDAO apiKeyDAO;
    @Autowired
    private UserDetailsService userService;


    public UserDetails validate(String apiKey, String apiSecret)
    {
        Optional<ApiKeyModel> opt = apiKeyDAO.findById(apiKey);
        if(opt.isEmpty())
        {
            return null;
        }
        ApiKeyModel model = opt.get();
        if(!model.getApiSecret().equals(apiSecret))
        {
            return null;
        }
        UUID userID = model.getUserID();
        return userService.loadUserByUsername(username);
    }
}
