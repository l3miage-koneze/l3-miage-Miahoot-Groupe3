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
import java.util.stream.Collectors;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class ReponseController implements ReponseEndpoint {

    private final ReponseService reponseService;
    private final ReponseMapper reponseMapper;
/* 
    @Autowired
    public ReponseController(ReponseService reponseService, ReponseMapper reponseMapper){
        this.reponseService = reponseService;
        this.reponseMapper = reponseMapper;
    }
*/
@GetMapping("/reponse")
public Collection<ReponseDto> reponse(@RequestParam(value = "q", required = false) String query) {
    Collection<ReponseDto> reponses;
    if (query == null) {
        reponses = reponseService.list();
    } else {
        reponses = reponseService.searchByName(query);
    }
    return reponses.stream().map(reponseMapper::toReponseDto).collect(Collectors.toList());
}

    @GetMapping("/reponse/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReponseDto reponse(@PathVariable("id") Long id){
        try{
            ReponseEntity reponse= this.reponseService.get(id);
            return this.reponseMapper.toReponseDto(reponse);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null , e);
        }
    }



    @PostMapping("/reponse")
    @ResponseStatus(HttpStatus.CREATED)
    public ReponseDto newReponse(@RequestBody @Valid ReponseDto reponseDto){
        ReponseEntity reponseEntity = reponseMapper.toReponseEntity(reponseDto);
        if(reponseEntity == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , null);
        }
        try{
            reponseEntity = this.reponseService.save(reponseEntity);
            return reponseMapper.toReponseDto(reponseEntity);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null , e);
        }
    }

    @PutMapping("reponse/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReponseDto updateMiahoot(@RequestBody ReponseDto reponseDto, @PathVariable("id") Long id){
        if(id != reponseDto.id()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        try{
            ReponseEntity updated = this.reponseService.update(this.reponseMapper.toReponseEntity(reponseDto));
            return this.reponseMapper.toReponseDto(updated);
        } catch( EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/reponse/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReponse(@PathVariable("id") Long id){
        try{
            reponseService.delete(id);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Reponse was not found");
        }
    }
}