package fr.uga.l3miage.example.endpoint;

import fr.uga.l3miage.example.annotations.Error400Custom;
import fr.uga.l3miage.example.error.TestEntityNotDeletedErrorResponse;
import fr.uga.l3miage.example.error.TestNotFoundErrorResponse;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.QuestionDto;
import fr.uga.l3miage.example.response.ReponseDto;
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
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Tag(name = "Question tag")
@CrossOrigin
@RestController
@RequestMapping("question/")
public interface QuestionEndpoint {

    /*
    @Operation(description = "Récupérer les questions ")
    @ApiResponse(responseCode = "200", description = "recuperation des questions reussis.",
            content = @Content(schema = @Schema(implementation = QuestionDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "500", description = "recuperation non reussis renvoie erreur",
            content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.TEXT_PLAIN_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("question")
    Collection<QuestionDto> getAllQuestions()(@RequestParam QuestionDto questionDto);
*/ // N'est pas gerer par les service
//ok
    @Operation(description = "Récupérer le DTO de l'entité question qui a pour id celui passé en paramètre")
    @ApiResponse(responseCode = "200", description = "Renvoie le DTO de l'entité question demandée",
            content = @Content(schema = @Schema(implementation = Test.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/question/{id}")
    QuestionDto getQuestion(@PathVariable Long id);

//Ok
    @Operation(description = "Création d'une entité Question")
    @ApiResponse(responseCode = "201", description = "L'entité question a bien été créée.")
    @Error400Custom
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/question")
    void newQuestion(@Valid @RequestBody QuestionDto questionDto);



    @Operation(description = "Mise à jour d'une entité question")
    @ApiResponse(responseCode = "202", description = "L'entité à bien été mis à jour")
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @Error400Custom
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("{Questions/{id}")
    void updateQuestion(@PathVariable Long id,@RequestBody @Valid QuestionDto questionDto);



    @Operation(description = "Suppression d'une entité question en bd")
    @ApiResponse(responseCode = "200", description = "si isInError est à false alors la question est renvoyé")
    @ApiResponse(responseCode = "418", description = "Renvoie une erreur 418 si l'entité n'a pu être supprimée",
            content = @Content(schema = @Schema(implementation = TestEntityNotDeletedErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/Questions/{id}")
    void deleteQuestion(@PathVariable Long id);

/*
    @Operation(description = "Récupérer les reponses possibles d'une question ")
    @ApiResponse(responseCode = "200", description = "recuperation des reponses reussis.",
            content = @Content(schema = @Schema(implementation = QuestionDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "500", description = "recuperation non reussis renvoie erreur",
            content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.TEXT_PLAIN_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/Questions/{id}/Reponses")
    Collection<ReponseDto> getAllReponses(@PathVariable("id") @NotNull Long questionId);
*/ // N'est pas gerer par les service

}


