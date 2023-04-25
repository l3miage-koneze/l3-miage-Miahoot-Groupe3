package fr.uga.l3miage.example.request;

import java.util.List;

import fr.uga.l3miage.example.response.QuestionDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Correspond à la requête permettant de créer une entité Miahoot")
public class CreateMiahootRequest {
    @Schema(description = "Miahoot ID")
    private Long id;
    
    @Schema(description = "Miahoot nom")
    private String nom;
    
    @Schema(description = "Liste de questions")
    private List<QuestionDto> questions;
}
