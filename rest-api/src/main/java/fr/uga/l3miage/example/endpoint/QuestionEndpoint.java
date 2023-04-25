package fr.uga.l3miage.example.endpoint;

import fr.uga.l3miage.example.annotations.Error400Custom;
import fr.uga.l3miage.example.error.TestEntityNotDeletedErrorResponse;
import fr.uga.l3miage.example.error.TestNotFoundErrorResponse;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.Test;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Question tag")
@CrossOrigin
@RestController
@RequestMapping("question/")
public interface QuestionEndpoint {

    @Operation(description = "Récupérer la phrase 'Hello word'")
    @ApiResponse(responseCode = "200", description = "Si isInError est à false alors 'Hello word' est renvoyé.",
            content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.TEXT_PLAIN_VALUE))
    @ApiResponse(responseCode = "500", description = "Si isInError est à true alors nous avons une erreur 500 qui est renvoyée.",
            content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.TEXT_PLAIN_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("hello")
    ResponseEntity<String> getHelloWord(@Parameter(description = "correspond a si vous voulez avoir une erreur ou non" ,example = "true") @RequestParam boolean isInError);


    @Operation(description = "Récupérer le DTO de l'entité test qui a pour id celui passé en paramètre")
    @ApiResponse(responseCode = "200", description = "Renvoie le DTO de l'entité test demandée",
            content = @Content(schema = @Schema(implementation = Test.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{description}")
    Test getEntityTest(@PathVariable String description);


    @Operation(description = "Création d'une entité Test")
    @ApiResponse(responseCode = "201", description = "L'entité Test a bien été créée.")
    @Error400Custom
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createEntityTest(@Valid @RequestBody CreateTestRequest request);



    @Operation(description = "Mise à jour d'une entité test")
    @ApiResponse(responseCode = "202", description = "L'entité à bien été mis à jour")
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @Error400Custom
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("{lastDescription}")
    void updateTestEntity(@PathVariable final String lastDescription,@RequestBody final Test test);



    @Operation(description = "Suppression d'une entité Test en bd")
    @ApiResponse(responseCode = "200", description = "si isInError est à false alors 'Hello word' est renvoyé")
    @ApiResponse(responseCode = "418", description = "Renvoie une erreur 418 si l'entité n'a pu être supprimée",
            content = @Content(schema = @Schema(implementation = TestEntityNotDeletedErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{description}")
    void deleteTestEntity(@PathVariable String description);
}
