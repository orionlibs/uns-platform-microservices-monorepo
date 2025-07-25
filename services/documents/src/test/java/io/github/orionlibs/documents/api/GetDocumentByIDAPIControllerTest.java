package io.github.orionlibs.documents.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GetDocumentByIDAPIControllerTest
{
    @LocalServerPort
    private int port;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private TestUtils utils;
    @Autowired
    private APITestUtils apiUtils;


    @BeforeEach
    public void setUp()
    {
        documentService.deleteAll();
        RestAssured.baseURI = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/documents";
        RestAssured.useRelaxedHTTPSValidation();
    }


    @Test
    void getDocumentByID_noResults()
    {
        RestAssured.baseURI += "/100";
        Response response = apiUtils.makeGetAPICall();
        assertEquals(404, response.statusCode());
    }


    @Test
    void getDocumentByID()
    {
        DocumentModel doc1 = utils.saveDocument("https://company.com/1.pdf");
        RestAssured.baseURI += "/" + doc1.getId();
        Response response = apiUtils.makeGetAPICall();
        assertEquals(200, response.statusCode());
        DocumentDTO body = response.as(DocumentDTO.class);
        assertEquals("https://company.com/1.pdf", body.documentURL());
    }
}
