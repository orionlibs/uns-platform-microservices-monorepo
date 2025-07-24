package io.github.orionlibs.documents;

import io.github.orionlibs.documents.api.SaveDocumentRequest;
import io.github.orionlibs.documents.api.UpdateDocumentRequest;
import io.github.orionlibs.documents.converter.NewDocumentDTOToEntityConverter;
import io.github.orionlibs.documents.model.DocumentDAO;
import io.github.orionlibs.documents.model.DocumentModel;
import io.github.orionlibs.documents.model.DocumentType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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


    public List<DocumentModel> getByType(DocumentType.Type documentType)
    {
        return documentRepository.findAllByType(documentType);
    }


    public Optional<DocumentModel> getByID(Integer documentID)
    {
        return documentRepository.findById(documentID);
    }


    public DocumentModel save(SaveDocumentRequest newDocument)
    {
        newDocument.setCreatedAt(LocalDateTime.now());
        newDocument.setUpdatedAt(LocalDateTime.now());
        DocumentModel toSave = newDocumentDTOToEntityConverter.convert(newDocument);
        return save(toSave);
    }


    @Transactional
    public DocumentModel save(DocumentModel toSave)
    {
        toSave = documentRepository.save(toSave);
        log.info("Saved document");
        return toSave;
    }


    @Transactional
    public boolean update(Integer documentID, UpdateDocumentRequest document)
    {
        Optional<DocumentModel> docTemp = getByID(documentID);
        if(docTemp.isPresent())
        {
            DocumentModel doc = docTemp.get();
            doc.setDocumentURL(document.getDocumentURL());
            doc.setType(document.getType());
            doc.setTitle(document.getTitle());
            doc.setDescription(document.getDescription());
            doc.setUpdatedAt(LocalDateTime.now());
            save(doc);
            log.info("Updated document");
            return true;
        }
        else
        {
            return false;
        }
    }


    public void delete(Integer documentID)
    {
        documentRepository.deleteById(documentID);
        log.info("Deleted document");
    }


    public void deleteAll()
    {
        documentRepository.deleteAll();
        log.info("Deleted all documents");
    }
}
