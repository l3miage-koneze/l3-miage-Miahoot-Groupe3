package fr.uga.l3miage.example.mapper;

import javax.validation.Valid;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.request.CreateQuestionRequest;
import fr.uga.l3miage.example.response.QuestionDto;
import fr.uga.l3miage.example.response.ReponseDto;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface QuestionMapper {
    QuestionDto toQuestionDto (QuestionEntity updated);
    QuestionEntity toQuestionEntity(@Valid QuestionDto questionDto);
    void mergeQuestionEntity(@MappingTarget @NonNull QuestionEntity questionEntity, QuestionDto question);
}
