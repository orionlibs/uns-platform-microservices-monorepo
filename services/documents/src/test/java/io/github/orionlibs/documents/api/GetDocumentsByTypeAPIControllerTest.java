package io.github.orionlibs.documents.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.documents.ControllerUtils;
import io.github.orionlibs.documents.DocumentService;
import io.github.orionlibs.documents.model.DocumentModel;
import io.github.orionlibs.documents.model.DocumentType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GetDocumentsByTypeAPIControllerTest
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
    public void setUp()
    {
        documentService.deleteAll();
        headers = new HttpHeaders();
        RestAssured.baseURI = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/documents/types";
    }


    @Test
    void getDocumentsByType_noResults()
    {
        RestAssured.baseURI += "/" + DocumentType.Type.DOCUMENTATION.name();
        Response response = apiUtils.makeGetAPICall(null, "Jimmy", "DOCUMENT_MANAGER");
        assertEquals(200, response.statusCode());
        DocumentsDTO body = response.as(DocumentsDTO.class);
        assertTrue(body.documents().isEmpty());
    }


    @Test
    void getDocumentsByType_noResults_anonymous()
    {
        RestAssured.baseURI += "/" + DocumentType.Type.DOCUMENTATION.name();
        Response response = apiUtils.makeGetAPICall(null);
        assertEquals(403, response.statusCode());
    }


    @Test
    void getDocumentsByType()
    {
        DocumentModel doc1 = utils.saveDocument("https://company.com/1.pdf");
        DocumentModel doc2 = utils.saveDocument("https://company.com/2.pdf");
        RestAssured.baseURI += "/" + DocumentType.Type.DOCUMENTATION.name();
        Response response = apiUtils.makeGetAPICall(null, "Jimmy", "DOCUMENT_MANAGER");
        assertEquals(200, response.statusCode());
        DocumentsDTO body = response.as(DocumentsDTO.class);
        assertThat(body.documents().size()).isEqualTo(2);
        assertThat(body.documents().get(0).documentURL()).isEqualTo("https://company.com/1.pdf");
        assertThat(body.documents().get(1).documentURL()).isEqualTo("https://company.com/2.pdf");
    }


    @Test
    void getDocumentsByType_anonymous()
    {
        DocumentModel doc1 = utils.saveDocument("https://company.com/1.pdf");
        DocumentModel doc2 = utils.saveDocument("https://company.com/2.pdf");
        RestAssured.baseURI += "/" + DocumentType.Type.DOCUMENTATION.name();
        Response response = apiUtils.makeGetAPICall(null);
        assertEquals(403, response.statusCode());
    }
}
