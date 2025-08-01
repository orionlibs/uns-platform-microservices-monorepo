package io.github.orionlibs.service1.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.api.APIError;
import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.service1.ControllerUtils;
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
    @LocalServerPort int port;
    @Autowired APITestUtils apiUtils;
    String basePath;


    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp()
    {
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/users";
    }


    @Test
    void someTest2()
    {
        RestAssured.baseURI = basePath;
        RequestBean request = RequestBean.builder()
                        .username("me@email.com")
                        .build();
        Response response = apiUtils.makePostAPICall(request, null);
        assertThat(response.statusCode()).isEqualTo(201);
    }
}
