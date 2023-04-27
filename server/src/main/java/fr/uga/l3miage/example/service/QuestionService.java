package fr.uga.l3miage.example.service;

import fr.uga.l3miage.example.component.QuestionComponent;
import fr.uga.l3miage.example.exception.rest.*;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.QuestionMapper;
import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.response.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private static final String ERROR_DETECTED = "Une erreur lors de la création de l'entité TestConfigWithProperties à été détecté.";
    private final QuestionComponent questionComponent;
    private final QuestionMapper questionMapper;


    public QuestionDto getQuestion(final Long miahootId, final Long id)  {
        try {
            return questionMapper.toQuestionDto(questionComponent.getQuestion(miahootId, id));
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(String.format("Aucune question n'a été trouvé pour l'id°[%lu] : impossible de récupérer", id), id);
        }
    }


    public void createQuestion(final Long miahootId, final QuestionDto questionDto){
        QuestionEntity newQuestionEntity = questionMapper.toQuestionEntity(questionDto);
        try {
            questionComponent.createQuestion(miahootId, newQuestionEntity);
        } catch (AlreadyExistException ex) {
            throw new AlreadyUseRestException(ERROR_DETECTED,questionDto.getId(),ex);
        } catch (EntityNotFoundException ex){
            throw new EntityNotFoundRestException(ERROR_DETECTED,questionDto.getId(),ex);
        }
    }


    public void updateQuestion(final Long miahootId, final Long idQuesToModify,final QuestionDto question){
        if (idQuesToModify == question.getId()){
            try {
                questionComponent.updateQuestion(miahootId, idQuesToModify,question);
            } catch (EntityNotFoundException ex) {
                throw new EntityNotFoundRestException(String.format("Aucune question n'a  été trouvé pour l'Id : Impossible de modifier",idQuesToModify),idQuesToModify);
            }
        }else{
            throw new NotTheSameIdRestException(String.format("L'id de la question remplaçante([%lu]) est différent de l'id de la question à remplacer([%lu])", question.getId(), idQuesToModify), question.getId(), idQuesToModify);
        }

    }

    @Transactional
    public void deleteQuestion(final Long miahootId, final Long id){
        try {
            questionComponent.deleteQuestion(miahootId, id);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(String.format("Aucune question n'a été trouvé pour l'id°[%lu] : impossible de supprimer.", id), id);
        }
    }

}
