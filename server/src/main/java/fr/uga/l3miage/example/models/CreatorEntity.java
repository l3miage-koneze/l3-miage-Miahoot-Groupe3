package fr.uga.l3miage.example.models;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

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
public class CreatorEntity {
    /* 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String nom;
    @Column
    private String photo;

    @OneToMany(mappedBy = "creator")
    private Collection<MiahootEntity> miahoots;
    */
    @Id
    private String id;

    @Column
    private String nom;
    @Column
    private String photo;
    @Column
    private String uid;

    @OneToMany(mappedBy = "creator")
    private Collection<MiahootEntity> miahoots;

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }
 
}