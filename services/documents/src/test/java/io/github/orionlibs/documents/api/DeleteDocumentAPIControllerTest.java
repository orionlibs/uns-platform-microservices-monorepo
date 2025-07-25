package io.github.orionlibs.documents.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.orionlibs.core.api.HTTPHeader;
import io.github.orionlibs.core.api.HTTPHeaderValue;
import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.documents.ControllerUtils;
import io.github.orionlibs.documents.DocumentService;
import io.github.orionlibs.documents.model.DocumentModel;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
})
@ActiveProfiles("test")
class DeleteDocumentAPIControllerTest
{
    @LocalServerPort
    private int port;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private TestUtils utils;
    @Autowired
    private APITestUtils apiUtils;
    private String jwtToken;
    private HttpHeaders headers;


    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp()
    {
        documentService.deleteAll();
        utils.saveUser(port, ControllerUtils.baseAPIPath + "/users", "me@email.com", "bunkzh3Z!", "USER,DOCUMENT_MANAGER");
        jwtToken = utils.loginUserAndGetJWT(port, ControllerUtils.baseAPIPath + "/users/login", "me@email.com", "bunkzh3Z!");
        headers = new HttpHeaders();
        headers.add(HTTPHeader.Authorization.get(), HTTPHeaderValue.Bearer.get() + jwtToken);
        RestAssured.baseURI = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/documents";
    }


    @Test
    void getDocumentByID_noResults()
    {
        RestAssured.baseURI += "/100";
        Response response = apiUtils.makeDeleteAPICall(headers);
        assertEquals(200, response.statusCode());
    }


    @Test
    void getDocumentByID()
    {
        DocumentModel doc1 = utils.saveDocument("https://company.com/1.pdf");
        RestAssured.baseURI += "/" + doc1.getId();
        Response response = apiUtils.makeDeleteAPICall(headers);
        assertEquals(200, response.statusCode());
        response = apiUtils.makeGetAPICall(headers);
        assertEquals(404, response.statusCode());
    }
}
