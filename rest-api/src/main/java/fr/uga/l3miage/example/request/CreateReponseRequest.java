package fr.uga.l3miage.example.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Correspond à la requête permettant de créer une entité Reponse")
public class CreateReponseRequest {
    @Schema(description = "Reponse ID")
    private Long id;
    
    @Schema(description = "Reponse label")
    private String label;
    
    @Schema(description = "Si la reponse est valide")
    private boolean estValide;
}
