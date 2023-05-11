package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.CreatorMapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.models.CreatorEntity;
import fr.uga.l3miage.example.repository.MiahootRepository;
import fr.uga.l3miage.example.repository.CreatorRepository;
import fr.uga.l3miage.example.response.CreatorDto;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreatorComponent {

    private final MiahootRepository miahootRepository;
    private final CreatorRepository creatorRepository;
    private final CreatorMapper creatorMapper;


    /*
     * Récupérer un creator par uid
     */
    public CreatorEntity getCreator(final String uid) throws EntityNotFoundException {
        Optional<CreatorEntity> creOpt = creatorRepository.findByUId(uid);
        if (creOpt.isPresent()){
            return creOpt.get();
        }
        else{
            throw new EntityNotFoundException(String.format("Aucun créateur n'a été trouvé pour l'id°[%s] : impossible de récupérer", uid), 404l);
        }
    }

    /*
     * Créer un nouvel creator
     */
    public String createCreator(final CreatorEntity creator) throws AlreadyExistException {
        if (creator.getId() == ""){
            CreatorEntity cre = creatorRepository.save(creator);
            System.out.println("Créateur created");
            System.out.println(cre.getId());
            return cre.getId();
        }
        else{
            if (creatorRepository.findByUId(creator.getId()).isPresent()){
                throw new AlreadyExistException(String.format("Le créateur n°[%s] existe déjà en BD.", creator.getId()), 404l);
            }
            else{
                return creatorRepository.save(creator).getId();
            }
        }
    }

    /*
     * Vérifier si le creator avec uid donnée existe ou pas
     */
    public boolean checkIfCreatorExists(String uid) {
        return creatorRepository.existsByUid(uid);
    }

    /*
     * Mettre à jour un creator
     */
    public void updateCreator(final String idCreToModify, final CreatorDto creator) throws EntityNotFoundException{
        if (idCreToModify == creator.getId()) {
            Optional<CreatorEntity> creOpt = creatorRepository.findByUId(idCreToModify);
            if (creOpt.isPresent()){
                creatorMapper.mergeCreatorEntity(creOpt.get(), creator);
                creatorRepository.save(creOpt.get());
            }
            else{
                throw new EntityNotFoundException(String.format("Aucun créateur n'a été trouvé pour l'id°[%s] : impossible de modifier.", idCreToModify), 404l);
            }
        } 
    }


    /*
     * Supprimer un creator avec l'uid donnée
     */
    public void deleteCreator(final String uid) throws EntityNotFoundException {
        Optional<CreatorEntity> creOpt = creatorRepository.findByUId(uid);
        if (creOpt.isPresent()) {
            creatorRepository.deleteById(uid);
            for (MiahootEntity miahoot : creOpt.get().getMiahoots()) {
                miahootRepository.deleteById(miahoot.getId());
            }
        } else {
            throw new EntityNotFoundException(String.format("Aucun créateur n'a été trouvé pour l'id°[%d] : impossible de supprimer.", uid), 404l);
        }
    }

   




}
