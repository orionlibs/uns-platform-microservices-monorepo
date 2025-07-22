package io.github.orionlibs.documents.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.orionlibs.documents.DocumentService;
import io.github.orionlibs.documents.model.DocumentModel;
import io.github.orionlibs.documents.model.DocumentType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.io.File;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
})
@ActiveProfiles("test")
class UpdateDocumentAPIControllerTest
{
    @LocalServerPort
    private int port;
    @MockitoBean
    private ProducerFactory<String, String> producerFactory;
    @MockitoBean
    private Producer<String, String> producer;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private TestUtils utils;


    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp()
    {
        documentService.deleteAll();
        RestAssured.baseURI = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/documents";
        RestAssured.useRelaxedHTTPSValidation();
        System.setProperty(
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
                                            0L,
                                            0,
                                            System.currentTimeMillis(),
                                            record.key() != null ? record.key().length() : 0,
                                            record.value() != null ? record.value().length() : 0
                            );
                            cb.onCompletion(meta, null);
                            return CompletableFuture.completedFuture(meta);
                        });
    }


    @Test
    void updateDocument_notFound()
    {
        RestAssured.baseURI += "/100";
        NewDocumentDTO doc = updateDocumentRequest("https://company.com/1.pdf");
        Response response = utils.makePutAPICall(doc);
        assertEquals(404, response.statusCode());
    }


    @Test
    void updateDocument()
    {
        DocumentModel doc = utils.saveDocument("https://company.com/1.pdf");
        RestAssured.baseURI += "/" + doc.getId();
        NewDocumentDTO docToUpdate = updateDocumentRequest("https://company.com/1.pdf");
        docToUpdate.setDocumentURL("https://company.com/2.pdf");
        docToUpdate.setType(DocumentType.Type.OTHER);
        docToUpdate.setTitle("new title");
        docToUpdate.setDescription("new description");
        Response response = utils.makePutAPICall(docToUpdate);
        assertEquals(200, response.statusCode());
        response = utils.makeGetAPICall();
        assertEquals(200, response.statusCode());
        DocumentDTO body = response.as(DocumentDTO.class);
        assertEquals("https://company.com/2.pdf", body.documentURL());
        assertEquals(DocumentType.Type.OTHER, body.type());
        assertEquals("new title", body.title());
        assertEquals("new description", body.description());
    }


    private NewDocumentDTO updateDocumentRequest(String docURL)
    {
        return new NewDocumentDTO(docURL, DocumentType.Type.DOCUMENTATION, "title", "description", LocalDateTime.now(), LocalDateTime.now());
    }
}
