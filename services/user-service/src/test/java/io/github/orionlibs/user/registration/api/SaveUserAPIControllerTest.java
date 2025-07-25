package io.github.orionlibs.user.registration.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.orionlibs.core.api.APIError;
import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.UserAuthority;
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
class SaveUserAPIControllerTest
{
    @LocalServerPort
    private int port;
    @Autowired
    private APITestUtils apiUtils;
    private String basePath;


    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp()
    {
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/users";
        RestAssured.useRelaxedHTTPSValidation();
    }


    @Test
    void saveUser()
    {
        RestAssured.baseURI = basePath;
        UserRegistrationRequest userToSave = UserRegistrationRequest.builder()
                        .username("me@email.com")
                        .password("bunkzh3Z!")
                        .authority(UserAuthority.ADMINISTRATOR.name() + ",CUSTOMER")
                        .build();
        Response response = apiUtils.makePostAPICall(userToSave);
        System.out.println(response.body().prettyPrint());
        assertEquals(201, response.statusCode());
    }


    @Test
    void saveUser_invalidUsername()
    {
        RestAssured.baseURI = basePath;
        UserRegistrationRequest userToSave = UserRegistrationRequest.builder()
                        .username("me")
                        .password("bunkzh3Z!")
                        .authority(UserAuthority.ADMINISTRATOR.name() + ",CUSTOMER")
                        .build();
        Response response = apiUtils.makePostAPICall(userToSave);
        assertEquals(400, response.statusCode());
        APIError body = response.as(APIError.class);
        assertEquals("Invalid email address format", body.fieldErrors().get(0).message());
    }


    @Test
    void saveUser_duplicateUsername()
    {
        RestAssured.baseURI = basePath;
        UserRegistrationRequest userToSave = UserRegistrationRequest.builder()
                        .username("me@email.com")
                        .password("bunkzh3Z!")
                        .authority(UserAuthority.ADMINISTRATOR.name() + ",CUSTOMER")
                        .build();
        apiUtils.makePostAPICall(userToSave);
        Response response = apiUtils.makePostAPICall(userToSave);
        assertEquals(409, response.statusCode());
        APIError body = response.as(APIError.class);
        assertEquals("This user already exists", body.message());
    }


    @Test
    void saveUser_invalidPassword()
    {
        RestAssured.baseURI = basePath;
        UserRegistrationRequest userToSave = UserRegistrationRequest.builder()
                        .username("me@email.com")
                        .password("4528")
                        .authority(UserAuthority.ADMINISTRATOR.name() + ",CUSTOMER")
                        .build();
        Response response = apiUtils.makePostAPICall(userToSave);
        assertEquals(400, response.statusCode());
        APIError body = response.as(APIError.class);
        assertEquals("Password does not meet security requirements", body.fieldErrors().get(0).message());
    }


    @Test
    void saveUser_invalidAuthority()
    {
        RestAssured.baseURI = basePath;
        UserRegistrationRequest userToSave = UserRegistrationRequest.builder()
                        .username("me@email.com")
                        .password("bunkzh3Z!")
                        .authority("")
                        .build();
        Response response = apiUtils.makePostAPICall(userToSave);
        assertEquals(400, response.statusCode());
        APIError body = response.as(APIError.class);
        assertEquals("Authority must not be blank", body.fieldErrors().get(0).message());
    }


    @Test
    void saveUser_invalidUsernamePasswordAuthority()
    {
        RestAssured.baseURI = basePath;
        UserRegistrationRequest userToSave = UserRegistrationRequest.builder()
                        .username("me")
                        .password("4528")
                        .authority("")
                        .build();
        Response response = apiUtils.makePostAPICall(userToSave);
        assertEquals(400, response.statusCode());
        APIError body = response.as(APIError.class);
        Set<String> errorMessages = body.fieldErrors().stream().map(e -> e.message()).collect(Collectors.toSet());
        assertEquals(Set.of("Authority must not be blank",
                        "Password does not meet security requirements",
                        "Invalid email address format"), errorMessages);
    }
}
