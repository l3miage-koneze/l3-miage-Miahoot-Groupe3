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
@Schema(description = "correspond au DTO de l'entité Miahoot")
public class MiahootDto {
    @Schema(description = "Miahoot ID")
    private Long id;
    
    @Schema(description = "Miahoot nom")
    private String nom;
    
    @Schema(description = "Miahoot creator")
    private CreatorDto creatorDto;
    
    @Schema(description = "Liste de questions")
    private Collection<QuestionDto> questions;

}
