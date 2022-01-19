package io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.topic.TopicEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@EqualsAndHashCode(callSuper = true)
@Table(name = "exam")
public class ExamEntity extends AppointmentAbstractEntity {

    @ManyToOne
    @JoinColumn (name = "TOPIC_ID")
    @JsonIgnoreProperties ("exam")
    private TopicEntity subjectToExamination;

    @Column (length = 100)
    private String examType;

    @Column (length = 100)
    private String examName;


    @JsonIgnoreProperties(value = "employee")
    @ManyToMany (cascade = CascadeType.MERGE)
    @JoinTable (name = "exam_examiner",
            joinColumns = {@JoinColumn(name="fk_exam")},
            inverseJoinColumns = {@JoinColumn(name="fk_employee")}
    )
    private Set <EmployeeEntity> examiners;

    @JsonIgnoreProperties(value = "employee")
    @ManyToMany (cascade = CascadeType.MERGE)
    @JoinTable (name = "exam_examsupervisor",
    joinColumns = {@JoinColumn(name="fk_exam")},
    inverseJoinColumns = {@JoinColumn(name="fk_employee")})
    private Set <EmployeeEntity> examinationSupervisors;

    @OneToOne
    @JoinColumn (name = "RESULTS_ID")
    @JsonIgnoreProperties ("results")
    private ResultsOfExamEntity results;

}
