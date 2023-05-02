package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.ParticipantMapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.models.ReponseEntity;
import fr.uga.l3miage.example.models.ParticipantEntity;
import fr.uga.l3miage.example.repository.MiahootRepository;
import fr.uga.l3miage.example.repository.ReponseRepository;
import fr.uga.l3miage.example.repository.ParticipantRepository;
import fr.uga.l3miage.example.response.ParticipantDto;
import lombok.RequiredArgsConstructor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ParticipantComponent {

    private final ParticipantRepository participantRepository;
    private final MiahootRepository miahootRepository;
    private final ParticipantMapper participantMapper;


    public ParticipantEntity getParticipant(final Long id) throws EntityNotFoundException {
        Optional<ParticipantEntity> parOpt = participantRepository.findById(id);
        if (parOpt.isPresent()) {
            return parOpt.get();
        } else {
            throw new EntityNotFoundException(String.format("Aucune Participant n'a été trouvé pour l'id°[%d] : impossible de récupérer", id), id);
        }

    }

    public List<ParticipantEntity> getAllParticipants(){
            return participantRepository.findAll();
    }

    public Long createParticipant(final Long miahootId, final ParticipantEntity participant) throws AlreadyExistException, EntityNotFoundException{
        if (miahootRepository.findById(miahootId).isPresent()) {
            if (Objects.equals(participant.getId(), null)){
                miahootRepository.findById(miahootId).get().getParticipants().add(participant);
                return participantRepository.save(participant).getId();
            }
            else{
                if (participantRepository.findById(participant.getId()).isPresent()){
                    throw new AlreadyExistException(String.format("La Participant n°[%d] existe déjà en BD.", participant.getId()), participant.getId());
                }
                else{
                    return participantRepository.save(participant).getId();
                }
            }
        }
        else{
            throw new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de récupérer", miahootId), miahootId);
        }
    }

    public void updateParticipant(final Long idParToModify, final ParticipantDto participant) throws EntityNotFoundException{
        if (idParToModify == participant.getId()) {
            Optional<ParticipantEntity> parOpt = participantRepository.findById(idParToModify);
            if (parOpt.isPresent()) {
                participantMapper.mergeParticipantEntity(parOpt.get(), participant);
                participantRepository.save(parOpt.get());
            } else {
                throw new EntityNotFoundException(String.format("Aucune Participant n'a été trouvée pour l'id°[%d] : impossible de modifier.", idParToModify), idParToModify);
            }
        } //else throw new NotTheSameIdException(String.format("L'id de la Participant remplaçante([%d]) est différent de l'id de la Participant à remplacer([%lu])", Participant.getId(), idQuesToModify), Participant.getId(), idQuesToModify);

    }


    public void deleteParticipant(final Long id) throws EntityNotFoundException {
        Optional<ParticipantEntity> parOpt = participantRepository.findById(id);
        if (parOpt.isPresent()) {
            participantRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(String.format("Aucune réponse n'a été trouvée pour l'id°[%d] : impossible de supprimer.", id), id);
        }
    }


}
