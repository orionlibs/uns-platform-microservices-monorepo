package io.github.orionlibs.gateway_home.api;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.orionlibs.gateway_home.document.DocumentService;
import io.github.orionlibs.gateway_home.document.model.DocumentEntity;
import io.github.orionlibs.gateway_home.document.model.DocumentType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class GetDocumentsByTypeAPIControllerTest
{
    @Autowired
    private DocumentService documentService;
    @Value("http://localhost:8080/v1/documents/types")
    private String testUrl;


    @BeforeEach
    public void setUp()
    {
        documentService.deleteAll();
        RestAssured.baseURI = testUrl;
        RestAssured.useRelaxedHTTPSValidation();
    }


    @Test
    void getDocumentsByType_noResults()
    {
        RestAssured.baseURI += "/" + DocumentType.DOCUMENTATION.name();
        Response response = given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get()
                        .then()
                        .extract().response();
        assertEquals(200, response.statusCode());
        DocumentsDTO body = response.as(DocumentsDTO.class);
        assertTrue(body.documents().isEmpty());
    }


    @Test
    void getDocumentsByType()
    {
        DocumentEntity doc1 = saveDocument("https://company.com/1.pdf");
        DocumentEntity doc2 = saveDocument("https://company.com/2.pdf");
        RestAssured.baseURI += "/" + DocumentType.DOCUMENTATION.name();
        Response response = given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get()
                        .then()
                        .extract().response();
        assertEquals(200, response.statusCode());
        DocumentsDTO body = response.as(DocumentsDTO.class);
        assertThat(body.documents().size()).isEqualTo(2);
        assertThat(body.documents().get(0).documentURL()).isEqualTo("https://company.com/1.pdf");
        assertThat(body.documents().get(1).documentURL()).isEqualTo("https://company.com/2.pdf");
    }


    private DocumentEntity saveDocument(String documentURL)
    {
        DocumentEntity doc = new DocumentEntity(documentURL, DocumentType.DOCUMENTATION, "doc title 1", "doc description 1");
        return documentService.save(doc);
    }
}
