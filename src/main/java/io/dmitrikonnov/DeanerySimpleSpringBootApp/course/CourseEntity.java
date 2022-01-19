package io.dmitrikonnov.DeanerySimpleSpringBootApp.course;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.enums.LearningGroupType;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.topic.TopicEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@Table (name = "course")
public class CourseEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    @Column (name = "id", unique = true, nullable = false)
    private Long id;

    @NotNull
    private String name;
    private Integer durationInUnities;
    private String teachingMaterial;

    @Enumerated(EnumType.STRING)
    private LearningGroupType learningGroupType;


    @ManyToOne (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH}, fetch = FetchType.LAZY)
   @JoinColumn (name = "TOPIC_ID")
    //@JsonIgnoreProperties ("courses")
    private TopicEntity topic;

    /*@OneToOne (fetch = FetchType.LAZY, mappedBy = "course")
    @JsonIgnoreProperties ("course")
    private TopicEntity topic;*/

    @Temporal(TemporalType.TIMESTAMP)
    @Column (updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date createdOn;


    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastModified;

}
