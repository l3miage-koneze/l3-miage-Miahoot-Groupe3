package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.MiahootMapper;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MiahootComponent {

    private final MiahootRepository miahootRepository;
    private final QuestionRepository questionRepository;
    private final MiahootMapper miahootMapper;
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

    public List<MiahootEntity> getAllMiahoots() {
        return miahootRepository.findAll();
    }

    public List<MiahootEntity> findByName(String name) {
        return miahootRepository.findByName(name);
        
    }

    public List<MiahootEntity> findByCreatorId(Long creatorId) {
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
public Long createMiahoot(final Long creatorId, final MiahootEntity miahoot) throws AlreadyExistException, EntityNotFoundException{
    Optional<CreatorEntity> creatorOpt = creatorRepository.findById(creatorId);

    if (creatorOpt.isPresent()) {
        CreatorEntity creator = creatorOpt.get();
        miahoot.setCreator(creator);
        MiahootEntity savedMiahoot = miahootRepository.save(miahoot);
        return savedMiahoot.getId();
    } else {
        throw new EntityNotFoundException(String.format("Aucun Creator n'a été trouvé pour l'id°[%d] : impossible de créer le Miahoot", creatorId), creatorId);
    }
}

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


}
