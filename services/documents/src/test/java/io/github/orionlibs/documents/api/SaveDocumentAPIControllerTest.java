package io.github.orionlibs.documents.api;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.orionlibs.core.api.APIError;
import io.github.orionlibs.core.document.json.JSONService;
import io.github.orionlibs.documents.DocumentService;
import io.github.orionlibs.documents.model.DocumentType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class SaveDocumentAPIControllerTest
{
    @Autowired
    private JSONService jsonService;
    @Autowired
    private DocumentService documentService;
    //@Autowired
    //private TestUtils utils;
    private String basePath = "http://localhost:8080" + ControllerUtils.baseAPIPath + "/documents";


    @BeforeEach
    public void setUp()
    {
        documentService.deleteAll();
        RestAssured.useRelaxedHTTPSValidation();
    }


    @Test
    void saveDocument()
    {
        RestAssured.baseURI = basePath;
        NewDocumentDTO docToSave = saveDocumentRequest("https://company.com/1.pdf");
        Response response = makeAPICallToSaveDocument(docToSave);
        assertEquals(201, response.statusCode());
        assertTrue(response.header("Location").startsWith(ControllerUtils.baseAPIPath + "/documents"));
    }


    @Test
    void saveDocument_invalidDocumentType()
    {
        RestAssured.baseURI = basePath;
        NewDocumentDTO docToSave = saveDocumentRequestWithoutType("https://company.com/1.pdf");
        Response response = makeAPICallToSaveDocument(docToSave);
        assertEquals(400, response.statusCode());
        APIError body = response.as(APIError.class);
        assertEquals("type must not be blank", body.fieldErrors().get(0).message());
    }


    private NewDocumentDTO saveDocumentRequest(String docURL)
    {
        return new NewDocumentDTO(docURL, DocumentType.Type.DOCUMENTATION, "title", "description", LocalDateTime.now(), LocalDateTime.now());
    }


    private NewDocumentDTO saveDocumentRequestWithoutType(String docURL)
    {
        return new NewDocumentDTO(docURL, null, "title", "description", LocalDateTime.now(), LocalDateTime.now());
    }


    private Response makeAPICallToSaveDocument(NewDocumentDTO docToSave)
    {
        return given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(jsonService.toJson(docToSave))
                        .when()
                        .post()
                        .then()
                        .extract().response();
    }
}
