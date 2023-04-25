package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.MiahootEndpoint;
import fr.uga.l3miage.example.exception.rest.IsInErrorRestException;
import fr.uga.l3miage.example.mapper.MiahootMapper;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.MiahootDto;
import fr.uga.l3miage.example.response.Test;
import fr.uga.l3miage.example.service.MiahootService;
import fr.uga.l3miage.example.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
//@RequestMapping(value = "", produces = "")
public class MiahootController implements MiahootEndpoint {

    private final MiahootService miahootService;
    private final MiahootMapper miahootMapper;
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @Autowired
    public MiahootController(MiahootService miahootService, MiahootMapper miahootMapper, QuestionMapper questionMapper, QuestionService questionService){
       this.miahootService = miahootService;
       this.miahootMapper = miahootMapper;
       this.questionMapper = questionMapper;
       this.questionService = questionService;
    }

    @GetMapping("/miahoot")
    public Collection<MiahootDTO> miahoot (@RequestParam(value = "q", required = false) String query) {
        Collection<Miahoot> miahoots;
        if (query == null) {
            miahoots = miahootService.list();
        } else {
            miahoots = miahootService.searchByName(query);
        }
        return miahoots.stream().map(miahootMapper::entityToDTO).toList();
    }

    @GetMapping("/miahoot/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MiahootDTO miahoot(@PathVariable("id") Long id){
        try{
            Miahoot miahoot= this.miahootService.get(id);
            return this.miahootMapper.entityToDTO(miahoot);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null , e);
        }
    }

    @PostMapping("/miahoot")
    @ResponseStatus(HttpStatus.CREATED)
    public MiahootDTO newMiahoot(@RequestBody @Valid MiahootDTO miahoot){
        miahoot = miahootMapper.dtoToEntity(miahoot);
        if(miahoot == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , null);
        }
        try{
            miahoot = this.miahootService.save(miahoot);
            return miahootMapper.entityToDTO(miahoot);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null , e);
        }
    }

    @PutMapping("miahoot/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MiahootDto updateMiahoot(@RequestBody MiahootDTO miahoot, @PathVariable("id") Long id){
        if(id != miahoot.id()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        try{
            Miahoot updated = this.miahootService.update(this.miahootMapper.dtoToEntity(miahoot));
            return this.miahootMapper.entityDTO(updated);
        } catch( EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/miahoot/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMiahoot(@PathVariable("id") Long id){
        try{
            miahootService.delete(id);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Miahoot was not found");
        }
    }

    @GetMapping("/miahoot/{id}/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDTO newQuestion(@PathVariable("id") Long miahootId, @RequestBody @Valid questionDTO question){

        Question question1 = questionMapper.dtoToEntity(question);
        try{
            Question quest = this.questionService.save(miahootId, question1);
            return questionMapper.entityToDTO(quest);
        } catch( Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The miahoot was not found");
        }
    }

}
