package fr.uga.l3miage.example.response;

import java.util.Collection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "correspond au DTO de l'entité Miahoot")
public class CreatorDto {
    @Schema(description = "Creator ID")
    private Long id;

    @Schema(description = "Creator nom")
    private String nom;

    @Schema(description = "Creator PP")
    private String photo;

    @Schema(description = "Liste de miahoots")
    private Collection<MiahootDto> miahoots;
}
