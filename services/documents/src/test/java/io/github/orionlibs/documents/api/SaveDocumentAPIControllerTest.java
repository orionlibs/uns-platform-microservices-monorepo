package io.github.orionlibs.documents.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.orionlibs.core.api.APIError;
import io.github.orionlibs.core.api.HTTPHeader;
import io.github.orionlibs.core.api.HTTPHeaderValue;
import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.documents.ControllerUtils;
import io.github.orionlibs.documents.DocumentService;
import io.github.orionlibs.documents.model.DocumentType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
})
@ActiveProfiles("test")
class SaveDocumentAPIControllerTest
{
    @LocalServerPort
    private int port;
    /*@MockitoBean
    private ProducerFactory<String, String> producerFactory;
    @MockitoBean
    private Producer<String, String> producer;*/
    @Autowired
    private DocumentService documentService;
    @Autowired
    private TestUtils utils;
    @Autowired
    private APITestUtils apiUtils;
    private String basePath;
    private String jwtToken;
    private HttpHeaders headers;


    @BeforeEach
    public void setUp()
    {
        documentService.deleteAll();
        //utils.saveUser(port, ControllerUtils.baseAPIPath + "/users", "me@email.com", "bunkzh3Z!", "USER,DOCUMENT_MANAGER");
        //jwtToken = utils.loginUserAndGetJWT(port, ControllerUtils.baseAPIPath + "/users/login", "me@email.com", "bunkzh3Z!");
        headers = new HttpHeaders();
        //headers.add(HTTPHeader.Authorization.get(), HTTPHeaderValue.Bearer.get() + jwtToken);
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/documents";
        /*System.setProperty(
                        "java.security.auth.login.config",
                        new File("src/test/resources/jaas.conf").getAbsolutePath()
        );
        producer = mock(org.apache.kafka.clients.producer.Producer.class);
        OngoingStubbing<Producer<String, String>> stub = when(producerFactory.createProducer());
        stub.thenReturn(producer);
        when(producerFactory.createProducer()).thenReturn(producer);
        when(producer.send(any(ProducerRecord.class), any(Callback.class)))
                        .thenAnswer(invocation -> {
                            ProducerRecord<String, String> record = invocation.getArgument(0);
                            Callback cb = invocation.getArgument(1);
                            RecordMetadata meta = new RecordMetadata(
                                            new TopicPartition(record.topic(), record.partition() != null ? record.partition() : 0),
                                            0L,                                            0,
                                            System.currentTimeMillis(),
                                            record.key() != null ? record.key().length() : 0,
                                            record.value() != null ? record.value().length() : 0
                            );
                            cb.onCompletion(meta, null);
                            return CompletableFuture.completedFuture(meta);
                        });*/
    }


    @Test
    void saveDocument()
    {
        RestAssured.baseURI = basePath;
        SaveDocumentRequest docToSave = saveDocumentRequest("https://company.com/1.pdf");
        Response response = apiUtils.makePostAPICall(docToSave, headers);
        assertEquals(201, response.statusCode());
        assertTrue(response.header("Location").startsWith(ControllerUtils.baseAPIPath + "/documents"));
    }


    @Test
    void saveDocument_invalidDocumentType()
    {
        RestAssured.baseURI = basePath;
        SaveDocumentRequest docToSave = saveDocumentRequestWithoutType("https://company.com/1.pdf");
        Response response = apiUtils.makePostAPICall(docToSave, headers);
        assertEquals(400, response.statusCode());
        APIError body = response.as(APIError.class);
        assertEquals("type must not be blank", body.fieldErrors().get(0).message());
    }


    private SaveDocumentRequest saveDocumentRequest(String docURL)
    {
        return new SaveDocumentRequest(docURL, DocumentType.Type.DOCUMENTATION, "title", "description", LocalDateTime.now(), LocalDateTime.now());
    }


    private SaveDocumentRequest saveDocumentRequestWithoutType(String docURL)
    {
        return new SaveDocumentRequest(docURL, null, "title", "description", LocalDateTime.now(), LocalDateTime.now());
    }
}
