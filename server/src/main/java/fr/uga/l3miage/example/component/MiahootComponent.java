package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.MiahootMapper;
import fr.uga.l3miage.example.mapper.QuestionMapper;
import fr.uga.l3miage.example.models.CreatorEntity;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.repository.CreatorRepository;
import fr.uga.l3miage.example.repository.MiahootRepository;
import fr.uga.l3miage.example.repository.QuestionRepository;
import fr.uga.l3miage.example.response.MiahootDto;
import fr.uga.l3miage.example.response.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MiahootComponent {

    private final MiahootRepository miahootRepository;
    private final QuestionRepository questionRepository;
    private final MiahootMapper miahootMapper;
    private final QuestionMapper questionMapper;
    private final CreatorRepository creatorRepository;


    public MiahootEntity getMiahoot(final Long id) throws EntityNotFoundException {
        Optional<MiahootEntity> miaOpt = miahootRepository.findById(id);
        if (miaOpt.isPresent()){
            return miaOpt.get();
        }
        else{
            throw new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de récupérer", id), id);
        }
    }

    public Collection<MiahootEntity> getAllMiahoots() {
        return miahootRepository.findAll();
    }

    public Collection<MiahootEntity> findByName(String name) {
        return miahootRepository.findByName(name);
        
    }

    public Collection<MiahootEntity> findByCreatorId(String creatorId) {
        return miahootRepository.findByCreatorId(creatorId);
    }
/* 
    public Long createMiahoot(final MiahootEntity miahoot) throws AlreadyExistException {

        if (miahoot.getId() == null){
            MiahootEntity mia = miahootRepository.save(miahoot);
            System.out.println("Miahoot create");
            System.out.println(mia.getId());
            return mia.getId();
        }
        else{
            if (miahootRepository.findById(miahoot.getId()).isPresent()){
                throw new AlreadyExistException(String.format("Le Miahoot n°[%d] existe déjà en BD.", miahoot.getId()), miahoot.getId());
            }
            else{
                return miahootRepository.save(miahoot).getId();
            }
        }

    }

public Long createMiahoot(final Long creatorId, final MiahootEntity miahoot) throws AlreadyExistException, EntityNotFoundException{
    if (creatorRepository.findById(creatorId).isPresent()) {
        if (Objects.equals(miahoot.getId(), null)){
            creatorRepository.findById(creatorId).get().getMiahoots().add(miahoot);
            return miahootRepository.save(miahoot).getId();
        }
        else{
            if (miahootRepository.findById(miahoot.getId()).isPresent()){
                throw new AlreadyExistException(String.format("La Participant n°[%d] existe déjà en BD.", miahoot.getId()), miahoot.getId());
            }
            else{
                return miahootRepository.save(miahoot).getId();
            }
        }
    }
    else{
        throw new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de récupérer",creatorId), creatorId);
    }
}
*/
public Long createMiahoot(final String creatorId, final MiahootEntity miahoot) throws AlreadyExistException, EntityNotFoundException{
    Optional<CreatorEntity> creatorOpt = creatorRepository.findByUId(creatorId);

    if (creatorOpt.isPresent()) {
        CreatorEntity creator = creatorOpt.get();
        miahoot.setCreator(creator);
        creatorRepository.findByUId(creatorId).get().getMiahoots().add(miahoot);
        MiahootEntity savedMiahoot = miahootRepository.save(miahoot);
        return savedMiahoot.getId();
    } else {
        throw new EntityNotFoundException(String.format("Aucun Creator n'a été trouvé pour l'id°[%s] : impossible de créer le Miahoot", creatorId), 404l);
    }
}

public void updateMiahoot(final Long idMiaToModify, final MiahootDto miahoot) throws EntityNotFoundException {
    if (idMiaToModify.equals(miahoot.getId())) {
        Optional<MiahootEntity> miaOpt = miahootRepository.findById(idMiaToModify);
        if (miaOpt.isPresent()) {
            MiahootEntity existingMiahootEntity = miaOpt.get();

            // Get the existing questions list
            Collection<QuestionEntity> existingQuestions = existingMiahootEntity.getQuestions();

            // Update the list of questions
            for (QuestionDto questionDto : miahoot.getQuestions()) {
                QuestionEntity questionEntity = questionMapper.toQuestionEntity(questionDto);

                // Check if the question already exists in the existingQuestions list
                Optional<QuestionEntity> existingQuestionOpt = existingQuestions.stream()
                        .filter(q -> q.getId().equals(questionDto.getId()))
                        .findFirst();

                if (existingQuestionOpt.isPresent()) {
                    // If the question already exists, update it
                    QuestionEntity existingQuestionEntity = existingQuestionOpt.get();
                    questionMapper.mergeQuestionEntity(existingQuestionEntity, questionEntity);
                } else {
                    // If the question does not exist, add it to the existingQuestions list
                    questionEntity.setMiahoot(existingMiahootEntity);
                    existingMiahootEntity.getQuestions().add(questionEntity);
                }
            }

            // Merge other fields of the MiahootEntity
            miahootMapper.mergeMiahootEntity(existingMiahootEntity, miahoot);
            miahootRepository.save(existingMiahootEntity);
        } else {
            throw new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de modifier.", idMiaToModify), idMiaToModify);
        }
    }
}


/* 
    public void updateMiahoot(final Long idMiaToModify, final MiahootDto miahoot) throws EntityNotFoundException{
        if (idMiaToModify == miahoot.getId()) {
            Optional<MiahootEntity> miaOpt = miahootRepository.findById(idMiaToModify);
            if (miaOpt.isPresent()){
                miahootMapper.mergeMiahootEntity(miaOpt.get(), miahoot);
                miahootRepository.save(miaOpt.get());
            }
            else{
                throw new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de modifier.", idMiaToModify), idMiaToModify);
            }
        } //else throw new NotTheSameIdException(String.format("L'id du Miahoot remplaçant([%d]) est différent de l'id du Miahoot à remplacer([%d])", miahoot.getId(), idMiaToModify), miahoot.getId(), idMiaToModify);
    }

    */
/* 
public void updateMiahoot(final Long idMiaToModify, final MiahootDto miahoot) throws EntityNotFoundException {
    if (idMiaToModify.equals(miahoot.getId())) {
        Optional<MiahootEntity> miaOpt = miahootRepository.findById(idMiaToModify);
        if (miaOpt.isPresent()) {
            MiahootEntity existingMiahootEntity = miaOpt.get();
            List<QuestionEntity> updatedQuestions = new ArrayList<>();
            for (QuestionDto questionDto : miahoot.getQuestions()) {
                QuestionEntity questionEntity = questionMapper.toQuestionEntity(questionDto);
                questionEntity.setMiahoot(existingMiahootEntity);
                updatedQuestions.add(questionEntity);
            }
            existingMiahootEntity.setQuestions(updatedQuestions);
            miahootMapper.mergeMiahootEntity(existingMiahootEntity, miahoot);
            miahootRepository.save(existingMiahootEntity);
        } else {
            throw new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de modifier.", idMiaToModify), idMiaToModify);
        }
    } // else throw new NotTheSameIdException(String.format("L'id du Miahoot remplaçant([%d]) est différent de l'id du Miahoot à remplacer([%d])", miahoot.getId(), idMiaToModify), miahoot.getId(), idMiaToModify);
}
*/

    public void deleteMiahoot(final Long id) throws EntityNotFoundException {
        Optional<MiahootEntity> miaOpt = miahootRepository.findById(id);
        if (miaOpt.isPresent()) {
            miahootRepository.deleteById(id);
            for (QuestionEntity question : miaOpt.get().getQuestions()) {
                questionRepository.deleteById(question.getId());
            }
        } else {
            throw new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de supprimer.", id), id);
        }
    }


    public Collection<QuestionEntity> getQuestionsMiahoot (final Long id) throws EntityNotFoundException{
        Optional<MiahootEntity> miaOpt = miahootRepository.findById(id);
        if(miaOpt.isPresent()){
            return miaOpt.get().getQuestions();
        } else {
            throw new EntityNotFoundException(String.format("Pas de Miahoot de avec cet id trouvé donc pas de questions", id), id);
        }
    }

    public Collection<QuestionEntity> getQuestionsByMiahootId(Long miahootId) {
        return questionRepository.findQuestionsByMiahootId(miahootId);
    }

    public void saveMiahoot(MiahootEntity existingMiahootEntity) {
        miahootRepository.save(existingMiahootEntity);
    }


}
