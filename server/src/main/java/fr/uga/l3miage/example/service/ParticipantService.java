package fr.uga.l3miage.example.service;

import fr.uga.l3miage.example.component.ParticipantComponent;
import fr.uga.l3miage.example.exception.rest.*;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.ParticipantMapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.models.ParticipantEntity;
import fr.uga.l3miage.example.response.ParticipantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantService {
    private static final String ERROR_DETECTED = "Une erreur lors de la création de l'entité TestConfigWithProperties à été détecté.";
    private final ParticipantComponent participantComponent;
    private final ParticipantMapper participantMapper;


    public ParticipantDto getParticipant(final Long id)  {
        try {
            return participantMapper.toParticipantDto(participantComponent.getParticipant(id));
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(String.format("Aucune Participant n'a été trouvé pour l'id°[%d] : impossible de récupérer", id), id);
        }
    }


    public Long createParticipant(final Long miahootId, final ParticipantDto participantDto){
        ParticipantEntity newParticipantEntity = participantMapper.toParticipantEntity(participantDto);
        try {
            return participantComponent.createParticipant(miahootId, newParticipantEntity);
        } catch (AlreadyExistException ex) {
            throw new AlreadyUseRestException(ERROR_DETECTED,participantDto.getId(),ex);
        } catch (EntityNotFoundException ex){
            throw new EntityNotFoundRestException(ERROR_DETECTED,participantDto.getId(),ex);
        }
    }


    public void updateParticipant(final Long idParToModify,final ParticipantDto participant){
        if (idParToModify == participant.getId()){
            try {
                participantComponent.updateParticipant(idParToModify,participant);
            } catch (EntityNotFoundException ex) {
                throw new EntityNotFoundRestException(String.format("Aucune Participant n'a  été trouvé pour l'Id : Impossible de modifier",idParToModify),idParToModify);
            }
        }else{
            throw new NotTheSameIdRestException(String.format("L'id de la Participant remplaçante([%lu]) est différent de l'id de la Participant à remplacer([%lu])", participant.getId(), idParToModify), participant.getId(), idParToModify);
        }

    }

    @Transactional
    public void deleteParticipant(final Long id){
        try {
            participantComponent.deleteParticipant(id);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(String.format("Aucune Participant n'a été trouvé pour l'id°[%lu] : impossible de supprimer.", id), id);
        }
    }


    public List<ParticipantDto> getALlParticipants() {
            List<ParticipantEntity> participantEntities = participantComponent.getAllParticipants();
            return participantEntities.stream()
                    .map(participantMapper::toParticipantDto)
                    .collect(Collectors.toList());


    }

}
