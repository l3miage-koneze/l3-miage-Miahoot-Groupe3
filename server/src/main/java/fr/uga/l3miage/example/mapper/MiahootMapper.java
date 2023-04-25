package fr.uga.l3miage.example.mapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.MiahootDto;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface MiahootMapper {
    MiahootDto toMiahootDto(MiahootEntity miahootEntity);
    MiahootEntity toMiahootEntity(CreateTestRequest request);
    void mergeTestEntity(@MappingTarget @NonNull MiahootEntity miahootEntity, MiahootDto miahootDto);
}
