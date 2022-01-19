package io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Data
@MappedSuperclass
public abstract class PersonAbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer Id;

    @Column(name = "EMAIL", unique = false, nullable = true, length = 100)
    private String email;

    @Column(name = "firstName", unique = false, nullable = true, length = 100)
    private String firstName;

    @Column(name = "lastName", unique = false, nullable = true, length = 100)
    private String lastName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column (updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column (insertable = false, updatable = false)
    @org.hibernate.annotations.Generated (GenerationTime.ALWAYS)
    private Date lastModified;



}
