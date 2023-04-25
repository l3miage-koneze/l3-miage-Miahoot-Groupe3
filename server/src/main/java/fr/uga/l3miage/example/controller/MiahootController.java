package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.MiahootEndpoint;
import fr.uga.l3miage.example.exception.rest.IsInErrorRestException;
import fr.uga.l3miage.example.mapper.MiahootMapper;
import fr.uga.l3miage.example.mapper.QuestionMapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.request.CreateMiahootRequest;
import fr.uga.l3miage.example.response.MiahootDto;
import fr.uga.l3miage.example.response.QuestionDto;
import fr.uga.l3miage.example.response.ReponseDto;
import fr.uga.l3miage.example.service.MiahootService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.stream.Collectors;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class MiahootController implements MiahootEndpoint {

    private final MiahootService miahootService;
    private final MiahootMapper miahootMapper;
    private final QuestionMapper questionMapper;

    @PostMapping(value = "/Miahoots")
    @ResponseStatus(HttpStatus.CREATED)
    public MiahootDto newMiahoot(@RequestBody @Valid MiahootDto miahootDto){
        //MiahootEntity MiahootEntity = MiahootMapper.toMiahootEntity(MiahootDto);
        if(miahootDto == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , null);
        }
        try{
            MiahootEntity miahootEntity = this.miahootService.save(miahootMapper.toMiahootEntity(miahootDto));
            return miahootMapper.toMiahootDto(miahootEntity);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null , e);
        }
    }

    @GetMapping("/Miahoots/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MiahootDto getMiahoot(@PathVariable("id") Long id){
        try{
            MiahootEntity miahootEntity= this.miahootService.get(id);
            return this.miahootMapper.toMiahootDto(miahootEntity);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null , e);
        }
    }


@GetMapping("/Miahoots")
public Collection<MiahootDto> getAllMiahoots() {
   return miahootMapper.toMiahootDto(miahootService.list());
}

@GetMapping("/Miahoots")
public Collection<MiahootDto> getMiahootsByLabel(@RequestParam(value = "q", required = false) String query){
    Collection<MiahootEntity> miahootEntities;
    if (query == null) {
        miahootEntities = miahootService.list();
    } else {
        miahootEntities = miahootService.searchByName(query);
    }
    return miahootEntities.stream()
            .map(miahootMapper::toMiahootDto)
            .toList();
}

@DeleteMapping("/Miahoots/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteMiahoot(@PathVariable("id") Long id) throws Exception{
    try{
        miahootService.delete(id);
    }catch(Exception e){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Miahoot was not found");
    }
}



    @PutMapping("Miahoots/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MiahootDto updateMiahoot(@PathVariable("id") @NotNull Long id, @RequestBody @Valid MiahootDto miahootDto){
        try {
            if (miahootDto.id().equals(id)) {
                MiahootEntity miahootEntity = (MiahootEntity) miahootMapper.toMiahootEntity(miahootDto);
                var updated = MiahootService.update(miahootEntity);
                return miahootMapper.toMiahootDto(updated);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null, e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, null, e);
        }
    }

    @GetMapping("/Miahoots/{id}/Questions")
    public Collection<QuestionDto> getAllQuestions(@PathVariable("id") @NotNull Long miahootId) {
        try {
            var miahoot = MiahootService.get(miahootId);
            return questionMapper.toQuestionDto(miahoot.getQuestions());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null, e);
        }
    }
    }
