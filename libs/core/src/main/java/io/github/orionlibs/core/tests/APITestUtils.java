package io.github.orionlibs.core.tests;

import static io.restassured.RestAssured.given;

import io.github.orionlibs.core.document.json.JSONService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class APITestUtils
{
    @Autowired
    private JSONService jsonService;


    public Response makeGetAPICall()
    {
        log.info("[JUnit] making GET call");
        return given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .when()
                        .get()
                        .then()
                        .extract().response();
    }


    public Response makePostAPICall(Object objectToSave)
    {
        log.info("[JUnit] making POST call");
        return given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(jsonService.toJson(objectToSave))
                        .when()
                        .post()
                        .then()
                        .extract().response();
    }


    public Response makePutAPICall(Object objectToSave)
    {
        log.info("[JUnit] making PUT call");
        return given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(jsonService.toJson(objectToSave))
                        .when()
                        .put()
                        .then()
                        .extract().response();
    }


    public Response makeDeleteAPICall()
    {
        log.info("[JUnit] making DELETE call");
        return given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .when()
                        .delete()
                        .then()
                        .extract().response();
    }
}
