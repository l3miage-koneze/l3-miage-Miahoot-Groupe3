package fr.uga.l3miage.example.service;

import fr.uga.l3miage.example.component.MiahootComponent;
import fr.uga.l3miage.example.exception.rest.*;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.MiahootMapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.repository.MiahootRepository;
import fr.uga.l3miage.example.response.MiahootDto;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MiahootService {

    private static final String ERROR_DETECTED = "Une erreur lors de la création de l'entité TestConfigWithProperties à été détecté.";
    private final MiahootComponent miahootComponent;
    private final MiahootMapper miahootMapper;

    public MiahootDto getMiahoot(final Long id){
        try {
            return miahootMapper.toMiahootDto(miahootComponent.getMiahoot(id));
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de récupérer", id), id);
        }
    }

    public List<MiahootDto> getAllMiahoots(){
        List<MiahootEntity> miahootEntities = miahootComponent.getAllMiahoots();
        return miahootEntities.stream()
            .map(miahootMapper::toMiahootDto)
            .collect(Collectors.toList());
    }

    public List<MiahootDto> findByName(final String nom){
        List<MiahootEntity> miahootEntities = miahootComponent.findByName(nom);
        return miahootEntities.stream()
            .map(miahootMapper::toMiahootDto)
            .collect(Collectors.toList());
    }

    public void createMiahoot(final MiahootDto miahootDto) throws Exception {
        MiahootEntity newMiahootEntity = miahootMapper.toMiahootEntity(miahootDto);
        try {
            miahootComponent.createMiahoot(newMiahootEntity);
        } catch (AlreadyExistException ex) {
            throw new AlreadyUseRestException(ERROR_DETECTED,newMiahootEntity.getId(),ex);
        }
    }

    public void updateMiahoot(final Long idMiaToModify,final MiahootDto miahoot) {
        if (Objects.equals(idMiaToModify, miahoot.getId())){
            try {
                miahootComponent.updateMiahoot(idMiaToModify,miahoot);
            } catch (EntityNotFoundException ex) {
                throw new EntityNotFoundRestException(String.format("Aucun Miahoot n'a pas été trouvé pour l'Id : Impossible de modifier", idMiaToModify), idMiaToModify);
            }
        } else{
           throw new NotTheSameIdRestException(String.format("L'id du Miahoot remplaçant([%lu]) est différent de l'id du Miahoot à remplacer([%lu])", miahoot.getId(), idMiaToModify), miahoot.getId(), idMiaToModify);
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