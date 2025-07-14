package io.github.orionlibs.documents;

import io.github.orionlibs.documents.model.DocumentEntity;
import io.github.orionlibs.documents.model.DocumentRepository;
import io.github.orionlibs.documents.model.DocumentType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService
{
    @Autowired
    private DocumentRepository documentRepository;


    public List<DocumentEntity> getDocumentsByType(DocumentType documentType)
    {
        return documentRepository.findAllByType(documentType);
    }


    public DocumentEntity save(DocumentEntity document)
    {
        return documentRepository.save(document);
    }


    public void deleteAll()
    {
        documentRepository.deleteAll();
    }
}
