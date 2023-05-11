package fr.uga.l3miage.example.controller;

import fr.uga.l3miage.example.endpoint.ReponseEndpoint;
import fr.uga.l3miage.example.response.ReponseDto;
import fr.uga.l3miage.example.service.ReponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class ReponseController implements ReponseEndpoint {
    private final ReponseService reponseService;

    @Override
    public ReponseDto getReponse(Long id) {
        return reponseService.getReponse(id);
    }

    @Override
    public Collection<ReponseDto> getReponsesByQuestionId(Long questionId){
        return reponseService.getReponsesByQuestionId(questionId);
    }

    @Override
    public Long newReponse(Long questionId, ReponseDto reponseDto) {
        return reponseService.createReponse(questionId, reponseDto);
    }

    @Override
    public void updateReponse(Long id, ReponseDto reponseDto) {
        reponseService.updateReponse( id, reponseDto);
    }

    @Override
    public void deleteReponse(Long id) {
        reponseService.deleteReponse(id);
    }
}
