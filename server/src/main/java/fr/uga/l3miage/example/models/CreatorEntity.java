package fr.uga.l3miage.example.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CreatorEntity extends Personne {
    @OneToMany()
    private Collection<MiahootEntity> miahoots;
 
}