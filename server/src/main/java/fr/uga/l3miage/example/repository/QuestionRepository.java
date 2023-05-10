package fr.uga.l3miage.example.repository;

import fr.uga.l3miage.example.models.QuestionEntity;
import fr.uga.l3miage.example.models.MiahootEntity;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    //List<MiahootEntity> findByCreatorId(Long creatorId);
    @Query("SELECT q FROM QuestionEntity q WHERE q.miahoot.id = :miahootId")
    Collection<QuestionEntity> findQuestionsByMiahootId(Long miahootId);
    //ATTENTION !
    /* Les fichiers repository sont vides car les fonctions CRUD sont déjà fournies par
    JpaRepository, qui hérite lui même de CRUDRepository. (getById, deleteById...)
    Ici, on doit définir des fonctions en plus dont on aurait besoin, par exemple getByDescription
     */
}