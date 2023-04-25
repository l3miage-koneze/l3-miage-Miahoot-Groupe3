package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.QuestionMapper;
import fr.uga.l3miage.example.models.ReponseEntity;
import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.repository.ReponseRepository;
import fr.uga.l3miage.example.repository.QuestionRepository;
import fr.uga.l3miage.example.response.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuestionComponent {

    private final QuestionRepository questionRepository;
    private final ReponseRepository reponseRepository;
    private final QuestionMapper questionMapper;


    public QuestionEntity getQuestion(final Long id) throws EntityNotFoundException {
        Optional<QuestionEntity> quesOpt = questionRepository.findById(id);
        if (quesOpt.isPresent()){
            return quesOpt.get();
        }
        else{
            throw new EntityNotFoundException(String.format("Aucune question n'a été trouvé pour l'id°[%lu] : impossible de récupérer", id), id);
        }
    }

    public void createQuestion(final QuestionEntity question) throws AlreadyExistException {
        if (questionRepository.findById(question.getId()).isPresent()){
            throw new AlreadyExistException(String.format("La question n°[%lu] existe déjà en BD.", question.getId()), question.getId());
        }
        else{
            questionRepository.save(question);
        }
    }

    public void updateQuestion(final Long idQuesToModify, final QuestionDto question) throws EntityNotFoundException, NotTheSameIdException{
        if (idQuesToModify == question.getId()) {
            Optional<QuestionEntity> quesOpt = questionRepository.findById(idQuesToModify);
            if (quesOpt.isPresent()){
                questionMapper.mergeQuestionEntity(quesOpt.get(), question);
                questionRepository.save(quesOpt.get());
            }
            else{
                throw new EntityNotFoundException(String.format("Aucune question n'a été trouvée pour l'id°[%lu] : impossible de modifier.", idQuesToModify), idQuesToModify);
            }
        } else throw new NotTheSameIdException(String.format("L'id de la question remplaçante([%lu]) est différent de l'id de la question à remplacer([%lu])", question.getId(), idQuesToModify), question.getId(), idQuesToModify);
    }


    public void deleteQuestion(final Long id) throws EntityNotFoundException {
        Optional<QuestionEntity> quesOpt = questionRepository.findById(id);
        if (quesOpt.isPresent()) {
            questionRepository.deleteById(id);
            for (ReponseEntity reponse : quesOpt.get().getReponses()) {
                reponseRepository.deleteById(reponse.getId());
            }
        } else {
            throw new EntityNotFoundException(String.format("Aucune question n'a été trouvé pour l'id°[%lu] : impossible de supprimer.", id), id);
        }
    }

}
