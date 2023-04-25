package fr.uga.l3miage.example.models;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    
    @Column
    private String label;

    @OneToMany
    private Reponse[] reponses;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TestEntity that = (TestEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

