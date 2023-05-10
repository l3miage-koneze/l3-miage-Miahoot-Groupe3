package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.MiahootMapper;
import fr.uga.l3miage.example.mapper.QuestionMapper;
import fr.uga.l3miage.example.mapper.ReponseMapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.models.ReponseEntity;
import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.repository.MiahootRepository;
import fr.uga.l3miage.example.repository.ReponseRepository;
import fr.uga.l3miage.example.repository.QuestionRepository;
import fr.uga.l3miage.example.response.QuestionDto;
import fr.uga.l3miage.example.response.ReponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;


import java.util.*;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuestionComponent {

    private final QuestionRepository questionRepository;
    private final MiahootRepository miahootRepository;
    private final ReponseRepository reponseRepository;
    private final QuestionMapper questionMapper;
    private final ReponseMapper reponseMapper;
    private final MiahootMapper miahootMapper;

    public QuestionEntity getQuestion(final Long id) throws EntityNotFoundException {
        Optional<QuestionEntity> quesOpt = questionRepository.findById(id);
        if (quesOpt.isPresent()) {
            return quesOpt.get();
        } else {
            throw new EntityNotFoundException(String.format("Aucune question n'a été trouvé pour l'id°[%d] : impossible de récupérer", id), id);
        }

    }
/* 
    public List<QuestionEntity> getAllQuestions(){
            return questionRepository.findAll();
    }
    */
/* 
    public Long createQuestion(final Long miahootId, final QuestionEntity question) throws AlreadyExistException, EntityNotFoundException{
        if (miahootRepository.findById(miahootId).isPresent()) {
            if (question.getId() == null){
                miahootRepository.findById(miahootId).get().getQuestions().add(question);
                return questionRepository.save(question).getId();
            }
            else{
                if (questionRepository.findById(question.getId()).isPresent()){
                    throw new AlreadyExistException(String.format("La question n°[%d] existe déjà en BD.", question.getId()), question.getId());
                }
                else{
                    return questionRepository.save(question).getId();
                }
            }
        }
        else{
            throw new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de récupérer", miahootId), miahootId);
        }
    }
*/
public Long createQuestion(final Long miahootId, final QuestionEntity question) throws AlreadyExistException, EntityNotFoundException {
    Optional<MiahootEntity> miahootOpt = miahootRepository.findById(miahootId);

    if (miahootOpt.isPresent()) {
        MiahootEntity miahoot = miahootOpt.get();
        question.setMiahoot(miahoot);
        questionMapper.toQuestionDto(question).setMiahootId(miahootId);
        miahootRepository.findById(miahootId).get().getQuestions().add(question);
        QuestionEntity savedQuestion = questionRepository.save(question);
        return savedQuestion.getId();
    } else {
        throw new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de créer la question", miahootId), miahootId);
    }
}
public void updateQuestion(final Long idQuestionToModify, final QuestionDto questionDto) throws EntityNotFoundException {
    if (idQuestionToModify.equals(questionDto.getId())) {
        Optional<QuestionEntity> questionOpt = questionRepository.findById(idQuestionToModify);
        if (questionOpt.isPresent()) {
            QuestionEntity existingQuestionEntity = questionOpt.get();
            questionMapper.mergeQuestionEntity(questionOpt.get(), questionDto);
            // récupérer tous les réponses de ce question
            Collection<ReponseEntity> existingReponses = existingQuestionEntity.getReponses();

            //récupérer l'id Miahoot de ce question
            Long miahootId = questionDto.getMiahootId();
            MiahootEntity miahootEntity = miahootRepository.findById(miahootId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de modifier.", miahootId), miahootId));
                questionRepository.save(questionOpt.get());
            // mettre à jour les reponses
            for (ReponseDto reponseDto : questionDto.getReponses()) {
                ReponseEntity reponseEntity = reponseMapper.toReponseEntity(reponseDto);

                // Check if the question already exists in the existingQuestions list
                Optional<ReponseEntity> existingReponseOpt = existingReponses.stream()
                        .filter(q -> q.getId().equals(reponseDto.getId()))
                        .findFirst();

                if (existingReponseOpt.isPresent()) {
                    // If the reponse already exists, update it
                    ReponseEntity existingReponseEntity = existingReponseOpt.get();
                    reponseMapper.mergeReponseEntity(existingReponseEntity, reponseEntity);
                } else {
                    // If the reponse does not exist, add it to the existingReponses list
                    reponseEntity.setQuestion(existingQuestionEntity);
                    existingQuestionEntity.getReponses().add(reponseEntity);
                }
            }

            // Merge other fields of the QuestionEntity
            existingQuestionEntity.setMiahoot(miahootEntity);
            miahootRepository.findById(miahootId).get().getQuestions().add(existingQuestionEntity);
            questionMapper.mergeQuestionEntity(existingQuestionEntity, questionDto);
            questionRepository.save(existingQuestionEntity);
        } else {
            throw new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de modifier.", idQuestionToModify), idQuestionToModify);
        }
    }
}
/* 
    public void updateQuestion(final Long idQuesToModify, final QuestionDto question) throws EntityNotFoundException{
        if (idQuesToModify == question.getId()) {
            Optional<QuestionEntity> quesOpt = questionRepository.findById(idQuesToModify);
            if (quesOpt.isPresent()) {
                QuestionEntity existingQuestionEntity = quesOpt.get();
                questionMapper.mergeQuestionEntity(quesOpt.get(), question);
                 // Get miahootId from QuestionDto and find the associated MiahootEntity
            Long miahootId = question.getMiahootId();
            MiahootEntity miahootEntity = miahootRepository.findById(miahootId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de modifier.", miahootId), miahootId));
                questionRepository.save(quesOpt.get());
                existingQuestionEntity.setMiahoot(miahootEntity);
            miahootRepository.findById(miahootId).get().getQuestions().add(existingQuestionEntity);
            questionRepository.save(existingQuestionEntity); // Save the existingQuestionEntity again after setting the MiahootEntity
            } else {
                throw new EntityNotFoundException(String.format("Aucune question n'a été trouvée pour l'id°[%d] : impossible de modifier.", idQuesToModify), idQuesToModify);
            }
        } //else throw new NotTheSameIdException(String.format("L'id de la question remplaçante([%d]) est différent de l'id de la question à remplacer([%lu])", question.getId(), idQuesToModify), question.getId(), idQuesToModify);

    }
    */

/* 

public void updateQuestion(final Long idQuestionToModify, final QuestionDto question) throws EntityNotFoundException {
    if (idQuestionToModify.equals(question.getId())) {
        Optional<QuestionEntity> questionOpt = questionRepository.findById(idQuestionToModify);
        if (questionOpt.isPresent()) {
            QuestionEntity existingQuestionEntity = questionOpt.get();
            // Clear the existing reponses list
            existingQuestionEntity.getReponses().clear();

            // Update the list of reponses
            for (ReponseDto reponseDto : question.getReponses()) {
                ReponseEntity reponseEntity = reponseMapper.toReponseEntity(reponseDto);
                reponseEntity.setQuestion(existingQuestionEntity);
                existingQuestionEntity.getReponses().add(reponseEntity);
            }

            questionMapper.mergeQuestionEntity(existingQuestionEntity, question);
            questionRepository.save(existingQuestionEntity);

            // Get miahootId from QuestionDto and find the associated MiahootEntity
            Long miahootId = question.getMiahootId();
            MiahootEntity miahootEntity = miahootRepository.findById(miahootId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de modifier.", miahootId), miahootId));

            // Set the MiahootEntity reference for the existingQuestionEntity
            existingQuestionEntity.setMiahoot(miahootEntity);
            miahootRepository.findById(miahootId).get().getQuestions().add(existingQuestionEntity);
            questionRepository.save(existingQuestionEntity); // Save the existingQuestionEntity again after setting the MiahootEntity
        } else {
            throw new EntityNotFoundException(String.format("Aucune Question n'a été trouvée pour l'id°[%d] : impossible de modifier.", idQuestionToModify), idQuestionToModify);
        }
    } //else throw new NotTheSameIdException(String.format("L'id de la Question remplaçante([%d]) est différente de l'id de la Question à remplacer([%d])", question.getId(), idQuestionToModify), question.getId(), idQuestionToModify);
}
*/

    public void deleteQuestion(final Long id) throws EntityNotFoundException {
        Optional<QuestionEntity> quesOpt = questionRepository.findById(id);
        if (quesOpt.isPresent()) {
            
            for (ReponseEntity reponse : quesOpt.get().getReponses()) {
                reponseRepository.deleteById(reponse.getId());
            }
            questionRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(String.format("Aucune question n'a été trouvé pour l'id°[%d] : impossible de supprimer.", id), id);
        }
    }


    public Collection<QuestionEntity> getQuestionsByMiahootId(Long miahootId) {
        return questionRepository.findQuestionsByMiahootId(miahootId);
    }

}
