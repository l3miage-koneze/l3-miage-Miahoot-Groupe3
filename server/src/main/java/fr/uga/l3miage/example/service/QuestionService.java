package fr.uga.l3miage.example.service;

import fr.uga.l3miage.example.component.QuestionComponent;
import fr.uga.l3miage.example.exception.rest.*;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.QuestionMapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.response.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private static final String ERROR_DETECTED = "Une erreur lors de la création de l'entité TestConfigWithProperties à été détecté.";
    private final QuestionComponent questionComponent;
    private final QuestionMapper questionMapper;


    public QuestionDto getQuestion(final Long id)  {
        try {
            return questionMapper.toQuestionDto(questionComponent.getQuestion(id));
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(String.format("Aucune question n'a été trouvé pour l'id°[%lu] : impossible de récupérer", id), id);
        }
    }


    public Long createQuestion(final Long miahootId, final QuestionDto questionDto){
        QuestionEntity newQuestionEntity = questionMapper.toQuestionEntity(questionDto);
        try {
            return questionComponent.createQuestion(miahootId, newQuestionEntity);
        } catch (AlreadyExistException ex) {
            throw new AlreadyUseRestException(ERROR_DETECTED,questionDto.getId(),ex);
        } catch (EntityNotFoundException ex){
            throw new EntityNotFoundRestException(ERROR_DETECTED,questionDto.getId(),ex);
        }
    }


    public void updateQuestion(final Long idQuesToModify,final QuestionDto question){
        if (idQuesToModify == question.getId()){
            try {
                questionComponent.updateQuestion(idQuesToModify,question);
            } catch (EntityNotFoundException ex) {
                throw new EntityNotFoundRestException(String.format("Aucune question n'a  été trouvé pour l'Id : Impossible de modifier",idQuesToModify),idQuesToModify);
            }
        }else{
            throw new NotTheSameIdRestException(String.format("L'id de la question remplaçante([%lu]) est différent de l'id de la question à remplacer([%lu])", question.getId(), idQuesToModify), question.getId(), idQuesToModify);
        }

    }

    @Transactional
    public void deleteQuestion(final Long id){
        try {
            questionComponent.deleteQuestion( id);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(String.format("Aucune question n'a été trouvé pour l'id°[%lu] : impossible de supprimer.", id), id);
        }
    }


    public List<QuestionDto> getALlQuestions() {
            List<QuestionEntity> questionEntities = questionComponent.getAllQuestions();
            return questionEntities.stream()
                    .map(questionMapper::toQuestionDto)
                    .collect(Collectors.toList());


    }

}
