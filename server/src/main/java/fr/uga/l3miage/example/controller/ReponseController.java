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

    @Override
    public ReponseDto getReponse( Long id) {
        return reponseService.getReponse(id);
    }

    @Override
    public void newReponse(Long miahootId, Long questionId, ReponseDto reponseDto) {
        reponseService.createReponse(miahootId, questionId, reponseDto);
    }

    @Override
    public void updateReponse(Long id, ReponseDto reponseDto) {
        reponseService.updateReponse(id, reponseDto);
    }

    @Override
    public void deleteReponse(Long id) {
        reponseService.deleteReponse(id);
    }
}
