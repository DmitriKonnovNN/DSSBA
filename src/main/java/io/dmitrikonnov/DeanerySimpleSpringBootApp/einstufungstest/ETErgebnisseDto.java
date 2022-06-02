package io.dmitrikonnov.DeanerySimpleSpringBootApp.einstufungstest;

import lombok.Data;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class ETErgebnisseDto {

    private  String id;

    private Integer aufgabenBogenHash;

    private List<String> richtigeLoesungenNachNiveau;


    private int A1richtig;
    private int A2richtig;
    private int B1richtig;
    private int B2richtig;
    private int C1richtig;
    private int C2richtig;

    private Boolean A1erreicht;
    private Boolean A2erreicht;
    private Boolean B1erreicht;
    private Boolean B2erreicht;
    private Boolean C1erreicht;
    private Boolean C2erreicht;

    private ETAufgabenNiveau maxErreichtesNiveau;
    private Integer ZahlRichtigerAntworten;

    private Map<Integer, Boolean> idZuRichtigkeitMap;

}
