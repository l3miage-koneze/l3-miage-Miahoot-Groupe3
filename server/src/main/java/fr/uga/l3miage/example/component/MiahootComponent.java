package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.MiahootMapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.repository.MiahootRepository;
import fr.uga.l3miage.example.repository.QuestionRepository;
import fr.uga.l3miage.example.response.MiahootDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

<<<<<<< HEAD

import java.util.Collection;
=======
import java.util.Collection;
import java.util.List;
>>>>>>> 40614e57daae8f6a8e56ac1790c41343dc74b5f0
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MiahootComponent {

    private final MiahootRepository miahootRepository;
    private final QuestionRepository questionRepository;
    private final MiahootMapper miahootMapper;


    public MiahootEntity getMiahoot(final Long id) throws EntityNotFoundException {
        Optional<MiahootEntity> miaOpt = miahootRepository.findById(id);
        if (miaOpt.isPresent()){
            return miaOpt.get();
        }
        else{
            throw new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de récupérer", id), id);
        }
    }

    public List<MiahootEntity> getAllMiahoots() {
        return miahootRepository.findAll();
    }

    public List<MiahootEntity> findByName(String name) {
        return miahootRepository.findByName(name);
        
    }

    public void createMiahoot(final MiahootEntity miahoot) throws AlreadyExistException {

        if (miahoot.getId() == null){
            miahootRepository.save(miahoot);
            System.out.println("Miahoot create");
        }
        else{
            if (miahootRepository.findById(miahoot.getId()).isPresent()){
                throw new AlreadyExistException(String.format("Le Miahoot n°[%d] existe déjà en BD.", miahoot.getId()), miahoot.getId());
            }
            else{
                miahootRepository.save(miahoot);
            }
        }

    }

    public void updateMiahoot(final Long idMiaToModify, final MiahootDto miahoot) throws EntityNotFoundException{
        if (idMiaToModify == miahoot.getId()) {
            Optional<MiahootEntity> miaOpt = miahootRepository.findById(idMiaToModify);
            if (miaOpt.isPresent()){
                miahootMapper.mergeMiahootEntity(miaOpt.get(), miahoot);
                miahootRepository.save(miaOpt.get());
            }
            else{
                throw new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de modifier.", idMiaToModify), idMiaToModify);
            }
        } //else throw new NotTheSameIdException(String.format("L'id du Miahoot remplaçant([%lu]) est différent de l'id du Miahoot à remplacer([%lu])", miahoot.getId(), idMiaToModify), miahoot.getId(), idMiaToModify);
    }


    public void deleteMiahoot(final Long id) throws EntityNotFoundException {
        Optional<MiahootEntity> miaOpt = miahootRepository.findById(id);
        if (miaOpt.isPresent()) {
            miahootRepository.deleteById(id);
            for (QuestionEntity question : miaOpt.get().getQuestions()) {
                questionRepository.deleteById(question.getId());
            }
        } else {
            throw new EntityNotFoundException(String.format("Aucun Miahoot n'a été trouvé pour l'id°[%d] : impossible de supprimer.", id), id);
        }
    }


    public Collection<QuestionEntity> getQuestionsMiahoot (final Long id) throws EntityNotFoundException{
        Optional<MiahootEntity> miaOpt = miahootRepository.findById(id);
        if(miaOpt.isPresent()){
            return miaOpt.get().getQuestions();
        } else {
            throw new EntityNotFoundException(String.format("Pas de Miahoot de avec cet id trouvé donc pas de questions", id), id);
        }
    }

}
