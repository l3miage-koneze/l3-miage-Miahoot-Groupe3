package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.CreatorEndpoint;

import fr.uga.l3miage.example.response.CreatorDto;
import fr.uga.l3miage.example.service.CreatorService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreatorController implements CreatorEndpoint{
    private final CreatorService creatorService;

    @Override
    public CreatorDto getCreator(String uid){
        return creatorService.getCreator(uid);
    }

    @Override
    public String newCreator(CreatorDto creatorDto) throws Exception {
        return creatorService.createCreator(creatorDto);
    }

    @Override
    public void updateCreator(String uid, CreatorDto creatorDto) {
        creatorService.updateCreator(uid, creatorDto);
    }

    @Override
    public void deleteCreator(String uid) {
        creatorService.deleteCreator(uid);
    }

    @Override
    public ResponseEntity<?> createCreatorGoogle(CreatorDto creatorDto) {
        return creatorService.createCreatorGoogle(creatorDto);
    }

    @Override
    public ResponseEntity<Boolean> checkIfCreatorExists(String uid) {
        return ResponseEntity.ok(creatorService.checkIfCreatorExists(uid));
    }
    
}
