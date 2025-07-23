package io.github.orionlibs.documents.converter;

import io.github.orionlibs.core.converter.Converter;
import io.github.orionlibs.documents.api.DocumentRequest;
import io.github.orionlibs.documents.model.DocumentModel;
import org.springframework.stereotype.Component;

@Component
public class NewDocumentDTOToEntityConverter implements Converter<DocumentRequest, DocumentModel>
{
    @Override
    public DocumentModel convert(DocumentRequest objectToConvert)
    {
        if(objectToConvert == null)
        {
            return null;
        }
        DocumentModel model = new DocumentModel();
        model.setDocumentURL(objectToConvert.getDocumentURL());
        model.setType(objectToConvert.getType());
        model.setTitle(objectToConvert.getTitle());
        model.setDescription(objectToConvert.getDescription());
        model.setCreatedAt(objectToConvert.getCreatedAt());
        model.setUpdatedAt(objectToConvert.getUpdatedAt());
        return model;
    }
}
