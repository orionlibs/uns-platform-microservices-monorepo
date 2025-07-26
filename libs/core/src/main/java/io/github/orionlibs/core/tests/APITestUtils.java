package io.github.orionlibs.core.tests;

import static io.restassured.RestAssured.given;

import io.github.orionlibs.core.document.json.JSONService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class APITestUtils
{
    //private static final String RAW_SECRET = "myTestSecretKey1234567890";
    //private static final String BASE64_SECRET = Base64.getEncoder().encodeToString(RAW_SECRET.getBytes());
    @Autowired
    private JSONService jsonService;
    @Value("${jwt.secret}")
    String base64Secret;


    private Key getSigningKey()
    {
        byte[] keyBytes = Base64.getDecoder().decode(base64Secret);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }


    private String jwtWithAuthorities(String... authorities)
    {
        return Jwts.builder()
                        .setSubject("Jimmy")
                        .claim("authorities", List.of(authorities))
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 3600_000))
                        .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                        .compact();
    }


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


    public Response makePostAPICall(Object objectToSave, HttpHeaders headers, String commaSeparatedAuthorities)
    {
        log.info("[JUnit] making POST call");
        RestAssured.defaultParser = Parser.JSON;
        headers = getHttpHeaders(headers);
        return given()
                        .auth().oauth2(jwtWithAuthorities(commaSeparatedAuthorities.split(",")))
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
