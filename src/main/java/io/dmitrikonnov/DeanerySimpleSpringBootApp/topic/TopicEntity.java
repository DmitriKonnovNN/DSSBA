package io.dmitrikonnov.DeanerySimpleSpringBootApp.topic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.course.CourseEntity;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity.ExamEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "topic")
public class TopicEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false/*, updatable = false*/)
    private Long id;

    private String name;
    private String description;


    @OneToMany (fetch = FetchType.LAZY,
            cascade = {
           CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH}, orphanRemoval = false, /*optional = false,*/ mappedBy = "topic")
    //@JsonIgnoreProperties ("topic")
    private Set<CourseEntity> courses;


    /**  JPA spec 3.5.2:
     *
     * In general, the lifecycle method of a portable application should not invoke EntityManager or query operations,
     * access other entity instances, or modify relationships within the
     * same persistence context[46].
     * [47] A lifecycle callback method may modify the non-relationship state of the entity on which it is invoked.
     */

    //TODO: Taking into consideration the spec 3.5.2 above: Refactor the deletion functionality
    // Add in the table:  ON DELETE SET NULL
    @PreRemove
    private void nullifyTopics (){
        courses.forEach(courseEntity -> courseEntity.setTopic(null));
    }






    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "subjectToExamination")
    @JsonIgnoreProperties ("subjectToExamination")
    private Set<ExamEntity> exam;

    @Temporal(TemporalType.TIMESTAMP)
    @Column (updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastModified;


}
