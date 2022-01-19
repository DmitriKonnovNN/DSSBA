package io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Getter
@Setter
@Entity
@Table (name = "results_of_exam")
public class ResultsOfExamEntity extends BasicEntity {

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "results")
    @JsonIgnoreProperties ("results")
    private ExamEntity exam;

    @OneToMany (cascade = CascadeType.ALL)
    @JoinTable (name = "person_results_mapping",
    joinColumns = {@JoinColumn (name = "results_of_exam_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn (name = "skills_makrs_id", referencedColumnName ="id")})
    @MapKeyJoinColumn (name = "employee_id", referencedColumnName = "id")
    private Map<EmployeeEntity, SkillsToMarksEntity> resultsPersonToSkillToMark;
//TODO: Try to replace EmployeeEntity as a key by int-value of its id
}
