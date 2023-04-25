package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.QuestionEndpoint;
import fr.uga.l3miage.example.exception.rest.IsInErrorRestException;
import fr.uga.l3miage.example.mapper.QuestionMapper;
import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.MiahootDto;
import fr.uga.l3miage.example.response.QuestionDto;
import fr.uga.l3miage.example.response.Test;
import fr.uga.l3miage.example.service.QuestionService;
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
public class QuestionController implements QuestionEndpoint {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @PostMapping(value = "/Question")
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDto newQuestion(@RequestBody @Valid QuestionDto questionDto){
        //QuestionEntity QuestionEntity = QuestionMapper.toQuestionEntity(QuestionDto);
        if(questionDto == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , null);
        }
        try{
            QuestionEntity questionEntity = this.questionService.save(questionMapper.toQuestionEntity(questionDto));
            return questionMapper.toQuestionDto(questionEntity);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null , e);
        }
    }

    @GetMapping("/Question/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionDto getQuestion(@PathVariable("id") Long id){
        try{
            QuestionEntity questionEntity= this.questionService.get(id);
            return this.questionMapper.toQuestionDto(questionEntity);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null , e);
        }
    }


@GetMapping("/Question")
public Collection<QuestionDto> getAllQuestion() {
   return questionMapper.toQuestionDto(questionService.list());
}


@DeleteMapping("/Question/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteQuestion(@PathVariable("id") Long id) throws Exception{
    try{
        questionService.delete(id);
    }catch(Exception e){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Question was not found");
    }
}



    @PutMapping("Question/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionDto updateQuestion(@PathVariable("id") @NotNull Long id, @RequestBody @Valid QuestionDto questionDto){
        try {
            if (questionDto.id().equals(id)) {
                QuestionEntity questionEntity = (QuestionEntity) questionMapper.toQuestionEntity(questionDto);
                var updated = questionService.update(questionEntity);
                return questionMapper.toQuestionDto(updated);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null, e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, null, e);
        }
    }
    }
