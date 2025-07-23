package io.github.orionlibs.documents.api;

import io.github.orionlibs.documents.DocumentService;
import io.github.orionlibs.documents.model.DocumentModel;
import io.github.orionlibs.documents.model.DocumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class TestUtils
{
    @Autowired
    private DocumentService documentService;


    DocumentModel saveDocument(String documentURL)
    {
        DocumentModel doc = new DocumentModel(documentURL, DocumentType.Type.DOCUMENTATION, "document title 1", "document description 1");
        return documentService.save(doc);
    }
}
