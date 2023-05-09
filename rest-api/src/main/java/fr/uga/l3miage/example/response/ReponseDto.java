package fr.uga.l3miage.example.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "correspond au DTO de l'entité Reponse")
public class ReponseDto {
    @Schema(description = "Reponse ID")
    private Long id;
    
    @Schema(description = "Reponse label")
    private String label;
    
    @Schema(description = "Si la reponse est valide")
    private boolean estValide;

    @Schema(description = "Reponse QuestionId")
    private Long questionId;
}
