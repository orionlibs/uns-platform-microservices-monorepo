package io.github.orionlibs.gateway_home.document.converter;

import io.github.orionlibs.gateway_home.api.DocumentDTO;
import io.github.orionlibs.gateway_home.document.model.DocumentEntity;
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
