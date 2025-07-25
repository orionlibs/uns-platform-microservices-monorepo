package io.github.orionlibs.user.authentication.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.orionlibs.core.api.APIError;
import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.UserRegistrationService;
import io.github.orionlibs.user.model.UserDAO;
import io.github.orionlibs.user.registration.api.UserRegistrationRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class LoginAPIControllerTest
{
    @LocalServerPort
    private int port;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private APITestUtils apiUtils;
    private String basePath;
    @Autowired
    private UserRegistrationService userRegistrationService;


    @BeforeEach
    public void setUp()
    {
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/users/login";
        RestAssured.useRelaxedHTTPSValidation();
        userDAO.deleteAll();
        userRegistrationService.registerUser(UserRegistrationRequest.builder()
                        .username("me@email.com")
                        .password("bunkzh3Z!")
                        .authority("USER")
                        .build());
    }


    @Test
    void loginUser()
    {
        RestAssured.baseURI = basePath;
        LoginRequest request = LoginRequest.builder()
                        .username("me@email.com")
                        .password("bunkzh3Z!")
                        .build();
        Response response = apiUtils.makePostAPICall(request);
        assertThat(response.statusCode()).isEqualTo(200);
    }


    @Test
    void loginUser_invalidUsername()
    {
        RestAssured.baseURI = basePath;
        LoginRequest request = LoginRequest.builder()
                        .username("")
                        .password("bunkzh3Z!")
                        .build();
        Response response = apiUtils.makePostAPICall(request);
        assertThat(response.statusCode()).isEqualTo(404);
        APIError body = response.as(APIError.class);
        assertThat(body.message()).isEqualTo("Resource not found: User not found");
    }


    @Test
    void loginUser_invalidPassword()
    {
        RestAssured.baseURI = basePath;
        LoginRequest request = LoginRequest.builder()
                        .username("me@email.com")
                        .password("4528")
                        .build();
        Response response = apiUtils.makePostAPICall(request);
        assertThat(response.statusCode()).isEqualTo(400);
        APIError body = response.as(APIError.class);
        assertThat(body.message()).isEqualTo("Validation failed for one or more fields");
        assertEquals("Password does not meet security requirements", body.fieldErrors().get(0).message());
    }


    @Test
    void loginUser_invalidUsernamePassword()
    {
        RestAssured.baseURI = basePath;
        LoginRequest request = LoginRequest.builder()
                        .username("me")
                        .password("4528")
                        .build();
        Response response = apiUtils.makePostAPICall(request);
        assertThat(response.statusCode()).isEqualTo(400);
        APIError body = response.as(APIError.class);
        Set<String> errorMessages = body.fieldErrors().stream().map(e -> e.message()).collect(Collectors.toSet());
        assertThat(body.message()).isEqualTo("Validation failed for one or more fields");
        assertEquals(Set.of("Password does not meet security requirements",
                        "Invalid email address format"), errorMessages);
    }
}
