package io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity(name = "ForeignKeyAssoAccountEntity")
@Table(name = "ACCOUNT", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID"),
        @UniqueConstraint(columnNames = "ACC_NUMBER")})
public class AccountEntity implements Serializable {


    @Id
    @SequenceGenerator(name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "account_sequence")
    @Column(name = "ID", unique = true, nullable = false)
    private Integer accountId;

    @Column(name = "ACC_NUMBER", unique = true, nullable = false, length = 100)
    private String accountNumber;

    @ManyToOne
    @JsonIgnoreProperties ("accounts")
    private EmployeeEntity employee;

    @Temporal(TemporalType.TIMESTAMP)
    @Column (updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column (insertable = false, updatable = false)
    @org.hibernate.annotations.Generated (GenerationTime.ALWAYS)
    private Date lastModified;
}
