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

    /*
     * Récupérer un question par id
     */
    public QuestionEntity getQuestion(final Long id) throws EntityNotFoundException {
        Optional<QuestionEntity> quesOpt = questionRepository.findById(id);
        if (quesOpt.isPresent()) {
            return quesOpt.get();
        } else {
            throw new EntityNotFoundException(
                    String.format("Aucune question n'a été trouvé pour l'id°[%d] : impossible de récupérer", id), id);
        }

    }

    /*
     * Créer un nouvel question
     */
    public Long createQuestion(final Long miahootId, final QuestionEntity question)
            throws AlreadyExistException, EntityNotFoundException {
        Optional<MiahootEntity> miahootOpt = miahootRepository.findById(miahootId);

        if (miahootOpt.isPresent()) {
            MiahootEntity miahoot = miahootOpt.get();
            question.setMiahoot(miahoot);
            questionMapper.toQuestionDto(question).setMiahootId(miahootId);
            miahootRepository.findById(miahootId).get().getQuestions().add(question);
            QuestionEntity savedQuestion = questionRepository.save(question);
            return savedQuestion.getId();
        } else {
            throw new EntityNotFoundException(String
                    .format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de créer la question", miahootId),
                    miahootId);
        }
    }

    /*
     * Mettre à jour que le laber d'un question
     */
    public void updateQuestion(final Long idQuestionToModify, final QuestionDto questionDto)
            throws EntityNotFoundException {
        if (idQuestionToModify.equals(questionDto.getId())) {
            Optional<QuestionEntity> questionOpt = questionRepository.findById(idQuestionToModify);
            if (questionOpt.isPresent()) {
                QuestionEntity existingQuestionEntity = questionOpt.get();

                // mettre à jour que le label
                existingQuestionEntity.setLabel(questionDto.getLabel());

                questionRepository.save(existingQuestionEntity);
            } else {
                throw new EntityNotFoundException(
                        String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de modifier.",
                                idQuestionToModify),
                        idQuestionToModify);
            }
        }
    }

    /*
     * Supprimer un question avec l'id donnée
     */
    public void deleteQuestion(final Long id) throws EntityNotFoundException {
        Optional<QuestionEntity> quesOpt = questionRepository.findById(id);
        if (quesOpt.isPresent()) {

            for (ReponseEntity reponse : quesOpt.get().getReponses()) {
                reponseRepository.deleteById(reponse.getId());
            }
            questionRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(
                    String.format("Aucune question n'a été trouvé pour l'id°[%d] : impossible de supprimer.", id), id);
        }
    }

    /*
     * Récupérer tous les question d'un Miahoot avec l'id donnée
     */
    public Collection<QuestionEntity> getQuestionsByMiahootId(Long miahootId) {
        return questionRepository.findQuestionsByMiahootId(miahootId);
    }

}
