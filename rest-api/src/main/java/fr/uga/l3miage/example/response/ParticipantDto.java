package fr.uga.l3miage.example.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "correspond au DTO de l'entité Participant")
public class ParticipantDto {
    @Schema(description = "Participant ID")
    private Long id;
    
    @Schema(description = "Participant nom")
    private String nom;
    
    @Schema(description = "Participant photo")
    private String photo;
}
