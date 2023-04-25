package fr.uga.l3miage.example.service;

import fr.uga.l3miage.example.component.ExampleComponent;
import fr.uga.l3miage.example.component.ReponseComponent;
import fr.uga.l3miage.example.exception.rest.*;
import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.TestMapper;
import fr.uga.l3miage.example.models.TestEntity;
import fr.uga.l3miage.example.request.CreateTestRequest;
import fr.uga.l3miage.example.response.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ReponseService {

    private static final String ERROR_DETECTED = "Une erreur lors de la création de l'entité TestConfigWithProperties à été détecté.";
    private static ReponseComponent reponseComponent;
    private final TestMapper testMapper;

    public static String helloWord(final boolean isInError) {
        try {
            return reponseComponent.getHelloWord(isInError);
        } catch (IsInErrorException ex) {
            throw new IsInErrorRestException("Une erreur à été demandée par le client, ici elle est catch par le service qui renvoie une rest exception et qui a comme cause l'exception technique", ex);
        }
    }

    public Test getTest(final String description) {
        try {
            return testMapper.toDto(reponseComponent.getTest(description));
        } catch (TestEntityNotFoundException ex) {
            throw new TestEntityNotFoundRestException(String.format("Impossible de charger l'entité. Raison : [%s]",ex.getMessage()),description,ex);
        }
    }

    public void createTest(final CreateTestRequest createTestRequest) {
        TestEntity newTestEntity = testMapper.toEntity(createTestRequest);
        if(newTestEntity.getTestInt()!=0){
            try {
                reponseComponent.createTest(newTestEntity);
            } catch (IsNotTestException ex) {
                throw new IsNotTestRestException(ERROR_DETECTED,createTestRequest,ex);
            } catch (DescriptionAlreadyExistException ex) {
                throw new DescriptionAlreadyUseRestException(ERROR_DETECTED,newTestEntity.getDescription(),ex);
            }
        }else{
            throw new TestIntIsZeroRestException("La somme des testInt ne doit pas être égale à zéro");
        }
    }


    public void updateTest(final String lastDescription,final Test test) {
        if (test.getTestInt() != 0) {
            try {
                reponseComponent.updateTest(lastDescription, test);
            } catch (TestEntityNotFoundException ex) {
                throw new TestEntityNotFoundRestException(String.format("Impossible de charger l'entité. Raison : [%s]",ex.getMessage()),lastDescription,ex);
            } catch (IsNotTestException ex) {
                throw new IsNotTestRestException("Une erreur lors de la mise à jour de l'entité TestConfigWithProperties a été détectée.",null,ex);
            } catch (DescriptionAlreadyExistException ex) {
                throw new DescriptionAlreadyUseRestException(ERROR_DETECTED,test.getDescription(),ex);
            }
        }else throw new TestIntIsZeroRestException("L'attribut testInt ne peut pas être égal à zéro");
    }

    @Transactional
    public void deleteTest(String description) {
        try {
            reponseComponent.deleteTest(description);
        } catch (MultipleEntityHaveSameDescriptionException | TestEntityNotFoundException ex) {
            throw new TestEntityNotDeletedRestException(ex.getMessage());
        }
    }
}
