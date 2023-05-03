package fr.uga.l3miage.example.repository;

import fr.uga.l3miage.example.models.CreatorEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRepository extends JpaRepository<CreatorEntity, Long> {
    @Query("SELECT c FROM CreatorEntity c WHERE c.id LIKE %:id%")
    Optional<CreatorEntity> findById(String id);

    void deleteById(String id);
    boolean existsByUid(String uid);
}
