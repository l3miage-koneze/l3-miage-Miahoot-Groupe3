package fr.uga.l3miage.example.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "correspond au DTO de l'entité Reponse")
public class ReponseDto {
    @Schema(description = "Reponse ID")
    private Long id;
    
    @Schema(description = "Reponse label")
    private String label;
    
    @Schema(description = "Si la reponse est valide")
    private boolean estValide;
}
