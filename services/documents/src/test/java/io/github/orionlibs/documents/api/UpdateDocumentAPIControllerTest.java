package io.github.orionlibs.documents.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.orionlibs.documents.DocumentService;
import io.github.orionlibs.documents.model.DocumentModel;
import io.github.orionlibs.documents.model.DocumentType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
})
@ActiveProfiles("test")
class UpdateDocumentAPIControllerTest
{
    @LocalServerPort
    private int port;
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
    }


    @Test
    void updateDocument_notFound()
    {
        RestAssured.baseURI += "/100";
        DocumentRequest doc = updateDocumentRequest("https://company.com/1.pdf");
        Response response = utils.makePutAPICall(doc);
        assertEquals(404, response.statusCode());
    }


    @Test
    void updateDocument()
    {
        DocumentModel doc = utils.saveDocument("https://company.com/1.pdf");
        RestAssured.baseURI += "/" + doc.getId();
        DocumentRequest docToUpdate = updateDocumentRequest("https://company.com/1.pdf");
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


    private DocumentRequest updateDocumentRequest(String docURL)
    {
        return new DocumentRequest(docURL, DocumentType.Type.DOCUMENTATION, "title", "description", LocalDateTime.now(), LocalDateTime.now());
    }
}
