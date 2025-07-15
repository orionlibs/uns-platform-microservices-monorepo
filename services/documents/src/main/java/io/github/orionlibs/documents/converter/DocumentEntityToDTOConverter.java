package io.github.orionlibs.documents.converter;

import io.github.orionlibs.core.converter.Converter;
import io.github.orionlibs.documents.api.DocumentDTO;
import io.github.orionlibs.documents.model.DocumentModel;
import org.springframework.stereotype.Component;

@Component
public class DocumentEntityToDTOConverter implements Converter<DocumentModel, DocumentDTO>
{
    @Override
    public DocumentDTO convert(DocumentModel objectToConvert)
    {
        if(objectToConvert == null)
        {
            return null;
        }
        return new DocumentDTO(objectToConvert.getDocumentURL(), objectToConvert.getTitle(), objectToConvert.getDescription(),
                        objectToConvert.getCreatedAt(), objectToConvert.getUpdatedAt());
    }
}
