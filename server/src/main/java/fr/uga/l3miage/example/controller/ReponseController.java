package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.ReponseEndpoint;
import fr.uga.l3miage.example.exception.rest.IsInErrorRestException;
import fr.uga.l3miage.example.mapper.ReponseMapper;
import fr.uga.l3miage.example.models.ReponseEntity;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.MiahootDto;
import fr.uga.l3miage.example.response.ReponseDto;
import fr.uga.l3miage.example.response.Test;
import fr.uga.l3miage.example.service.ReponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ReponseController implements ReponseEndpoint {

    private final ReponseService reponseService;
    private final ReponseMapper reponseMapper;

    @PostMapping(value = "/Reponses")
    @ResponseStatus(HttpStatus.CREATED)
    public ReponseDto newReponse(@RequestBody @Valid ReponseDto reponseDto){
        if(reponseDto == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , null);
        }
        try{
            ReponseEntity reponseEntity = this.reponseService.save(reponseMapper.toReponseEntity(reponseDto));
            return reponseMapper.toReponseDto(reponseEntity);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null , e);
        }
    }

    @GetMapping("/Reponses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReponseDto getReponse(@PathVariable("id") Long id){
        try{
            ReponseEntity reponseEntity= this.reponseService.get(id);
            return this.reponseMapper.toReponseDto(reponseEntity);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null , e);
        }
    }


@GetMapping("/Reponses")
public Collection<ReponseDto> getAllReponses() {
   return reponseMapper.toReponseDto(reponseService.list());
}

@GetMapping("/Reponses")
public Collection<ReponseDto> getReponsesByLabel(@RequestParam(value = "q", required = false) String query) {
    Collection<ReponseEntity> reponseEntities;
    if (query == null) {
        reponseEntities = reponseService.list();
    } else {
        reponseEntities = reponseService.searchByLabel(query);
    }
    return reponseEntities.stream()
            .map(reponseMapper::toReponseDto)
            .toList();
}


@DeleteMapping("/Reponses/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteReponse(@PathVariable("id") Long id) throws Exception{
    try{
        reponseService.delete(id);
    }catch(Exception e){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Reponse was not found");
    }
}



    @PutMapping("Reponses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReponseDto updateReponse(@PathVariable("id") @NotNull Long id, @RequestBody @Valid ReponseDto reponseDto){
        try {
            if (reponseDto.id().equals(id)) {
                ReponseEntity reponseEntity = (ReponseEntity) reponseMapper.toReponseEntity(reponseDto);
                var updated = reponseService.update(reponseEntity);
                return reponseMapper.toReponseDto(updated);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null, e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, null, e);
        }
    }
    }
