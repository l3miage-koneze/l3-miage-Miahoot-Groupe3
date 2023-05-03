package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.QuestionEndpoint;
import fr.uga.l3miage.example.mapper.QuestionMapper;
import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.response.QuestionDto;
import fr.uga.l3miage.example.response.ReponseDto;
import fr.uga.l3miage.example.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController implements QuestionEndpoint {
    private final QuestionService questionService;
    @Override
    public QuestionDto getQuestion(Long id) {
        return questionService.getQuestion(id);
    }

    @Override
    public Long newQuestion(Long miahootId, QuestionDto questionDto) {
        return questionService.createQuestion(miahootId, questionDto);
    }


    @Override
    public List<QuestionDto> getAllQuestions(){
        return questionService.getAllQuestions();
    }
    @Override
    public void updateQuestion(Long id, QuestionDto questionDto) {
        questionService.updateQuestion(id, questionDto);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionService.deleteQuestion(id);
    }
}
