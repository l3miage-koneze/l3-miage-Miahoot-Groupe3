package fr.uga.l3miage.example.service;

import fr.uga.l3miage.example.component.MiahootComponent;
import fr.uga.l3miage.example.component.QuestionComponent;
import fr.uga.l3miage.example.exception.rest.*;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.MiahootMapper;
import fr.uga.l3miage.example.mapper.QuestionMapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.response.MiahootDto;
import fr.uga.l3miage.example.response.QuestionDto;

import java.io.Console;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class MiahootService {

    private static final String ERROR_DETECTED = "Une erreur lors de la création de l'entité TestConfigWithProperties à été détecté.";
    private final MiahootComponent miahootComponent;
    private final MiahootMapper miahootMapper;
    private final QuestionComponent questionComponent;
    private final QuestionMapper questionMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MiahootService.class);

    public MiahootDto getMiahoot(final Long id){
        try {
            return miahootMapper.toMiahootDto(miahootComponent.getMiahoot(id));
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de récupérer", id), id);
        }
    }

    public Collection<MiahootDto> getAllMiahoots(){
        Collection<MiahootEntity> miahootEntities = miahootComponent.getAllMiahoots();
        return miahootEntities.stream()
            .map(miahootMapper::toMiahootDto)
            .collect(Collectors.toList());
    }

    public Collection<MiahootDto> findByName(final String nom){
        Collection<MiahootEntity> miahootEntities = miahootComponent.findByName(nom);
        return miahootEntities.stream()
            .map(miahootMapper::toMiahootDto)
            .collect(Collectors.toList());
    }


public Collection<MiahootDto> findByCreatorId(String creatorId) {
    Collection<MiahootEntity> miahootEntities = miahootComponent.findByCreatorId(creatorId);
    return miahootEntities.stream()
            .map(miahootMapper::toMiahootDto)
            .collect(Collectors.toList());
}
/* 
    public Long createMiahoot(final MiahootDto miahootDto) throws Exception {
        MiahootEntity newMiahootEntity = miahootMapper.toMiahootEntity(miahootDto);
        try {
            return miahootComponent.createMiahoot(newMiahootEntity);
        } catch (AlreadyExistException ex) {
            throw new AlreadyUseRestException(ERROR_DETECTED,newMiahootEntity.getId(),ex);
        }
    }
*/
public Long createMiahoot(final String creatorId, final MiahootDto miahootDto){
    MiahootEntity newMiahootEntity = miahootMapper.toMiahootEntity(miahootDto);
    try {
        return miahootComponent.createMiahoot(creatorId, newMiahootEntity);
    } catch (AlreadyExistException ex) {
        throw new AlreadyUseRestException(ERROR_DETECTED,miahootDto.getId(),ex);
    } catch (EntityNotFoundException ex){
        throw new EntityNotFoundRestException(ERROR_DETECTED,miahootDto.getId(),ex);
    }
}
/* 
    public void updateMiahoot(final Long idMiaToModify,final MiahootDto miahoot) {
        if (Objects.equals(idMiaToModify, miahoot.getId())){
            try {
                miahootComponent.updateMiahoot(idMiaToModify,miahoot);
            } catch (EntityNotFoundException ex) {
                throw new EntityNotFoundRestException(String.format("Aucun Miahoot n'a pas été trouvé pour l'Id : Impossible de modifier", idMiaToModify), idMiaToModify);
            }
        } else{
           throw new NotTheSameIdRestException(String.format("L'id du Miahoot remplaçant([%d]) est différent de l'id du Miahoot à remplacer([%d])", miahoot.getId(), idMiaToModify), miahoot.getId(), idMiaToModify);
        }
    }

*/
public void updateMiahoot(final Long idMiaToModify, final MiahootDto miahootDto) {
    if (Objects.equals(idMiaToModify, miahootDto.getId())) {
        
            try {
                miahootComponent.updateMiahoot(idMiaToModify,miahootDto);
            } catch (EntityNotFoundException ex) {
                throw new EntityNotFoundRestException(String.format("Aucune question n'a été trouvée pour l'id [%d] : impossible de récupérer", idMiaToModify), idMiaToModify);
            }
    } else {
        throw new NotTheSameIdRestException(String.format("L'id du Miahoot remplaçant([%d]) est différent de l'id du Miahoot à remplacer([%d])", miahootDto.getId(), idMiaToModify), miahootDto.getId(), idMiaToModify);
    }
}


    @Transactional
    public void deleteMiahoot(final Long id){
        try {
            miahootComponent.deleteMiahoot(id);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(String.format("Aucun Miahoot n'a été retrouvé pour l'id [%d] : impossible de supprimer",id),id);
        }
    }




}