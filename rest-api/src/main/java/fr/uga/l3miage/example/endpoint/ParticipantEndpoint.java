package fr.uga.l3miage.example.endpoint;

import fr.uga.l3miage.example.annotations.Error400Custom;
import fr.uga.l3miage.example.error.TestEntityNotDeletedErrorResponse;
import fr.uga.l3miage.example.error.TestNotFoundErrorResponse;
import fr.uga.l3miage.example.response.ParticipantDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Participant tag")
@CrossOrigin
@RestController
@RequestMapping("/api")
public interface ParticipantEndpoint {

    @Operation(description = "Récupérer le DTO de l'entité Participant qui a pour id celui passé en paramètre")
    @ApiResponse(responseCode = "200", description = "Renvoie le DTO de l'entité Participant demandée",
            content = @Content(schema = @Schema(implementation = ParticipantDto.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/participant/{id}")
    ParticipantDto getParticipant(@PathVariable Long id);

    @Operation(description = "Récupérer les DTO de tous les entités Participants")
    @ApiResponse(responseCode = "200", description = "Renvoie les DTO de tous les entités Participant",
            content = @Content(schema = @Schema(implementation = ParticipantDto.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/participant/all")
    List<ParticipantDto> getAllParticipants();


    @Operation(description = "Création d'une entité Participant")
    @ApiResponse(responseCode = "201", description = "L'entité Participant a bien été créée.")
    @Error400Custom
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/miahoot/id/{miahootId}/participant")
    Long newParticipant(@PathVariable Long miahootId, @Valid @RequestBody ParticipantDto participantDto);



    @Operation(description = "Mise à jour d'une entité Participant")
    @ApiResponse(responseCode = "202", description = "L'entité à bien été mise à jour")
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @Error400Custom
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/participant/{id}")
    void updateParticipant(@PathVariable final Long id,@RequestBody final ParticipantDto participantDto);



    @Operation(description = "Suppression d'une entité Participant en bd")
    @ApiResponse(responseCode = "200", description = "L'entité a bien été supprimée")
    @ApiResponse(responseCode = "418", description = "Renvoie une erreur 418 si l'entité n'a pu être supprimée",
            content = @Content(schema = @Schema(implementation = TestEntityNotDeletedErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/participant/{id}")
    void deleteParticipant(@PathVariable Long id);
}
