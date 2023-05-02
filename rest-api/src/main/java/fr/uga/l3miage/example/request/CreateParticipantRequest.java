package fr.uga.l3miage.example.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class CreateParticipantRequest {
    @Schema(description = "Participant ID")
    private Long id;
    
    @Schema(description = "Participant nom")
    private String nom;
    
    @Schema(description = "Participant photo")
    private String photo;
}
