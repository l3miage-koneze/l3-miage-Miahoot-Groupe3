package fr.uga.l3miage.example.service;

import fr.uga.l3miage.example.component.ExampleComponent;
import fr.uga.l3miage.example.component.MiahootComponent;
import fr.uga.l3miage.example.exception.rest.*;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.MiahootMapper;
import fr.uga.l3miage.example.mapper.TestMapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.request.CreateMiahootRequest;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.MiahootDto;
import fr.uga.l3miage.example.response.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MiahootService {

    private static final String ERROR_DETECTED = "Une erreur lors de la création de l'entité TestConfigWithProperties à été détecté.";
    private final MiahootComponent miahootComponent;
    private final MiahootMapper miahootMapper;

    public MiahootDto getMiahoot(final Long id) throws EntityNotFoundException{
        try {
            return miahootMapper.toMiahootDto(miahootComponent.getMiahoot(id));
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%lu] : impossible de récupérer", id), id);
        }
    }


    public void createMiahoot(final CreateMiahootRequest createMiahootRequest) throws AlreadyExistException {
        MiahootEntity newMiahootEntity = miahootMapper.toMiahootEntity(createMiahootRequest);
        try {
            miahootComponent.createMiahoot(newMiahootEntity);
        } catch (AlreadyExistException ex) {
            throw new AlreadyExistException(ERROR_DETECTED,newMiahootEntity.getId(),ex);
        }
    }

    public void updateMiahoot(final Long idMiaToModify,final MiahootDto miahoot) throws EntityNotFoundException, NotTheSameIdException {
        try {
            miahootComponent.updateMiahoot(idMiaToModify,miahoot);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(String.format("Aucun Miahoot n'a pas été trouvé pour l'Id : Impossible de modifier",idMiaToModify),idMiaToModify);
        } catch (NotTheSameIdException ex) {
            throw new NotTheSameIdException(String.format("L'id du Miahoot remplaçant([%lu]) est différent de l'id du Miahoot à remplacer([%lu])", miahoot.getId(), idMiaToModify), miahoot.getId(), idMiaToModify);
        }
    }


    @Transactional
    public void deleteMiahoot(final Long id) throws EntityNotFoundException {
        try {
            miahootComponent.deleteMiahoot(id);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(String.format("Aucun Miahoot n'a été retrouvé pour l'id [%lu] : impossible de supprimer",id),id);
        }
    }
}