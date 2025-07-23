package io.github.orionlibs.documents.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.orionlibs.core.tests.APITestUtils;
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


    @BeforeEach
    public void setUp()
    {
        documentService.deleteAll();
        RestAssured.baseURI = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/documents/types";
        RestAssured.useRelaxedHTTPSValidation();
    }


    @Test
    void getDocumentsByType_noResults()
    {
        RestAssured.baseURI += "/" + DocumentType.Type.DOCUMENTATION.name();
        Response response = apiUtils.makeGetAPICall();
        assertEquals(200, response.statusCode());
        DocumentsDTO body = response.as(DocumentsDTO.class);
        assertTrue(body.documents().isEmpty());
    }


    @Test
    void getDocumentsByType()
    {
        DocumentModel doc1 = utils.saveDocument("https://company.com/1.pdf");
        DocumentModel doc2 = utils.saveDocument("https://company.com/2.pdf");
        RestAssured.baseURI += "/" + DocumentType.Type.DOCUMENTATION.name();
        Response response = apiUtils.makeGetAPICall();
        assertEquals(200, response.statusCode());
        DocumentsDTO body = response.as(DocumentsDTO.class);
        assertThat(body.documents().size()).isEqualTo(2);
        assertThat(body.documents().get(0).documentURL()).isEqualTo("https://company.com/1.pdf");
        assertThat(body.documents().get(1).documentURL()).isEqualTo("https://company.com/2.pdf");
    }
}
