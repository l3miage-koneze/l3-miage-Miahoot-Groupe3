package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.MiahootEndpoint;

import fr.uga.l3miage.example.response.MiahootDto;
import fr.uga.l3miage.example.service.MiahootService;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
    public List<MiahootDto> getAllMiahoots() {
            return miahootService.getAllMiahoots();
    
    }
    
    @Override
    public void newMiahoot(MiahootDto miahootDto) throws Exception {
        miahootService.createMiahoot(miahootDto);
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
    public List<MiahootDto> findByName(String nom) {
        return miahootService.findByName(nom);
    }
}
