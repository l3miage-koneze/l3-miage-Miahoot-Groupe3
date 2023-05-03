package fr.uga.l3miage.example.repository;

import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.models.MiahootEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    //List<MiahootEntity> findByCreatorId(Long creatorId);

    List<QuestionEntity> findQuestionsByMiahootId(Long miahootId);
    //ATTENTION !
    /* Les fichiers repository sont vides car les fonctions CRUD sont déjà fournies par
    JpaRepository, qui hérite lui même de CRUDRepository. (getById, deleteById...)
    Ici, on doit définir des fonctions en plus dont on aurait besoin, par exemple getByDescription
     */
}