package io.github.orionlibs.documents.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.orionlibs.documents.DocumentService;
import io.github.orionlibs.documents.model.DocumentModel;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class DeleteDocumentAPIControllerTest
{
    @Autowired
    private DocumentService documentService;
    @Autowired
    private TestUtils utils;
    @Value("http://localhost:8080/v1/documents")
    private String testUrl;


    @BeforeEach
    public void setUp()
    {
        documentService.deleteAll();
        RestAssured.baseURI = testUrl;
        RestAssured.useRelaxedHTTPSValidation();
    }


    @Test
    void getDocumentByID_noResults()
    {
        RestAssured.baseURI += "/100";
        Response response = utils.makeDeleteAPICall();
        assertEquals(200, response.statusCode());
    }


    @Test
    void getDocumentByID()
    {
        DocumentModel doc1 = utils.saveDocument("https://company.com/1.pdf");
        RestAssured.baseURI += "/" + doc1.getId();
        Response response = utils.makeDeleteAPICall();
        assertEquals(200, response.statusCode());
        response = utils.makeGetAPICall();
        assertEquals(404, response.statusCode());
    }
}
