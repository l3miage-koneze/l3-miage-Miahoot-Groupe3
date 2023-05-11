package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.ReponseMapper;
import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.models.ReponseEntity;
import fr.uga.l3miage.example.repository.ReponseRepository;
import fr.uga.l3miage.example.repository.QuestionRepository;
import fr.uga.l3miage.example.response.ReponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReponseComponent {

    private final ReponseRepository reponseRepository;
    private final QuestionRepository questionRepository;
    private final ReponseMapper reponseMapper;

    /*
     * Récupérer un reponse par uid
     */
    public ReponseEntity getReponse(final Long id) throws EntityNotFoundException {
        Optional<ReponseEntity> repOpt = reponseRepository.findById(id);
        if (repOpt.isPresent()) {
            return repOpt.get();
        } else {
            throw new EntityNotFoundException(
                    String.format("Aucune réponse n'a été trouvé pour l'id°[%d] : impossible de récupérer", id), id);
        }

    }

    /*
     * Créer un nouvel reponse
     */
    public Long createReponse(final Long questionId, final ReponseEntity reponse)
            throws AlreadyExistException, EntityNotFoundException {
        Optional<QuestionEntity> questionOpt = questionRepository.findById(questionId);

        if (questionOpt.isPresent()) {
            QuestionEntity question = questionOpt.get();
            reponse.setQuestion(question);
            reponseMapper.toReponseDto(reponse).setQuestionId(questionId);
            questionRepository.findById(questionId).get().getReponses().add(reponse);
            ReponseEntity savedReponse = reponseRepository.save(reponse);
            return savedReponse.getId();
        } else {
            throw new EntityNotFoundException(
                    String.format("Aucun question n'a été trouvé pour l'id°[%d] : impossible de créer la question",
                            questionId),
                    questionId);
        }

    }

    /*
     * Mettre à jour un reponse
     */
    public void updateReponse(final Long idReponseToModify, final ReponseDto reponse) throws EntityNotFoundException {
        if (idReponseToModify == reponse.getId()) {
            Optional<ReponseEntity> reponseOpt = reponseRepository.findById(idReponseToModify);
            if (reponseOpt.isPresent()) {
                ReponseEntity existingReponseEntity = reponseOpt.get();
                reponseMapper.mergeReponseEntity(reponseOpt.get(), reponse);

                Long questionId = reponse.getQuestionId();
                QuestionEntity questionEntity = questionRepository.findById(questionId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                String.format("Aucun Question n'a été trouvé pour l'id°[%d] : impossible de modifier.", questionId),questionId));
                reponseRepository.save(reponseOpt.get());
                existingReponseEntity.setQuestion(questionEntity);
                questionRepository.findById(questionId).get().getReponses().add(existingReponseEntity);
                reponseRepository.save(existingReponseEntity);

            } else {
                throw new EntityNotFoundException(
                        String.format("Aucune reponse n'a été trouvée pour l'id°[%d] : impossible de modifier.",idReponseToModify),idReponseToModify);
            }
        }
    }

    /*
     * Supprimer un reponse avec l'id donnée
     */
    public void deleteReponse(final Long id) throws EntityNotFoundException {
        Optional<ReponseEntity> repOpt = reponseRepository.findById(id);
        if (repOpt.isPresent()) {
            reponseRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(
                    String.format("Aucune réponse n'a été trouvée pour l'id°[%d] : impossible de supprimer.", id), id);
        }
    }

    /*
     * Récupérer tous les reponses d'un question avec id donnée
     */
    public List<ReponseEntity> getReponsesByQuestionId(Long questionId) {
        return reponseRepository.findReponsesByQuestionId(questionId);
    }

}
