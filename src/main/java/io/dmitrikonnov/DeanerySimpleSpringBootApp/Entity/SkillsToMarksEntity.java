package io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@Entity
public class SkillsToMarksEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable (name = "skill_mark_mapping")
    @MapKeyColumn (name = "skill")
    @Column (name = "mark")
    private Map <String, String> SkillsToMarks;

}
