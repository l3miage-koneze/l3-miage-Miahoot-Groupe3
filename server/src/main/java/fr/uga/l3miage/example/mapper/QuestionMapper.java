package fr.uga.l3miage.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.QuestionDto;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface QuestionMapper {
    QuestionDto toQuestionDto (QuestionDto questionDto);
    QuestionEntity toQuestionEntity(CreateTestRequest reques);
    void mergeQuestionEntity(@MappingTarget @NonNull QuestionEntity questionEntity, QuestionDto question);
}
