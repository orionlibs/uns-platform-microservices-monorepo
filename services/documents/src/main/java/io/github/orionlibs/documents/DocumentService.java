package io.github.orionlibs.documents;

import io.github.orionlibs.documents.model.DocumentModel;
import io.github.orionlibs.documents.model.DocumentDAO;
import io.github.orionlibs.documents.model.DocumentType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService
{
    @Autowired
    private DocumentDAO documentRepository;


    public List<DocumentModel> getDocumentsByType(DocumentType documentType)
    {
        return documentRepository.findAllByType(documentType);
    }


    public DocumentModel save(DocumentModel document)
    {
        return documentRepository.save(document);
    }


    public void deleteAll()
    {
        documentRepository.deleteAll();
    }
}
