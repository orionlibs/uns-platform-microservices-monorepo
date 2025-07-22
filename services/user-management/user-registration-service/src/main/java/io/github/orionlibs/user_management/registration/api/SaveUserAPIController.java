package io.github.orionlibs.user_management.registration.api;

import static org.springframework.http.ResponseEntity.created;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Users", description = "User manager")
public class SaveUserAPIController
{
    /*@Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private UserEntityToDTOConverter userEntityToDTOConverter;


    @Operation(
                    summary = "Save user",
                    description = "Save user",
                    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    content = @Content(
                                                    schema = @Schema(implementation = NewUserDTO.class)
                                    )
                    ),
                    responses = {@ApiResponse(responseCode = "201", description = "User saved",
                                    content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = UsersDTO.class)
                                    )),
                                    @ApiResponse(responseCode = "400", description = "Invalid input")}
    )
    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUser(@Valid @RequestBody NewUserDTO userToSave)
    {
        DocumentModel newDocument = documentService.save(documentToSave);
        return created(null).build();
    }*/
}
