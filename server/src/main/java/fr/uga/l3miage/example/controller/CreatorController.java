package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.CreatorEndpoint;

import fr.uga.l3miage.example.response.CreatorDto;
import fr.uga.l3miage.example.service.CreatorService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreatorController implements CreatorEndpoint{
    private final CreatorService creatorService;

    @Override
    public CreatorDto getCreator(String id){
        return creatorService.getCreator(id);
    }

    @Override
    public String newCreator(CreatorDto creatorDto) throws Exception {
        return creatorService.createCreator(creatorDto);
    }

    @Override
    public void updateCreator(String id, CreatorDto creatorDto) {
        creatorService.updateCreator(id, creatorDto);
    }

    @Override
    public void deleteCreator(String id) {
        creatorService.deleteCreator(id);
    }
}
