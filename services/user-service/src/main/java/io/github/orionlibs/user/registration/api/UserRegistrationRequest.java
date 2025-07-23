package io.github.orionlibs.user.registration.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegistrationRequest implements Serializable
{
    @NotBlank(message = "username must not be blank")
    @JsonProperty("username")
    private String username;
    @NotBlank(message = "password must not be blank")
    @JsonProperty("password")
    private String password;
    @NotBlank(message = "authority must not be blank")
    @JsonProperty("authority")
    private String authority;
}
