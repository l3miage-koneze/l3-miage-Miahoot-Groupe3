package fr.uga.l3miage.example.component;

import fr.uga.l3miage.example.exception.technical.*;
import fr.uga.l3miage.example.mapper.MiahootMapper;
import fr.uga.l3miage.example.models.MiahootEntity;
import fr.uga.l3miage.example.repository.MiahootRepository;
import fr.uga.l3miage.example.repository.QuestionRepository;
import fr.uga.l3miage.example.response.MiahootDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class MiahootComponentTest {

    /*
    @Autowired
    private MiahootRepository miahootRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private MiahootComponent miahootComponent;
    @Autowired
    private MiahootMapper miahootMapper;

    @AfterEach
    void clear() {
        questionRepository.deleteAll();
        miahootRepository.deleteAll();
    }

    @Test
    void getMiahoot() throws EntityNotFoundException {
        MiahootEntity miahootEntity = MiahootEntity
        .builder()
        .id(1L)
        .nom("Test Miahoot").build();
        miahootRepository.save(miahootEntity);

        assertThrows(EntityNotFoundException.class,()->miahootComponent.getMiahoot(0l));

        MiahootEntity miahootEntityReponse = miahootComponent.getMiahoot(1l);

        assertThat(miahootEntityReponse).usingRecursiveComparison().isEqualTo(miahootEntity);
    }

    @Test
    void createMiahoot() throws IsNotTestException,AlreadyExistException{
        MiahootEntity miahootEntity = MiahootEntity
        .builder()
        .id(2l)
        .nom("Test M2")
        .build();

        assertThrows(IsNotTestException.class,() -> miahootComponent.createMiahoot(miahootEntity));

        miahootEntity.setNom("M3");

        assertThat(miahootRepository.count()).isOne();

        assertThrows(AlreadyExistException.class, ()->miahootComponent.createMiahoot(miahootEntity));
    }

    @Test
    void updateMiahoot() throws IsNotTestException, AlreadyExistException, EntityNotFoundException{
        MiahootEntity miahootEntity = MiahootEntity
        .builder()
        .id(3l)
        .nom("M3")
        .build();

        MiahootEntity miahootEntity1 = MiahootEntity
        .builder()
        .id(4l)
        .nom("M4")
        .build();

        miahootRepository.save(miahootEntity);
        miahootRepository.save(miahootEntity1);

        MiahootDto miahootDto = MiahootDto
        .builder()
        .id(5l)
        .nom("M5")
        .build();

        assertThrows(IsNotTestException.class, ()->miahootComponent.updateMiahoot(miahootDto));
        miahootDto.setNom("M55");
        miahootDto.setDescription("D1");
        assertThrows(AlreadyExistException.class, () -> miahootComponent.updateMiahoot(miahootDto));
        miahootDto.setDescription("une description qui n'existe pas");
        assertThrows(EntityNotFoundException.class, () -> miahootComponent.updateMiahoot("une description qui n'existe pas",miahootDto));
        test.setDescription("description");
    } */
}