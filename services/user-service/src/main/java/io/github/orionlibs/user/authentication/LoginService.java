package io.github.orionlibs.user.authentication;

import io.github.orionlibs.core.data.ResourceNotFoundException;
import io.github.orionlibs.core.jwt.JWTService;
import io.github.orionlibs.user.UserService;
import io.github.orionlibs.user.authentication.api.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService
{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;


    public String loginUser(LoginRequest requestBean) throws ResourceNotFoundException
    {
        Authentication auth = new UsernamePasswordAuthenticationToken(requestBean.getUsername(), requestBean.getPassword());
        try
        {
            UserDetails user = userService.loadUserByUsername(requestBean.getUsername());
            authenticationManager.authenticate(auth);
            return jwtService.generateToken((String)auth.getPrincipal(), user.getAuthorities());
        }
        catch(UsernameNotFoundException e)
        {
            throw new ResourceNotFoundException(e, "User not found");
        }
    }
}
