package fr.uga.l3miage.example.models;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Personne {
    private String nom;
    private String prenom;
    // autres attributs et méthodes communs à Participant et Creator

    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

}
