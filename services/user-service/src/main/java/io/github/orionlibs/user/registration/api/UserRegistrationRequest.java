package io.github.orionlibs.user.registration.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.orionlibs.core.user.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserRegistrationRequest implements Serializable
{
    @Email(message = "Invalid email address format")
    @JsonProperty("username")
    private String username;
    @Password
    @JsonProperty("password")
    private String password;
    @NotBlank(message = "Authority must not be blank")
    @JsonProperty("authority")
    private String authority;
}
