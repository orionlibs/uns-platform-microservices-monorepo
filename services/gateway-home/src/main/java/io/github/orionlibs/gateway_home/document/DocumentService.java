package io.github.orionlibs.gateway_home.document;

import io.github.orionlibs.gateway_home.document.model.DocumentEntity;
import io.github.orionlibs.gateway_home.document.model.DocumentRepository;
import io.github.orionlibs.gateway_home.document.model.DocumentType;
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
}
