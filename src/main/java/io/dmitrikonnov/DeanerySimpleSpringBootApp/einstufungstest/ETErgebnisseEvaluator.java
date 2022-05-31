package io.dmitrikonnov.DeanerySimpleSpringBootApp.einstufungstest;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ETErgebnisseEvaluator {
    public void evaluate(ETErgebnisse ergebnisse) {
        List<String> sortedList = ergebnisse.getRichtigeLoesungenNachNiveau()
                .stream()
                .sorted()
                .collect(Collectors.toList());
        //TODO: Bringe in Erfahrung,
        // i) nach welchem Algorythmus das Niveau eingeschätzt werden soll
        // ii) wie groß der Aufgaben-Pool ist
        // iii) u.v.a.m.

    }
}
