package fr.uga.l3miage.example.endpoint;

import fr.uga.l3miage.example.annotations.Error400Custom;
import fr.uga.l3miage.example.error.TestEntityNotDeletedErrorResponse;
import fr.uga.l3miage.example.error.TestNotFoundErrorResponse;
import fr.uga.l3miage.example.response.QuestionDto;
import fr.uga.l3miage.example.response.Test;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Question tag")
@CrossOrigin
@RestController
@RequestMapping("/miahoot/")
public interface QuestionEndpoint {

    @Operation(description = "Récupérer le DTO de l'entité Question qui a pour id celui passé en paramètre")
    @ApiResponse(responseCode = "200", description = "Renvoie le DTO de l'entité Question demandée",
            content = @Content(schema = @Schema(implementation = QuestionDto.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{miahootId}/question/{id}")
    QuestionDto getQuestion(@PathVariable Long miahootId, @PathVariable Long id);


    @Operation(description = "Création d'une entité Miahoot")
    @ApiResponse(responseCode = "201", description = "L'entité Miahoot a bien été créée.")
    @Error400Custom
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{miahootId}/question")
    void newQuestion(@PathVariable Long miahootId, @Valid @RequestBody QuestionDto questionDto);



    @Operation(description = "Mise à jour d'une entité Question")
    @ApiResponse(responseCode = "202", description = "L'entité à bien été mise à jour")
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @Error400Custom
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("{miahootId}/question/{id}")
    void updateQuestion(@PathVariable Long miahootId, @PathVariable final Long id,@RequestBody final QuestionDto questionDto);



    @Operation(description = "Suppression d'une entité Question en bd")
    @ApiResponse(responseCode = "200", description = "L'entité a bien été supprimée")
    @ApiResponse(responseCode = "418", description = "Renvoie une erreur 418 si l'entité n'a pu être supprimée",
            content = @Content(schema = @Schema(implementation = TestEntityNotDeletedErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{miahootId}/question/{id}")
    void deleteQuestion(@PathVariable Long miahootId, @PathVariable Long id);
}
