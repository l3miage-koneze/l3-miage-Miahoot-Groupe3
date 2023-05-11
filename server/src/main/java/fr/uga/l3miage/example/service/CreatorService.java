package fr.uga.l3miage.example.service;

import fr.uga.l3miage.example.component.CreatorComponent;
import fr.uga.l3miage.example.exception.rest.*;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.CreatorMapper;
import fr.uga.l3miage.example.models.CreatorEntity;
import fr.uga.l3miage.example.response.CreatorDto;

import java.util.Objects;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CreatorService {

    private static final String ERROR_DETECTED = "Une erreur lors de la création de l'entité TestConfigWithProperties à été détecté.";
    private final CreatorComponent creatorComponent;
    private final CreatorMapper creatorMapper;

    public CreatorDto getCreator(final String id){
        try {
            return creatorMapper.toCreatorDto(creatorComponent.getCreator(id));
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(String.format("Aucun Creator n'a été trouvé pour l'id°[%s] : impossible de récupérer", id), 404l);
        }
    }

    public String createCreator(final CreatorDto creatorDto) throws Exception {
        CreatorEntity newCreatorEntity = creatorMapper.toCreatorEntity(creatorDto);
        try {
            return creatorComponent.createCreator(newCreatorEntity);
        } catch (AlreadyExistException ex) {
            throw new AlreadyUseRestException(ERROR_DETECTED,404l);
        }
    }

    public void updateCreator(final String idCreToModify,final CreatorDto creator) {
        if (Objects.equals(idCreToModify, creator.getId())){
            try {
                creatorComponent.updateCreator(idCreToModify,creator);
            } catch (EntityNotFoundException ex) {
                throw new EntityNotFoundRestException(String.format("Aucun créateur n'a pas été trouvé pour l'Id : Impossible de modifier", idCreToModify), 404l);
            }
        } else{
            throw new NotTheSameIdRestException(String.format("L'id du créateur remplaçant([%s]) est différent de l'id du créateur à remplacer([%s])", creator.getId(), idCreToModify), 404l, 404l);
        }
    }


    public ResponseEntity<?> createCreatorGoogle(CreatorDto creatorDto) {
        CreatorEntity newCreatorEntity = creatorMapper.toCreatorEntity(creatorDto);
        try {
            String creatorId = creatorComponent.createCreator(newCreatorEntity);
            return new ResponseEntity<>(creatorId, HttpStatus.CREATED);
        } catch (AlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    public boolean checkIfCreatorExists(String uid) {
        return creatorComponent.checkIfCreatorExists(uid);
    }
    
    @Transactional
    public void deleteCreator(final String id){
        try {
            creatorComponent.deleteCreator(id);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(String.format("Aucun créateur n'a été retrouvé pour l'id [%s] : impossible de supprimer",id),404l);
        }
    }


}