package io.github.orionlibs.documents.api;

import io.github.orionlibs.core.document.json.JSONService;
import io.github.orionlibs.documents.DocumentService;
import io.github.orionlibs.documents.event.DocumentDeletedEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerUtils.baseAPIPath)
@Tag(name = "Documents", description = "Document manager")
public class DeleteDocumentAPIController
{
    @Autowired
    private DocumentService documentService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private JSONService jsonService;
    private static final String TOPIC = "document-deleted";


    @Operation(
                    summary = "Delete document by ID",
                    description = "Delete document by ID",
                    parameters = @io.swagger.v3.oas.annotations.Parameter(
                                    name = "documentID",
                                    description = "The ID of the document to delete",
                                    required = true,
                                    in = ParameterIn.PATH,
                                    schema = @Schema(type = "integer", format = "int32")
                    ),
                    responses = {@ApiResponse(responseCode = "200", description = "Document deleted")}
    )
    @DeleteMapping(value = "/documents/{documentID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteDocumentByID(@PathVariable(name = "documentID") Integer documentID)
    {
        documentService.delete(documentID);
        if(kafkaTemplate != null)
        {
            kafkaTemplate.send(TOPIC, jsonService.toJson(DocumentDeletedEvent.builder()
                            .documentID(documentID)
                            .build()));
        }
        return ResponseEntity.ok(null);
    }
}