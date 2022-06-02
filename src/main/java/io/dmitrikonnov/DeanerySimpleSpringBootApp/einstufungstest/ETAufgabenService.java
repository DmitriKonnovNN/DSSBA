package io.dmitrikonnov.DeanerySimpleSpringBootApp.einstufungstest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ETAufgabenService {

    private final ETAufgabenAufsetzer aufsetzer;
    private final ETAntwortenPruefer pruefer;
    private final ETErgebnisseEvaluator evaluator;
    private final ETErgebnisseConverterAndPersister converterAndPersister;


    public ETAufgabenBogen getAufgabenListe (){
        List<ETAufgabe> aufgesetzteListe = aufsetzer.listeAufsetzen();
        return ETAufgabenBogen.builder().aufgabenListe(aufgesetzteListe).build();
    }


    public ETEndResultForFE checkAntwortBogenAndGetTestErgebnisse (ETAntwortBogenDto antwortBogen, ETAufgabenBogen chachedAufgabenBogen){
        var ergebnisseDto = pruefer.checkBogen(antwortBogen,chachedAufgabenBogen);
        evaluator.evaluate(ergebnisseDto);
        converterAndPersister.convertAndPersist(ergebnisseDto);
        return ETEndResultForFE.builder()
                .id(ergebnisseDto.getId())
                .erreichtesNiveau(ergebnisseDto.getMaxErreichtesNiveau())
                .zahlRichtigerAntworten(ergebnisseDto.getZahlRichtigerAntworten())
                .build();
    }
}
