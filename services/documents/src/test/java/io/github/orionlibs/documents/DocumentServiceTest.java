package io.github.orionlibs.documents;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.documents.model.DocumentModel;
import io.github.orionlibs.documents.model.DocumentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class DocumentServiceTest
{
    @Autowired
    private DocumentService documentService;


    @BeforeEach
    void setup()
    {
        documentService.deleteAll();
    }


    @Test
    void saveDocumentAndReturnDocument()
    {
        // given
        DocumentModel doc1 = saveDocument("https://company.com/1.pdf");
        // when
        List<DocumentModel> savedDoc = documentService.getDocumentsByType(DocumentType.Type.DOCUMENTATION);
        // then
        assertThat(savedDoc).isNotNull();
        assertThat(savedDoc.size()).isEqualTo(1);
        assertThat(savedDoc.get(0).getId()).isGreaterThan(0);
        assertThat(savedDoc.get(0).getDocumentURL()).isEqualTo("https://company.com/1.pdf");
        assertThat(savedDoc.get(0).getCreatedAt().toString()).isNotBlank();
        assertThat(savedDoc.get(0).getUpdatedAt().toString()).isNotBlank();
    }


    @Test
    void saveDocumentsAndReturnByType()
    {
        // given
        DocumentModel doc1 = saveDocument("https://company.com/1.pdf");
        DocumentModel doc2 = saveDocument("https://company.com/2.pdf");
        // when
        List<DocumentModel> docs = documentService.getDocumentsByType(DocumentType.Type.DOCUMENTATION);
        // then
        assertThat(docs).isNotNull();
        assertThat(docs.size()).isEqualTo(2);
        assertThat(docs.get(0).getId()).isGreaterThan(0);
        assertThat(docs.get(1).getId()).isGreaterThan(0);
        assertThat(docs.get(0).getDocumentURL()).isEqualTo("https://company.com/1.pdf");
        assertThat(docs.get(1).getDocumentURL()).isEqualTo("https://company.com/2.pdf");
        assertThat(docs.get(0).getCreatedAt().toString()).isNotBlank();
        assertThat(docs.get(0).getUpdatedAt().toString()).isNotBlank();
        assertThat(docs.get(1).getCreatedAt().toString()).isNotBlank();
        assertThat(docs.get(1).getUpdatedAt().toString()).isNotBlank();
    }


    private DocumentModel saveDocument(String documentURL)
    {
        DocumentModel doc = new DocumentModel(documentURL, DocumentType.Type.DOCUMENTATION, "doc title 1", "doc description 1");
        return documentService.save(doc);
    }
}
