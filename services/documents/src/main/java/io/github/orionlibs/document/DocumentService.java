package io.github.orionlibs.document;

import io.github.orionlibs.core.Logger;
import io.github.orionlibs.core.event.Publishable;
import io.github.orionlibs.document.api.SaveDocumentRequest;
import io.github.orionlibs.document.api.UpdateDocumentRequest;
import io.github.orionlibs.document.converter.NewDocumentDTOToEntityConverter;
import io.github.orionlibs.document.event.EventDocumentSaved;
import io.github.orionlibs.document.model.DocumentDAO;
import io.github.orionlibs.document.model.DocumentModel;
import io.github.orionlibs.document.model.DocumentType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentService implements Publishable
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
        DocumentModel saved = save(toSave);
        publish(EventDocumentSaved.EVENT_NAME, EventDocumentSaved.builder()
                        .documentID(saved.getId())
                        .documentLocation(saved.getDocumentURL())
                        .build());
        return saved;
    }


    @Transactional
    public DocumentModel save(DocumentModel toSave)
    {
        toSave = documentRepository.save(toSave);
        Logger.info("Saved document");
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
            Logger.info("Updated document");
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
        Logger.info("Deleted document");
    }


    public void deleteAll()
    {
        documentRepository.deleteAll();
        Logger.info("Deleted all documents");
    }
}
