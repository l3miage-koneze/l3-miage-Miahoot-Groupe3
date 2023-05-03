package fr.uga.l3miage.example.mapper;
import fr.uga.l3miage.example.models.CreatorEntity;
import fr.uga.l3miage.example.response.CreatorDto;
import lombok.NonNull;

import javax.validation.Valid;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper
public interface CreatorMapper {
    CreatorDto toCreatorDto(CreatorEntity creatorEntity);
    @Mapping(target = "uid", source = "creatorDto.uid")
    CreatorEntity toCreatorEntity(@Valid CreatorDto creatorDto);
    void mergeCreatorEntity(@MappingTarget @NonNull CreatorEntity creatorEntity, CreatorDto creatorDto);
}
