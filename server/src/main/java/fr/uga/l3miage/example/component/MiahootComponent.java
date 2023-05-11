package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.models.CreatorEntity;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.repository.CreatorRepository;
import fr.uga.l3miage.example.repository.MiahootRepository;
import fr.uga.l3miage.example.repository.QuestionRepository;
import fr.uga.l3miage.example.response.MiahootDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MiahootComponent {

    private final MiahootRepository miahootRepository;
    private final QuestionRepository questionRepository;
    private final CreatorRepository creatorRepository;

    /*
     * Récupérer un Miahoot par id donnée
     */
    public MiahootEntity getMiahoot(final Long id) throws EntityNotFoundException {
        Optional<MiahootEntity> miaOpt = miahootRepository.findById(id);
        if (miaOpt.isPresent()) {
            return miaOpt.get();
        } else {
            throw new EntityNotFoundException(
                    String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de récupérer", id), id);
        }
    }

    /*
     * Récupérer tous les Miahoots
     */
    public Collection<MiahootEntity> getAllMiahoots() {
        return miahootRepository.findAll();
    }

    /*
     * Chercher le(s) Miahoot(s) par le nom
     */
    public Collection<MiahootEntity> findByName(String name) {
        return miahootRepository.findByName(name);

    }

    /*
     * Chercher le(s) Miahoot(s) par l'id de creator
     */
    public Collection<MiahootEntity> findByCreatorId(String creatorId) {
        return miahootRepository.findByCreatorId(creatorId);
    }

    /*
     * Créer un nouvel Miahoot
     */
    public Long createMiahoot(final String creatorId, final MiahootEntity miahoot)
            throws AlreadyExistException, EntityNotFoundException {
        Optional<CreatorEntity> creatorOpt = creatorRepository.findByUId(creatorId);
        if (creatorOpt.isPresent()) {
            CreatorEntity creator = creatorOpt.get();
            miahoot.setCreator(creator);
            creatorRepository.findByUId(creatorId).get().getMiahoots().add(miahoot);
            MiahootEntity savedMiahoot = miahootRepository.save(miahoot);
            return savedMiahoot.getId();
        } else {
            throw new EntityNotFoundException(String.format(
                    "Aucun Creator n'a été trouvé pour l'id°[%s] : impossible de créer le Miahoot", creatorId), 404l);
        }
    }

    /*
     * Mettre à jour que le nom d'un Miahoot
     */
    public void updateMiahoot(final Long idMiaToModify, final MiahootDto miahoot) throws EntityNotFoundException {
        if (idMiaToModify.equals(miahoot.getId())) {
            Optional<MiahootEntity> miaOpt = miahootRepository.findById(idMiaToModify);
            if (miaOpt.isPresent()) {
                MiahootEntity existingMiahootEntity = miaOpt.get();
                //Mettre à jour que le nom
                existingMiahootEntity.setNom(miahoot.getNom());
                miahootRepository.save(existingMiahootEntity);
            } else {
                throw new EntityNotFoundException(String
                        .format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de modifier.", idMiaToModify),
                        idMiaToModify);
            }
        }
    }

    /*
     * Supprimer un Miahoot avec l'id donnée
     */
    public void deleteMiahoot(final Long id) throws EntityNotFoundException {
        Optional<MiahootEntity> miaOpt = miahootRepository.findById(id);
        if (miaOpt.isPresent()) {

            for (QuestionEntity question : miaOpt.get().getQuestions()) {
                questionRepository.deleteById(question.getId());
            }
            miahootRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(
                    String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de supprimer.", id), id);
        }
    }

    /*
     * Récupérer tous les questions d'un Miahoot avec id donnée
     */
    public Collection<QuestionEntity> getQuestionsByMiahootId(Long miahootId) {
        return questionRepository.findQuestionsByMiahootId(miahootId);
    }

    /*
     * Sauvegarder un Miahoot
     */
    public void saveMiahoot(MiahootEntity existingMiahootEntity) {
        miahootRepository.save(existingMiahootEntity);
    }

}
