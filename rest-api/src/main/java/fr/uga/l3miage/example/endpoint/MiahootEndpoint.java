package fr.uga.l3miage.example.endpoint;

import fr.uga.l3miage.example.annotations.Error400Custom;
import fr.uga.l3miage.example.error.TestEntityNotDeletedErrorResponse;
import fr.uga.l3miage.example.error.TestNotFoundErrorResponse;
import fr.uga.l3miage.example.response.MiahootDto;
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

@Tag(name = "Miahoot tag")
@CrossOrigin
@RestController
@RequestMapping("/api/miahoot/")
public interface MiahootEndpoint {

    @Operation(description = "Récupérer le DTO de l'entité Miahoot qui a pour id celui passé en paramètre")
    @ApiResponse(responseCode = "200", description = "Renvoie le DTO de l'entité Miahoot demandée",
            content = @Content(schema = @Schema(implementation = MiahootDto.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("id/{id}")
    MiahootDto getMiahoot(@PathVariable Long id);


    @Operation(description = "Récupérer les DTO de tous les entités Miahoot")
    @ApiResponse(responseCode = "200", description = "Renvoie les DTO de tous les entités Miahoot",
            content = @Content(schema = @Schema(implementation = MiahootDto.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("all")
    List<MiahootDto> getAllMiahoots();
      


    @Operation(description = "Récupérer les DTO de tous les entités Miahoot qui a pour nom celui passé en paramètre")
    @ApiResponse(responseCode = "200", description = "Renvoie les DTO de tous les entités Miahoot demandée",
            content = @Content(schema = @Schema(implementation = MiahootDto.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("nom/{nom}")
    List<MiahootDto> findByName(@PathVariable String nom);

    @Operation(description = "Création d'une entité Miahoot")
    @ApiResponse(responseCode = "201", description = "L'entité Miahoot a bien été créée.")
    @Error400Custom
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void newMiahoot(@Valid @RequestBody MiahootDto miahootDto) throws Exception;



    @Operation(description = "Mise à jour d'une entité Miahoot")
    @ApiResponse(responseCode = "202", description = "L'entité à bien été mise à jour")
    @ApiResponse(responseCode = "404", description = "Renvoie une erreur 404 si l'entité n'est pas trouvée",
            content = @Content(schema = @Schema(implementation = TestNotFoundErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @Error400Custom
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("{id}")
    void updateMiahoot(@PathVariable final Long id,@RequestBody final MiahootDto miahootDto);



    @Operation(description = "Suppression d'une entité Miahoot en bd")
    @ApiResponse(responseCode = "200", description = "L'entité a bien été supprimée")
    @ApiResponse(responseCode = "418", description = "Renvoie une erreur 418 si l'entité n'a pu être supprimée",
            content = @Content(schema = @Schema(implementation = TestEntityNotDeletedErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    void deleteMiahoot(@PathVariable Long id);
}
