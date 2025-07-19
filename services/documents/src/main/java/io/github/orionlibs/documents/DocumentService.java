package io.github.orionlibs.documents;

import io.github.orionlibs.documents.api.NewDocumentDTO;
import io.github.orionlibs.documents.converter.NewDocumentDTOToEntityConverter;
import io.github.orionlibs.documents.model.DocumentDAO;
import io.github.orionlibs.documents.model.DocumentModel;
import io.github.orionlibs.documents.model.DocumentType;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DocumentService
{
    @Autowired
    private DocumentDAO documentRepository;
    @Autowired
    private NewDocumentDTOToEntityConverter newDocumentDTOToEntityConverter;


    public List<DocumentModel> getDocumentsByType(DocumentType.Type documentType)
    {
        return documentRepository.findAllByType(documentType);
    }


    @Transactional
    public DocumentModel save(NewDocumentDTO newDocument)
    {
        DocumentModel toSave = newDocumentDTOToEntityConverter.convert(newDocument);
        return save(toSave);
    }


    @Transactional
    public DocumentModel save(DocumentModel toSave)
    {
        toSave = documentRepository.save(toSave);
        log.info("Saved new document");
        return toSave;
    }


    public void deleteAll()
    {
        documentRepository.deleteAll();
    }
}
