package io.dmitrikonnov.DeanerySimpleSpringBootApp.einstufungstest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Service checks the correctness of answers.
 * */

@Service
@AllArgsConstructor
public class EinstufungsTestAntwortenPruefer {

    private final ETErgebnisse ergebnisse;

    public ETErgebnisse checkBogen(EinstufungsTestAntwortBogenDto antwortBogen, EinstufungsTestAufgabenBogen cachedAufgabenBogen) {
        List<EinstufungsTestAufgabe> cachedAufgaben = cachedAufgabenBogen.getAufgabenListe();
        ergebnisse.setAufgabenBogenHash(cachedAufgabenBogen.getAufgabenBogenHash());

        var cachedBogenHash = cachedAufgabenBogen.getAufgabenBogenHash();
        var aufgabenHashZuAntwortMap = antwortBogen.getAufgabenHashZuAntwortMap();

        aufgabenHashZuAntwortMap.forEach((hashedId, list) -> {
            var antwId = cachedBogenHash - hashedId;
            cachedAufgaben.forEach(aufgabe -> {

                if (aufgabe.getAufgabeId().equals(antwId)){
                    Boolean correct = aufgabe.getLoesungen().equals(list);
                    ergebnisse.getIdZuRichtigkeitMap().put(antwId, correct);
                    if (correct){
                        ergebnisse.getRichtigeLoesungenNachNiveau().add(aufgabe.getAufgabenNiveau().toString());
                    }
                }
            });
        });

        return ergebnisse;
    }
}