package io.dmitrikonnov.DeanerySimpleSpringBootApp.einstufungstest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Aufgabe des Aufsetzers ist es, Aufgaben aus dem Pool für alle Niveaus und Fertigkeiten
 * im erforderlichen Umfang teilweise randomisiert zu wählen. Somit wird der Ursprungsaufgabenbogen aufgesetzt, der
 * anschließend zweimal reshuffelt wird.*/
@Service
@AllArgsConstructor
public class EinstufungsTestAufgabenAufsetzer {

    private final ETAufgabenRepo ETAufgabenRepo;
    private final EinstufungstestAufgabenReshuffler aufgabenReshuffler;
    private final EinstufungsTestAntwortenReshuffler antwortenReshuffler;
    private final ETAufgabenRestricter aufgabenRestricter;
    public List<EinstufungsTestAufgabe> listeAufsetzen() {

        //TODO: Refactor the code down below by using Java Streams;
        List<EinstufungsTestAufgabe> aufgesetzteList = new ArrayList<>();
        for (EinstufungsTestAufgabenTyp typ: EinstufungsTestAufgabenTyp.values()) {
            var aufgabenNotReshuffeld = ETAufgabenRepo.findAllByAufgabenNiveau(typ.name());
            var aufgabenReshuffeld = aufgabenReshuffler.reshuffle(aufgabenNotReshuffeld);
            var aufgabenReshuffeldAndRestricted = aufgabenRestricter.restrict(aufgabenReshuffeld);
            var aufgabenWithReshuffeldAntworten = antwortenReshuffler.reshuffleAntworten(aufgabenReshuffeldAndRestricted);
            aufgesetzteList.addAll(aufgabenWithReshuffeldAntworten);
        }
        return aufgesetzteList;
    }
}
