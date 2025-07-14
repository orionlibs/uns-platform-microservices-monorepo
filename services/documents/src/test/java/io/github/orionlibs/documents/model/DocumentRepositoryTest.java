package io.github.orionlibs.documents.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class DocumentRepositoryTest
{
    @Autowired
    private DocumentRepository documentRepository;


    @BeforeEach
    void setup()
    {
        documentRepository.deleteAll();
    }


    @Test
    void saveDocumentAndReturnDocument()
    {
        // given
        DocumentEntity doc1 = saveDocument("https://company.com/1.pdf");
        // when
        DocumentEntity savedDoc = documentRepository.save(doc1);
        // then
        assertThat(savedDoc).isNotNull();
        assertThat(savedDoc.getId()).isGreaterThan(0);
        assertThat(savedDoc.getDocumentURL()).isEqualTo("https://company.com/1.pdf");
        assertThat(savedDoc.getCreatedAt().toString()).isNotBlank();
        assertThat(savedDoc.getUpdatedAt().toString()).isNotBlank();
    }


    @Test
    void saveDocumentsAndReturnByType()
    {
        // given
        DocumentEntity doc1 = saveDocument("https://company.com/1.pdf");
        DocumentEntity doc2 = saveDocument("https://company.com/2.pdf");
        // when
        DocumentEntity savedDoc1 = documentRepository.save(doc1);
        DocumentEntity savedDoc2 = documentRepository.save(doc2);
        List<DocumentEntity> docs = documentRepository.findAllByType(DocumentType.DOCUMENTATION);
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


    private DocumentEntity saveDocument(String documentURL)
    {
        DocumentEntity doc = new DocumentEntity(documentURL, DocumentType.DOCUMENTATION, "doc title 1", "doc description 1");
        return documentRepository.save(doc);
    }
}
