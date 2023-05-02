package fr.uga.l3miage.example.service;

import fr.uga.l3miage.example.component.CreatorComponent;
import fr.uga.l3miage.example.exception.rest.*;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.CreatorMapper;
import fr.uga.l3miage.example.models.CreatorEntity;
import fr.uga.l3miage.example.response.CreatorDto;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CreatorService {

    private static final String ERROR_DETECTED = "Une erreur lors de la création de l'entité TestConfigWithProperties à été détecté.";
    private final CreatorComponent creatorComponent;
    private final CreatorMapper creatorMapper;
    public CreatorDto getCreator(final Long id){
        try {
            return creatorMapper.toCreatorDto(creatorComponent.getCreator(id));
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(String.format("Aucun Creator n'a été trouvé pour l'id°[%d] : impossible de récupérer", id), id);
        }
    }

    public Long createCreator(final CreatorDto creatorDto) throws Exception {
        CreatorEntity newCreatorEntity = creatorMapper.toCreatorEntity(creatorDto);
        try {
            return creatorComponent.createCreator(newCreatorEntity);
        } catch (AlreadyExistException ex) {
            throw new AlreadyUseRestException(ERROR_DETECTED,newCreatorEntity.getId(),ex);
        }
    }

    public void updateCreator(final Long idCreToModify,final CreatorDto creator) {
        if (Objects.equals(idCreToModify, creator.getId())){
            try {
                creatorComponent.updateCreator(idCreToModify,creator);
            } catch (EntityNotFoundException ex) {
                throw new EntityNotFoundRestException(String.format("Aucun créateur n'a pas été trouvé pour l'Id : Impossible de modifier", idCreToModify), idCreToModify);
            }
        } else{
            throw new NotTheSameIdRestException(String.format("L'id du créateur remplaçant([%lu]) est différent de l'id du créateur à remplacer([%lu])", creator.getId(), idCreToModify), creator.getId(), idCreToModify);
        }
    }


    @Transactional
    public void deleteCreator(final Long id){
        try {
            creatorComponent.deleteCreator(id);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(String.format("Aucun créateur n'a été retrouvé pour l'id [%d] : impossible de supprimer",id),id);
        }
    }


}