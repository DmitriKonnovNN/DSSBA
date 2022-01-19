package io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Person.PersonDto;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@MappedSuperclass
public abstract class AppointmentAbstractEntity extends BasicEntity{


    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

   /* @JsonProperty(value = "group")
    private GroupDto group;*/


    @Column (name = "comment")
    private String comment;



    /*private Set <StudentsEntity> currentStudents;*/

   /* @JsonProperty (value = "place")
    private PlaceDto place;*/


    @Column (name = "equipment")
    private String equipment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column (name = "created_on", updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastModified;
}
