package fr.uga.l3miage.example.repository;

import fr.uga.l3miage.example.models.CreatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRepository extends JpaRepository<CreatorEntity, Long> {

}
