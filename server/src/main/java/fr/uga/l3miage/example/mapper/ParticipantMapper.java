package fr.uga.l3miage.example.mapper;
import javax.validation.Valid;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import fr.uga.l3miage.example.models.ParticipantEntity;
import fr.uga.l3miage.example.request.CreateParticipantRequest;
import fr.uga.l3miage.example.response.ParticipantDto;
import lombok.NonNull;

@Mapper
public interface ParticipantMapper {
    ParticipantDto toParticipantDto(ParticipantEntity updated);
    ParticipantEntity toParticipantEntity(@Valid ParticipantDto participantDto);
    void mergeParticipantEntity(@MappingTarget @NonNull ParticipantEntity participantEntity, ParticipantDto participantDto);
}
