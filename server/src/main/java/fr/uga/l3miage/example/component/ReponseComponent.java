package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.ReponseMapper;
import fr.uga.l3miage.example.models.ReponseEntity;
import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.repository.ReponseRepository;
import fr.uga.l3miage.example.response.ReponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReponseComponent {

    private final ReponseRepository reponseRepository;
    private final ReponseMapper reponseMapper;

    public ReponseEntity getReponse(final Long id) throws EntityNotFoundException {
        Optional<ReponseEntity> repOpt = reponseRepository.findById(id);
        if (repOpt.isPresent()){
            return repOpt.get();
        }
        else{
            throw new EntityNotFoundException(String.format("Aucune réponse n'a été trouvé pour l'id°[%lu] : impossible de récupérer", id), id);
        }
    }

    public void createReponse(final ReponseEntity reponse) throws AlreadyExistException {
        if (reponseRepository.findById(reponse.getId()).isPresent()){
            throw new AlreadyExistException(String.format("La réponse n°[%lu] existe déjà en BD.", reponse.getId()), reponse.getId());
        }
        else{
            reponseRepository.save(reponse);
        }
    }

    public void updateReponse(final Long idRepToModify, final ReponseDto reponse) throws EntityNotFoundException{
        if (idRepToModify == reponse.getId()) {
            Optional<ReponseEntity> repOpt = reponseRepository.findById(idRepToModify);
            if (repOpt.isPresent()){
                reponseMapper.mergeReponseEntity(repOpt.get(), reponse);
                reponseRepository.save(repOpt.get());
            }
            else{
                throw new EntityNotFoundException(String.format("Aucune réponse n'a été trouvée pour l'id°[%lu] : impossible de modifier.", idRepToModify), idRepToModify);
            }
        } //else throw new NotTheSameIdException(String.format("L'id de la question remplaçante([%lu]) est différent de l'id de la question à remplacer([%lu])", reponse.getId(), idRepToModify), reponse.getId(), idRepToModify);
    }


    public void deleteReponse(final Long id) throws EntityNotFoundException {
        Optional<ReponseEntity> repOpt = reponseRepository.findById(id);
        if (repOpt.isPresent()) {
            reponseRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(String.format("Aucune réponse n'a été trouvée pour l'id°[%lu] : impossible de supprimer.", id), id);
        }
    }

}
