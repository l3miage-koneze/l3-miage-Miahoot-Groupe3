package fr.uga.l3miage.example.endpoint;

import fr.uga.l3miage.example.annotations.Error400Custom;
import fr.uga.l3miage.example.error.TestEntityNotDeletedErrorResponse;
import fr.uga.l3miage.example.error.TestNotFoundErrorResponse;
import fr.uga.l3miage.example.response.ReponseDto;
import fr.uga.l3miage.example.response.Test;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@Tag(name = "Reponse tag")
@CrossOrigin
@RestController
@RequestMapping("/api/question/{questionId}")
public interface ReponseEndpoint {

    @Operation(description = "Récupérer le DTO de l'entité Reponse qui a pour id celui passé en paramètre")
    @ApiResponse(responseCode = "200", description = "Renvoie le DTO de l'entité Reponse demandée",
            content = @Content(schema = @Schema(implementation = ReponseDto.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reponse/{id}")
    ReponseDto getReponse(@PathVariable Long id);

    @Operation(description = "Récupérer les DTO de les entités Reponses")
    @ApiResponse(responseCode = "200", description = "Renvoie le DTO de l'entité Reponse",
            content = @Content(schema = @Schema(implementation = ReponseDto.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reponse/all")
    List<ReponseDto> getReponsesByQuestionId(@PathVariable Long questionid);


    @Operation(description = "Création d'une entité Reponse")
    @ApiResponse(responseCode = "201", description = "L'entité Reponse a bien été créée.")
    @Error400Custom
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reponse")
    Long newReponse(@PathVariable final Long questionId, @Valid @RequestBody ReponseDto reponseDto);



    @Operation(description = "Mise à jour d'une entité Reponse")
    @ApiResponse(responseCode = "202", description = "L'entité à bien été mise à jour")
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @Error400Custom
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/reponse/{id}")
    void updateReponse(@PathVariable final Long id,@RequestBody final ReponseDto reponseDto);



    @Operation(description = "Suppression d'une entité Reponse en bd")
    @ApiResponse(responseCode = "200", description = "L'entité a bien été supprimée")
    @ApiResponse(responseCode = "418", description = "Renvoie une erreur 418 si l'entité n'a pu être supprimée",
            content = @Content(schema = @Schema(implementation = TestEntityNotDeletedErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/reponse/{id}")
    void deleteReponse(@PathVariable Long id);
}
