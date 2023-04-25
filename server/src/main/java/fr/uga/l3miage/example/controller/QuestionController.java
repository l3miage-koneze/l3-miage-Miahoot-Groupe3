package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.QuestionEndpoint;
import fr.uga.l3miage.example.exception.rest.IsInErrorRestException;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.MiahootDto;
import fr.uga.l3miage.example.response.QuestionDto;
import fr.uga.l3miage.example.response.Test;
import fr.uga.l3miage.example.service.QuestionService;
import fr.uga.l3miage.example.service.ReponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class QuestionController implements QuestionEndpoint {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final ReponseService reponseService;
    private final ReponseMapper reponsemapper;

    @Autowired
    public QuestionController(QuestionService questionService, QuestionMapper questionMapper, ReponseService reponseService, ReponseMapper reponsemapper){
        this.questionService = questionService;
        this.questionMapper = questionMapper;
        this.reponseService = reponseService;
        this.reponsemapper = reponsemapper;
    }


    @GetMapping("/question")
    public Collection<QuestionDto> question (@RequestParam( value = "q", required = false) String query) {
        Collection<Question> questions;
        if(query == null){
            questions = questionService.list();
        } else {
            questions = questionService.searchByName(query);
        }
        return questions.stream().map(questionMapper::entityToDTO).toList();
    }



    @GetMapping("/question/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionDto question(@PathVariable("id") Long id){
        try{
            Question question = this.questionService.get(id);
            return this.questionMapper.entityToDTO(question);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null, e);
        }
    }



    @PostMapping("/question")
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDTO newQuestion(@RequestBody @Valid QuestionDto question) {
        question = questionMapper.dtoToEntity(question);
        if(question == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, null);
        }
        try{
            question = this.questionService.save(question);
            return questionMapper.entityToDTO(question);
        } catch(Exception e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, null, e);
        }
    }

    @PutMapping("question/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MiahootDto updateQuestion(@RequestBody QuestionDTO question, @PathVariable("id") Long id){
        if(id != question.id()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        try{
            Question updated = this.questionService.update(this.questionMapper.dtoToEntity(question));
            return this.questionMapper.entityDTO(updated);
        } catch( EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/question/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@PathVariable("id") Long id){
        try{
            questionService.delete(id);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Question was not found");
        }
    }


    @GetMapping("/question/{id}/reponses")
    @ResponseStatus(HttpStatus.CREATED)
    public ReponseDTO newQuestion(@PathVariable("id") Long questionId, @RequestBody @Valid reponseDTO reponse){

        Reponse answers = questionMapper.dtoToEntity(reponse);
        try{
            Reponse repons = this.questionService.save(questionId, answers);
            return reponseMapper.entityToDTO(repons);
        } catch( Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The question was not found");
        }
    }

}
