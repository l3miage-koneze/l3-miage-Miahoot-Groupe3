package fr.uga.l3miage.example.models;

import lombok.*;

import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MiahootEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nom;

    @ManyToOne
@JoinColumn(name = "creatorId")
private CreatorEntity creator;


    @OneToMany()
    private Collection<ParticipantEntity> participants;

    @OneToMany()
    private Collection<QuestionEntity> questions;

    public Collection<QuestionEntity> getQuestions(){
        return questions;
    }

    public Collection<ParticipantEntity> getParticipants(){
        return participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        TestEntity that = (TestEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
