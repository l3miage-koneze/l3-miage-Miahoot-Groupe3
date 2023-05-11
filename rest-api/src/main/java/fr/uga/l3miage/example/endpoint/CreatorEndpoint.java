package fr.uga.l3miage.example.endpoint;

import fr.uga.l3miage.example.annotations.Error400Custom;
import fr.uga.l3miage.example.error.TestEntityNotDeletedErrorResponse;
import fr.uga.l3miage.example.error.TestNotFoundErrorResponse;
import fr.uga.l3miage.example.response.CreatorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@Tag(name = "Creator tag")
@CrossOrigin
@RestController
@RequestMapping("/api/creator/")
public interface CreatorEndpoint {

    @Operation(description = "Récupérer le DTO de l'entité Creator qui a pour uid celui passé en paramètre")
    @ApiResponse(responseCode = "200", description = "Renvoie le DTO de l'entité Creator demandée",
            content = @Content(schema = @Schema(implementation = CreatorDto.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{uid}")
    CreatorDto getCreator(@PathVariable String uid);

    @Operation(description = "Création d'une entité Creator")
    @ApiResponse(responseCode = "201", description = "L'entité Creator a bien été créée.")
    @Error400Custom
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    String newCreator(@Valid @RequestBody CreatorDto creatorDto) throws Exception;


    @Operation(description = "Création d'une entité Creator lié au uid")
    @ApiResponse(responseCode = "201", description = "L'entité Creator a bien été créée.")
    @Error400Custom
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/creatorGoogle")
    ResponseEntity<?> createCreatorGoogle(@Valid @RequestBody CreatorDto creatorDto) throws Exception;


    @Operation(description = "Mise à jour d'une entité Creator")
    @ApiResponse(responseCode = "202", description = "L'entité à bien été mise à jour")
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @Error400Custom
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("{uid}")
    void updateCreator(@PathVariable final String uid,@RequestBody final CreatorDto creatorDto);



    @Operation(description = "Suppression d'une entité Creator en bd")
    @ApiResponse(responseCode = "200", description = "L'entité a bien été supprimée")
    @ApiResponse(responseCode = "418", description = "Renvoie une erreur 418 si l'entité n'a pu être supprimée",
            content = @Content(schema = @Schema(implementation = TestEntityNotDeletedErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{uid}")
    void deleteCreator(@PathVariable String uid);

    @Operation(description = "Vérification d'une entité Creator existe")
    @ApiResponse(responseCode = "201", description = "L'entité Creator a bien été existe.")
    @Error400Custom
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/check/{uid}")
    ResponseEntity<Boolean> checkIfCreatorExists(@Valid @PathVariable String uid) throws Exception;
}