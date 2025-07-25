package io.github.orionlibs.user.authentication.api;

import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.authentication.JWTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerUtils.baseAPIPath)
@Validated
@Tag(name = "User login", description = "User manager")
public class LoginAPIController
{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;


    @Operation(
                    summary = "Login user",
                    description = "Login user",
                    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    content = @Content(
                                                    schema = @Schema(implementation = LoginRequest.class)
                                    )
                    ),
                    responses = {@ApiResponse(responseCode = "200", description = "User logged in"),
                                    @ApiResponse(responseCode = "400", description = "Invalid input")}
    )
    @PostMapping(value = "/users/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest requestBean)
    {
        Authentication auth = new UsernamePasswordAuthenticationToken(requestBean.getUsername(), requestBean.getPassword());
        authenticationManager.authenticate(auth);
        String token = jwtService.generateToken((UserDetails)auth.getPrincipal());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
