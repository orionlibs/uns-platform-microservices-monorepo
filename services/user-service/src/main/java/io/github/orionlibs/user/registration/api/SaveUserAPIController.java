package io.github.orionlibs.user.registration.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
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
