package fr.uga.l3miage.example.response;

import java.util.Collection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "correspond au DTO de l'entité Question")
public class QuestionDto {
    @Schema(description = "Question ID")
    private Long id;
    
    @Schema(description = "Question label")
    private String label;

    @Schema(description = "Question miahoot")
    private Long miahootId;

    @Schema(description = "Liste de responses")
    private Collection<ReponseDto> reponses;
}
