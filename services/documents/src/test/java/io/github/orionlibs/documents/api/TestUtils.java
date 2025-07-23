package io.github.orionlibs.documents.api;

import static io.restassured.RestAssured.given;

import io.github.orionlibs.core.document.json.JSONService;
import io.github.orionlibs.documents.DocumentService;
import io.github.orionlibs.documents.model.DocumentModel;
import io.github.orionlibs.documents.model.DocumentType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class TestUtils
{
    @Autowired
    private JSONService jsonService;
    @Autowired
    private DocumentService documentService;


    DocumentModel saveDocument(String documentURL)
    {
        DocumentModel doc = new DocumentModel(documentURL, DocumentType.Type.DOCUMENTATION, "document title 1", "document description 1");
        return documentService.save(doc);
    }


    Response makeGetAPICall()
    {
        return given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .when()
                        .get()
                        .then()
                        .extract().response();
    }


    Response makePostAPICall(Object objectToSave)
    {
        return given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(jsonService.toJson(objectToSave))
                        .when()
                        .post()
                        .then()
                        .extract().response();
    }


    Response makePutAPICall(Object objectToSave)
    {
        return given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(jsonService.toJson(objectToSave))
                        .when()
                        .put()
                        .then()
                        .extract().response();
    }


    Response makeDeleteAPICall()
    {
        return given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .when()
                        .delete()
                        .then()
                        .extract().response();
    }
}
