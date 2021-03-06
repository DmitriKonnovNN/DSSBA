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
public class ETAufgabenAufsetzer {

    private final ETAufgabenRepo ETAufgabenRepo;
    private final ETAufgabenReshuffler aufgabenReshuffler;
    private final ETAntwortenReshuffler antwortenReshuffler;
    private final ETAufgabenRestricter aufgabenRestricter;
    public List<ETAufgabe> listeAufsetzen() {

        //TODO: Refactor the code down below by using Java Streams;
        List<ETAufgabe> aufgesetzteList = new ArrayList<>();
        for (ETAufgabenTyp typ: ETAufgabenTyp.values()) {
            var aufgabenNotReshuffeld = ETAufgabenRepo.findAllByAufgabenNiveau(typ.name());
            var aufgabenReshuffeld = aufgabenReshuffler.reshuffle(aufgabenNotReshuffeld);
            var aufgabenReshuffeldAndRestricted = aufgabenRestricter.restrict(aufgabenReshuffeld);
            var aufgabenWithReshuffeldAntworten = antwortenReshuffler.reshuffleAntworten(aufgabenReshuffeldAndRestricted);
            aufgesetzteList.addAll(aufgabenWithReshuffeldAntworten);
        }
        return aufgesetzteList;
    }
}
