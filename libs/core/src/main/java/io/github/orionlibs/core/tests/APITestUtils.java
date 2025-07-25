package io.github.orionlibs.core.tests;

import static io.restassured.RestAssured.given;

import io.github.orionlibs.core.document.json.JSONService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class APITestUtils
{
    @Autowired
    private JSONService jsonService;


    public Response makeGetAPICall(HttpHeaders headers)
    {
        log.info("[JUnit] making GET call");
        RestAssured.defaultParser = Parser.JSON;
        headers = getHttpHeaders(headers);
        return given()
                        //.contentType(ContentType.JSON)
                        .headers(headers)
                        .accept(ContentType.JSON)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();
    }


    public Response makePostAPICall(Object objectToSave, HttpHeaders headers)
    {
        log.info("[JUnit] making POST call");
        RestAssured.defaultParser = Parser.JSON;
        headers = getHttpHeaders(headers);
        return given()
                        .contentType(ContentType.JSON)
                        .headers(headers)
                        .accept(ContentType.JSON)
                        .body(jsonService.toJson(objectToSave))
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();
    }


    public Response makePutAPICall(Object objectToSave, HttpHeaders headers)
    {
        log.info("[JUnit] making PUT call");
        RestAssured.defaultParser = Parser.JSON;
        headers = getHttpHeaders(headers);
        return given()
                        .contentType(ContentType.JSON)
                        .headers(headers)
                        .accept(ContentType.JSON)
                        .body(jsonService.toJson(objectToSave))
                        .when()
                        .put()
                        .then()
                        .extract()
                        .response();
    }


    public Response makeDeleteAPICall(HttpHeaders headers)
    {
        log.info("[JUnit] making DELETE call");
        RestAssured.defaultParser = Parser.JSON;
        headers = getHttpHeaders(headers);
        return given()
                        .contentType(ContentType.JSON)
                        .headers(headers)
                        .accept(ContentType.JSON)
                        .when()
                        .delete()
                        .then()
                        .extract()
                        .response();
    }


    private HttpHeaders getHttpHeaders(HttpHeaders headers)
    {
        if(headers == null)
        {
            headers = new HttpHeaders();
        }
        return headers;
    }
}
