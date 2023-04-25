package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.QuestionEndpoint;
import fr.uga.l3miage.example.exception.rest.IsInErrorRestException;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.Test;
import fr.uga.l3miage.example.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionController implements QuestionEndpoint {
    private final QuestionService questionService;
    @Override
    public ResponseEntity<String> getHelloWord(final boolean isInError) {
        try {
            return ResponseEntity.ok().body(questionService.helloWord(isInError));
        } catch (IsInErrorRestException ex) {
            return ResponseEntity.status(ex.getHttpStatus())
                    .body(String.format("Ici une erreur 500 est levée.\n\t le message des exceptions : \n\t\t%s", ex.getMessage()));
        }
    }

    @Override
    public Test getEntityTest(final String description) {

        return questionService.getTest(description);
    }

    @Override
    public void createEntityTest(final CreateTestRequest request) {

        questionService.createTest(request);
    }

    @Override
    public void updateTestEntity(final String lastDescription,final Test test) {
        questionService.updateTest(lastDescription,test);
    }

    @Override
    public void deleteTestEntity(final String description) {

        questionService.deleteTest(description);
    }



}
