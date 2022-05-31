package io.dmitrikonnov.DeanerySimpleSpringBootApp.einstufungstest;

import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * this class should be persistent
 * */
@Entity
public class EinstufungsTestAufgabe {

    @Id
    @SequenceGenerator(name = "et_aufgabe_seq",
            sequenceName = "et_aufgabe_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "et_aufgabe_seq")
    private Integer aufgabeId;

    @ElementCollection
    @CollectionTable (name = "et_loesungen_set")
    private Set<String> loesungen;

    private String aufgabenStellung;

    /**
     * Either a link to media content or simple text.
     * */
    private String aufgabenInhalt;

    @ElementCollection
    @CollectionTable (name = "et_moegl_antw_set")
    private Set<String> moeglicheAntworten;

    @Enumerated(EnumType.STRING)
    private EinstufungsTestAufgabenTyp aufgabenTyp;

    @Enumerated(EnumType.STRING)
    private ETAufgabenNiveau aufgabenNiveau;

    private Integer gewichtung;

    private Long counter;
    private Long counterCorrectAnswers;

    @Temporal(TemporalType.TIMESTAMP)
    @Column (updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column (insertable = false, updatable = false)
    @org.hibernate.annotations.Generated (GenerationTime.ALWAYS)
    private Date lastModified;

}