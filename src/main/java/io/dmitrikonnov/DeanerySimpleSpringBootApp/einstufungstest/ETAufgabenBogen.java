package io.dmitrikonnov.DeanerySimpleSpringBootApp.einstufungstest;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.List;

/**
 * Der Bogen wird einmal im BackEnd nach dem Reshuffle aufgesetzt. Hiervon werden einzelne Aufgaben abgerufen.
 * Die Reihenfolge der Aufgaben soll während der Session beibehalten werden. Dazu muss ein Cache implementiert sein.
 * Die Id wird dem Nutzer zuteil und abschließend in den Antwortbogen eingetragen.
 * */

@RequiredArgsConstructor
@Setter
@Getter
@Builder
public class ETAufgabenBogen {

    @SequenceGenerator(name = "et_aufgabenbogen_seq",
            sequenceName = "et_aufgabenbogen_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "et_aufgabenbogen_seq")
    private final Long aufgabenBogenId;
    private Integer aufgabenBogenHash;
    private final List<ETAufgabe> aufgabenListe;



}
