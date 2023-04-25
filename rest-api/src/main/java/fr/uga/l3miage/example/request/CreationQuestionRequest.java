package fr.uga.l3miage.example.request;

import java.util.List;

import fr.uga.l3miage.example.response.ReponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Correspond à la requête permettant de créer une entité Question")
public class CreationQuestionRequest {
    @Schema(description = "Question ID")
    private Long id;
    
    @Schema(description = "Question label")
    private String label;
    
    @Schema(description = "Liste de responses")
    private List<ReponseDto> reponses;
}

