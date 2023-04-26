package fr.uga.l3miage.example.service;

import fr.uga.l3miage.example.component.ExampleComponent;
import fr.uga.l3miage.example.component.QuestionComponent;
import fr.uga.l3miage.example.component.ReponseComponent;
import fr.uga.l3miage.example.exception.rest.*;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.QuestionMapper;
import fr.uga.l3miage.example.mapper.TestMapper;
import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.models.TestEntity;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.QuestionDto;
import fr.uga.l3miage.example.response.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
            throw new TestEntityNotFoundRestException(String.format("Aucune question n'a été trouvé pour l'id°[%lu] : impossible de récupérer", id), "id");
        }
    }


    public void createQuestion(final QuestionDto questionDto){
        QuestionEntity newQuestionEntity = questionMapper.toQuestionEntity(questionDto);
        try {
            questionComponent.createQuestion(newQuestionEntity);
        } catch (AlreadyExistException ex) {
            throw new AlreadyUseRestException(ERROR_DETECTED,questionDto.getId(),ex);
        }
    }


    public void updateQuestion(final Long idQuesToModify,final QuestionDto question){
        if (idQuesToModify == question.getId()){
            try {
                questionComponent.updateQuestion(idQuesToModify,question);
            } catch (EntityNotFoundException ex) {
                throw new TestEntityNotFoundRestException(String.format("Aucune question n'a  été trouvé pour l'Id : Impossible de modifier",idQuesToModify),"idQuesToModify");
            }
        }//else{
           // throw new NotTheSameIdException(String.format("L'id de la question remplaçante([%lu]) est différent de l'id de la question à remplacer([%lu])", question.getId(), idQuesToModify), question.getId(), idQuesToModify);
        //}

    }

    @Transactional
    public void deleteQuestion(final Long id){
        try {
            questionComponent.deleteQuestion(id);
        } catch (EntityNotFoundException ex) {
            throw new TestEntityNotFoundRestException(String.format("Aucune question n'a été trouvé pour l'id°[%lu] : impossible de supprimer.", id), "id");
        }
    }

}
