package io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Appointment.ExamDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity(name = "ForeignKeyAssoEntity")
@Table(name = "Employee", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")})
public class EmployeeEntity extends PersonAbstractEntity implements Serializable {

       @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="EMPLOYEE_ID")
    @JsonIgnoreProperties ("employee")
    private Set<AccountEntity> accounts;


    @ManyToMany (mappedBy = "examiners")
    @JsonIgnoreProperties ("examiners")
    private Set<ExamEntity> examsToConduct;

    @ManyToMany (mappedBy = "examinationSupervisors")
    @JsonIgnoreProperties ("examinationSupervisors")
    private Set<ExamEntity> examsToSupervise;


}
