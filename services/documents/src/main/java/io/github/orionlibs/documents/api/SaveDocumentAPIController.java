package io.github.orionlibs.documents.api;

import static org.springframework.http.ResponseEntity.created;

import io.github.orionlibs.documents.DocumentService;
import io.github.orionlibs.documents.converter.DocumentEntityToDTOConverter;
import io.github.orionlibs.documents.model.DocumentModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerUtils.baseAPIPath)
@Validated
@Tag(name = "Documents", description = "Document manager")
public class SaveDocumentAPIController
{
    @Autowired
    private DocumentService documentService;
    @Autowired
    private DocumentEntityToDTOConverter documentEntityToDTOConverter;


    @Operation(
                    summary = "Save document",
                    description = "Save document",
                    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    content = @Content(
                                                    schema = @Schema(implementation = NewDocumentDTO.class)
                                    )
                    ),
                    responses = {@ApiResponse(responseCode = "201", description = "Document saved",
                                    content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = DocumentsDTO.class)
                                    )),
                                    @ApiResponse(responseCode = "400", description = "Invalid input")}
    )
    @PostMapping(value = "/documents", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveDocument(@Valid @RequestBody NewDocumentDTO documentToSave)
    {
        documentToSave.setCreatedAt(LocalDateTime.now());
        documentToSave.setUpdatedAt(LocalDateTime.now());
        DocumentModel newDocument = documentService.save(documentToSave);
        return created(URI.create(ControllerUtils.baseAPIPath + "/documents/" + newDocument.getId())).build();
    }
}