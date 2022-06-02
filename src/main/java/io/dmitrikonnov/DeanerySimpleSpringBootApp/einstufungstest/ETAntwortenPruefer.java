package io.dmitrikonnov.DeanerySimpleSpringBootApp.einstufungstest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Service checks the correctness of answers.
 * */

@Service
@AllArgsConstructor
public class ETAntwortenPruefer {

    private final ETErgebnisseDto ergebnisseDto;



    public ETErgebnisseDto checkBogen(ETAntwortBogenDto antwortBogen, ETAufgabenBogen cachedAufgabenBogen) {
        List<ETAufgabe> cachedAufgaben = cachedAufgabenBogen.getAufgabenListe();
        ergebnisseDto.setAufgabenBogenHash(cachedAufgabenBogen.getAufgabenBogenHash());

        var cachedBogenHash = cachedAufgabenBogen.getAufgabenBogenHash();
        var aufgabenHashZuAntwortMap = antwortBogen.getAufgabenHashZuAntwortMap();

        aufgabenHashZuAntwortMap.forEach((hashedId, list) -> {
            var antwId = cachedBogenHash - hashedId;
            cachedAufgaben.forEach(aufgabe -> {

                if (aufgabe.getAufgabeId().equals(antwId)){
                    Boolean correct = aufgabe.getLoesungen().equals(list);
                    ergebnisseDto.getIdZuRichtigkeitMap().put(antwId, correct);
                    if (correct){
                        ergebnisseDto.getRichtigeLoesungenNachNiveau().add(aufgabe.getAufgabenNiveau().toString());
                    }
                }
            });
        });

        ergebnisseDto.setZahlRichtigerAntworten(ergebnisseDto.getRichtigeLoesungenNachNiveau().size());

        return ergebnisseDto;
    }
}