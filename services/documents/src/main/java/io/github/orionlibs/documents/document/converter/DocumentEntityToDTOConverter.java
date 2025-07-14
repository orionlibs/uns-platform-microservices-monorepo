package io.github.orionlibs.documents.document.converter;

import io.github.orionlibs.documents.api.DocumentDTO;
import io.github.orionlibs.documents.document.model.DocumentEntity;
import org.springframework.stereotype.Component;

@Component
public class DocumentEntityToDTOConverter implements Converter<DocumentEntity, DocumentDTO>
{
    @Override
    public DocumentDTO convert(DocumentEntity objectToConvert)
    {
        if(objectToConvert == null)
        {
            return null;
        }
        return new DocumentDTO(objectToConvert.getDocumentURL(), objectToConvert.getTitle(), objectToConvert.getDescription(),
                        objectToConvert.getCreatedAt(), objectToConvert.getUpdatedAt());
    }
}
