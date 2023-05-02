package fr.uga.l3miage.example.request;

import java.util.Collection;
import java.util.List;

import fr.uga.l3miage.example.response.MiahootDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Correspond à la requête permettant de créer une entité Miahoot")
public class CreateCreatorRequest {
    @Schema(description = "Creator ID")
    private Long id;

    @Schema(description = "Creator nom")
    private String nom;

    @Schema(description = "Creator PP")
    private String photo;

    @Schema(description = "Liste de miahoots")
    private Collection<MiahootDto> miahoots;
}
