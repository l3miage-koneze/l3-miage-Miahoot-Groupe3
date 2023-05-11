package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.MiahootEndpoint;

import fr.uga.l3miage.example.response.MiahootDto;
import fr.uga.l3miage.example.service.MiahootService;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MiahootController implements MiahootEndpoint{

    private final MiahootService miahootService;
    

    @Override
    public MiahootDto getMiahoot(Long id){
        return miahootService.getMiahoot(id);
    }
    

    @Override
    public Collection<MiahootDto> findByCreatorId(String creatorId){
        return miahootService.findByCreatorId(creatorId);
    }
    
    @Override
    public Long newMiahoot(String creatorId, MiahootDto miahootDto) throws Exception {
        return miahootService.createMiahoot(creatorId,miahootDto);
    }

    @Override
    public void updateMiahoot(Long id, MiahootDto miahootDto) {
        miahootService.updateMiahoot(id, miahootDto);
    }

    @Override
    public void deleteMiahoot(Long id) {
        miahootService.deleteMiahoot(id);
    }

    @Override
    public Collection<MiahootDto> findByName(String nom) {
        return miahootService.findByName(nom);
    }
}
