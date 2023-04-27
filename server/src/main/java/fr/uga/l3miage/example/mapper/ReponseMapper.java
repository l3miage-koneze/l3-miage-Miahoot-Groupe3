package fr.uga.l3miage.example.mapper;
import javax.validation.Valid;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import fr.uga.l3miage.example.models.ReponseEntity;
import fr.uga.l3miage.example.request.CreateReponseRequest;
import fr.uga.l3miage.example.response.ReponseDto;
import lombok.NonNull;

@Mapper
public interface ReponseMapper {
    ReponseDto toReponseDto(ReponseEntity updated);
    ReponseEntity toReponseEntity(@Valid ReponseDto reponseDto);
    void mergeReponseEntity(@MappingTarget @NonNull ReponseEntity reponseEntity, ReponseDto reponseDto);
}
