package io.github.orionlibs.documents.api;

import io.github.orionlibs.core.document.json.JSONService;
import io.github.orionlibs.documents.DocumentService;
import io.github.orionlibs.documents.converter.DocumentEntityToDTOConverter;
import io.github.orionlibs.documents.event.DocumentUpdatedEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerUtils.baseAPIPath)
@Validated
@Tag(name = "Documents", description = "Document manager")
public class UpdateDocumentAPIController
{
    @Autowired
    private DocumentService documentService;
    @Autowired
    private DocumentEntityToDTOConverter documentEntityToDTOConverter;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private JSONService jsonService;
    private static final String TOPIC = "document-updated";


    @Operation(
                    summary = "Update document",
                    description = "Update document",
                    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    content = @Content(
                                                    schema = @Schema(implementation = NewDocumentDTO.class)
                                    )
                    ),
                    responses = {@ApiResponse(responseCode = "201", description = "Document updated",
                                    content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = DocumentsDTO.class)
                                    )),
                                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                                    @ApiResponse(responseCode = "404", description = "Document not found")}
    )
    @PutMapping(value = "/documents/{documentID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDocument(@PathVariable(name = "documentID") Integer documentID, @Valid @RequestBody NewDocumentDTO documentToSave)
    {
        boolean isDocumentFound = documentService.update(documentID, documentToSave);
        if(isDocumentFound)
        {
            kafkaTemplate.send(TOPIC, jsonService.toJson(DocumentUpdatedEvent.builder()
                            .documentID(documentID)
                            .build()));
            return ResponseEntity.ok(null);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}