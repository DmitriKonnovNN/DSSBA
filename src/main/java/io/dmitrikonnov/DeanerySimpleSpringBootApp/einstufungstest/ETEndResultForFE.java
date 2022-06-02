package io.dmitrikonnov.DeanerySimpleSpringBootApp.einstufungstest;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public class ETEndResultForFE {
    private final String id;

    private final ETAufgabenNiveau erreichtesNiveau;

    private final Integer zahlRichtigerAntworten;
}
