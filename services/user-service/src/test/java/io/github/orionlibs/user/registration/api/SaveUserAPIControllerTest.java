package io.github.orionlibs.user.registration.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.orionlibs.core.api.APIError;
import io.github.orionlibs.core.tests.APITestUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
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
                        .authority("ADMINISTRATOR,CUSTOMER")
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
                        .authority("ADMINISTRATOR,CUSTOMER")
                        .build();
        Response response = apiUtils.makePostAPICall(userToSave);
        assertEquals(400, response.statusCode());
        APIError body = response.as(APIError.class);
        assertEquals("must be a well-formed email address", body.fieldErrors().get(0).message());
    }


    @Test
    void saveUser_invalidPassword()
    {
        RestAssured.baseURI = basePath;
        UserRegistrationRequest userToSave = UserRegistrationRequest.builder()
                        .username("me@email.com")
                        .password("4528")
                        .authority("ADMINISTRATOR,CUSTOMER")
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
                        .username("me")
                        .password("bunkzh3Z!")
                        .authority("")
                        .build();
        Response response = apiUtils.makePostAPICall(userToSave);
        assertEquals(400, response.statusCode());
        APIError body = response.as(APIError.class);
        assertEquals("authority must not be blank", body.fieldErrors().get(0).message());
    }
}
