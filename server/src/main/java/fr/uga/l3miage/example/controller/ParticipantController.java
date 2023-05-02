package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.ParticipantEndpoint;
import fr.uga.l3miage.example.mapper.ParticipantMapper;
import fr.uga.l3miage.example.models.ParticipantEntity;
import fr.uga.l3miage.example.response.ParticipantDto;
import fr.uga.l3miage.example.response.ReponseDto;
import fr.uga.l3miage.example.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParticipantController implements ParticipantEndpoint {
    private final ParticipantService participantService;
    @Override
    public ParticipantDto getParticipant(Long id) {
        return participantService.getParticipant(id);
    }

    @Override
    public Long newParticipant(Long creatorId,Long miahootId, ParticipantDto ParticipantDto) {
        return participantService.createParticipant(miahootId, ParticipantDto);
    }


    @Override
    public List<ParticipantDto> getAllParticipants(Long id){
        return participantService.getALlParticipants();
    }
    @Override
    public void updateParticipant(Long id, ParticipantDto ParticipantDto) {
        participantService.updateParticipant(id, ParticipantDto);
    }

    @Override
    public void deleteParticipant(Long id) {
        participantService.deleteParticipant(id);
    }
}
