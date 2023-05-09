package fr.uga.l3miage.example.repository;

import fr.uga.l3miage.example.models.MiahootEntity;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MiahootRepository extends JpaRepository<MiahootEntity, Long> {
    @Query("SELECT m FROM MiahootEntity m WHERE m.nom LIKE %:nom%")
    Collection<MiahootEntity> findByName(String nom);
    
    @Query("SELECT m FROM MiahootEntity m WHERE m.creator.uid = :creatorUId")
    Collection<MiahootEntity> findByCreatorId(@Param("creatorUId") String creatorUId);

    //ATTENTION !
    /* Les fichiers repository sont vides car les fonctions CRUD sont déjà fournies par
    JpaRepository, qui hérite lui même de CRUDRepository. (getById, deleteById...)
    Ici, on doit définir des fonctions en plus dont on aurait besoin, par exemple getByDescription
     */
}
