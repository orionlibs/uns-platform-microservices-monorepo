package io.github.orionlibs.service1;

import static org.junit.jupiter.api.Assertions.assertThat;

import io.github.orionlibs.core.api.APIError;
import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.UserAuthority;
import io.github.orionlibs.user.model.UserDAO;
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
    @Autowired
    private UserDAO userDAO;


    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp()
    {
        userDAO.deleteAll();
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/users";
    }


    @Test
    void saveUser()
    {
        RestAssured.baseURI = basePath;
        UserRegistrationRequest request = UserRegistrationRequest.builder()
                        .username("me@email.com")
                        .password("bunkzh3Z!")
                        .authority(UserAuthority.ADMINISTRATOR.name() + ",CUSTOMER")
                        .build();
        Response response = apiUtils.makePostAPICall(request, null);
        assertThat(response.statusCode()).isEqualTo(201);
    }


    @Test
    void saveUser_invalidUsername()
    {
        RestAssured.baseURI = basePath;
        UserRegistrationRequest request = UserRegistrationRequest.builder()
                        .username("me")
                        .password("bunkzh3Z!")
                        .authority(UserAuthority.ADMINISTRATOR.name() + ",CUSTOMER")
                        .build();
        Response response = apiUtils.makePostAPICall(request, null);
        assertThat(response.statusCode()).isEqualTo(400);
        APIError body = response.as(APIError.class);
        assertThat(body.fieldErrors().get(0).message()).isEqualTo("Invalid email address format");
    }
}
