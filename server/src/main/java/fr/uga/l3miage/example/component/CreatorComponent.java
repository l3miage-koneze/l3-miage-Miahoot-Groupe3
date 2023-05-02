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


    public CreatorEntity getCreator(final Long id) throws EntityNotFoundException {
        Optional<CreatorEntity> creOpt = creatorRepository.findById(id);
        if (creOpt.isPresent()){
            return creOpt.get();
        }
        else{
            throw new EntityNotFoundException(String.format("Aucun créateur n'a été trouvé pour l'id°[%d] : impossible de récupérer", id), id);
        }
    }

    public Long createCreator(final CreatorEntity creator) throws AlreadyExistException {

        if (creator.getId() == null){
            CreatorEntity cre = creatorRepository.save(creator);
            System.out.println("Créateur created");
            System.out.println(cre.getId());
            return cre.getId();
        }
        else{
            if (creatorRepository.findById(creator.getId()).isPresent()){
                throw new AlreadyExistException(String.format("Le créateur n°[%d] existe déjà en BD.", creator.getId()), creator.getId());
            }
            else{
                return creatorRepository.save(creator).getId();
            }
        }

    }

    public void updateCreator(final Long idCreToModify, final CreatorDto creator) throws EntityNotFoundException{
        if (idCreToModify == creator.getId()) {
            Optional<CreatorEntity> creOpt = creatorRepository.findById(idCreToModify);
            if (creOpt.isPresent()){
                creatorMapper.mergeCreatorEntity(creOpt.get(), creator);
                creatorRepository.save(creOpt.get());
            }
            else{
                throw new EntityNotFoundException(String.format("Aucun créateur n'a été trouvé pour l'id°[%d] : impossible de modifier.", idCreToModify), idCreToModify);
            }
        } //else throw new NotTheSameIdException(String.format("L'id du Miahoot remplaçant([%d]) est différent de l'id du Miahoot à remplacer([%d])", miahoot.getId(), idMiaToModify), miahoot.getId(), idMiaToModify);
    }


    public void deleteCreator(final Long id) throws EntityNotFoundException {
        Optional<CreatorEntity> creOpt = creatorRepository.findById(id);
        if (creOpt.isPresent()) {
            creatorRepository.deleteById(id);
            for (MiahootEntity miahoot : creOpt.get().getMiahoots()) {
                miahootRepository.deleteById(miahoot.getId());
            }
        } else {
            throw new EntityNotFoundException(String.format("Aucun créateur n'a été trouvé pour l'id°[%d] : impossible de supprimer.", id), id);
        }
    }


}
