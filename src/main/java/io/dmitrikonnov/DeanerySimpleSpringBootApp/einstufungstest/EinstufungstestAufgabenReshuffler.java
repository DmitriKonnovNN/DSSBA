package io.dmitrikonnov.DeanerySimpleSpringBootApp.einstufungstest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class EinstufungstestAufgabenReshuffler {
    public List<EinstufungsTestAufgabe> reshuffle(Set<EinstufungsTestAufgabe> aufgabenNotReshuffeld) {
        List<EinstufungsTestAufgabe> aufgaben = new ArrayList<>(aufgabenNotReshuffeld);
        Collections.shuffle(aufgaben);
        return aufgaben;
    }
}
