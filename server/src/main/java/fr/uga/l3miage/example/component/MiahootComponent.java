package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.MiahootMapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.repository.MiahootRepository;
import fr.uga.l3miage.example.response.MiahootDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MiahootComponent {

    private final MiahootRepository miahootRepository;
    private final MiahootMapper miahootMapper;


    public MiahootEntity getMiahoot(final Long id) throws EntityNotFoundException {
        Optional<MiahootEntity> miaOpt = miahootRepository.findById(id);
        if (miaOpt.isPresent()){
            return miaOpt.get();
        }
        else{
            throw new EntityNotFoundException(String.format("Aucune entité n'a été trouvée pour la description [%s]", description), description));
        }
    }

    public void createMiahoot(final TestEntity entity) throws IsNotTestException, DescriptionAlreadyExistException {
        if (Boolean.TRUE.equals(entity.getIsTest())) {
            if (testRepository.findByDescription(entity.getDescription()).isPresent()) {
                throw new DescriptionAlreadyExistException(String.format("La description %s existe déjà en BD.", entity.getDescription()), entity.getDescription());
            }
            testRepository.save(entity);
        } else throw new IsNotTestException("Le champs isTest n'est pas à true, donc erreur technique levée", entity);
    }

    public void updateMiahoot(final String lastDescription, final Test test) throws TestEntityNotFoundException, IsNotTestException, DescriptionAlreadyExistException {
        if (Boolean.TRUE.equals(test.getIsTest())) {
            if (!lastDescription.equals(test.getDescription()) && testRepository.findByDescription(test.getDescription()).isPresent()) {
                throw new DescriptionAlreadyExistException(String.format("La description %s existe déjà en BD.", test.getDescription()), test.getDescription());
            }
            TestEntity actualEntity = testRepository.findByDescription(lastDescription)
                    .orElseThrow(() -> new TestEntityNotFoundException(String.format("Aucune entité n'a été trouvé pour la description [%s]", lastDescription), lastDescription));
            testMapper.mergeTestEntity(actualEntity, test);
            testRepository.save(actualEntity);
        } else throw new IsNotTestException("Le champs isTest n'est pas à true, donc erreur technique levée", null);
    }


    public void deleteTest(final String description) throws MultipleEntityHaveSameDescriptionException, TestEntityNotFoundException {
        int deleted = testRepository.deleteByDescription(description);
        if (deleted > 1)
            throw new MultipleEntityHaveSameDescriptionException("Plusieurs entités ont la même description alors que c'est impossible niveau métier !!");
        else if (deleted == 0)
            throw new TestEntityNotFoundException("L'entité à supprimer n'a pas été trouvée", description);

    }
}
