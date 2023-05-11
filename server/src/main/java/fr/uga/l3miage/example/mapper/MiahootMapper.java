package fr.uga.l3miage.example.mapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.response.MiahootDto;
import lombok.NonNull;
import javax.validation.Valid;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface MiahootMapper {
    MiahootDto toMiahootDto(MiahootEntity miahootEntity);
    MiahootEntity toMiahootEntity(@Valid MiahootDto miahootDto);
    void mergeMiahootEntity(@MappingTarget @NonNull MiahootEntity miahootEntity, MiahootDto miahootDto);
    void mergeMiahootEntities(@MappingTarget @NonNull MiahootEntity targetMiahootEntity, MiahootEntity sourceMiahootEntity);
}
