package io.github.orionlibs.user.authentication.api;

import io.github.orionlibs.core.user.Password;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginRequest
{
    @Email(message = "Invalid email address format")
    private String username;
    @Password
    private String password;
}
