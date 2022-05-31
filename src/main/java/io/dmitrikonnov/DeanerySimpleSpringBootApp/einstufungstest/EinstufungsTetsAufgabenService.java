package io.dmitrikonnov.DeanerySimpleSpringBootApp.einstufungstest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EinstufungsTetsAufgabenService {

    private final EinstufungsTestAufgabenAufsetzer aufsetzer;
    private final EinstufungsTestAntwortenPruefer pruefer;
    private final ETErgebnisseRepo etErgebnisseRepo;

    public EinstufungsTestAufgabenBogen getAufgabenListe (){
        List<EinstufungsTestAufgabe> aufgesetzteListe = aufsetzer.listeAufsetzen();
        return EinstufungsTestAufgabenBogen.builder().aufgabenListe(aufgesetzteListe).build();
    }

    public void checkAntwortBogenAndGetTestErgebnisse (EinstufungsTestAntwortBogenDto antwortBogen,EinstufungsTestAufgabenBogen chachedAufgabenBogen){
        var ergebnisse = pruefer.checkBogen(antwortBogen,chachedAufgabenBogen);
        etErgebnisseRepo.save(ergebnisse);
    }
}
