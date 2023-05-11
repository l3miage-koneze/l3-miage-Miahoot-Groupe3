package fr.uga.l3miage.example.service;

import fr.uga.l3miage.example.component.ReponseComponent;
import fr.uga.l3miage.example.exception.rest.*;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.ReponseMapper;
import fr.uga.l3miage.example.models.ReponseEntity;
import fr.uga.l3miage.example.response.ReponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ReponseService {
    private static final String ERROR_DETECTED = "Une erreur lors de la création de l'entité ReponseConfigWithProperties à été détecté.";
    private final ReponseComponent reponseComponent;
    private final ReponseMapper reponseMapper;

    public ReponseDto getReponse(final Long id) {
        try {
            return reponseMapper.toReponseDto(reponseComponent.getReponse(id));
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(
                    String.format("Aucune reponse n'a été trouvé pour l'id°[%lu] : impossible de récupérer", id), id);
        }
    }

    public List<ReponseDto> getReponsesByQuestionId(Long questionId) {
        List<ReponseEntity> reponseEntities = reponseComponent.getReponsesByQuestionId(questionId);
        return reponseEntities.stream()
                .map(reponseMapper::toReponseDto)
                .collect(Collectors.toList());

    }

    public Long createReponse(final long questionId, final ReponseDto reponseDto) {
        ReponseEntity newReponseEntity = reponseMapper.toReponseEntity(reponseDto);
        try {
            return reponseComponent.createReponse(questionId, newReponseEntity);
        } catch (AlreadyExistException ex) {
            throw new AlreadyUseRestException(ERROR_DETECTED, reponseDto.getId(), ex);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(ERROR_DETECTED, reponseDto.getId(), ex);
        }
    }

    public void updateReponse(final Long idRepoToModify, final ReponseDto reponse) {
        if (idRepoToModify == reponse.getId()) {
            try {
                reponseComponent.updateReponse(idRepoToModify, reponse);
            } catch (EntityNotFoundException ex) {
                throw new EntityNotFoundRestException(String
                        .format("Aucune reponse n'a  été trouvé pour l'Id : Impossible de modifier", idRepoToModify),
                        idRepoToModify);
            }
        } else {
            throw new NotTheSameIdRestException(String.format(
                    "L'id de la reponse remplaçante([%d]) est différent de l'id de la reponse à remplacer([%d])",
                    reponse.getId(), idRepoToModify), reponse.getId(), idRepoToModify);
        }

    }

    @Transactional
    public void deleteReponse(final Long id) {
        try {
            reponseComponent.deleteReponse(id);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundRestException(
                    String.format("Aucune reponse n'a été trouvé pour l'id°[%lu] : impossible de supprimer.", id), id);
        }
    }

}
