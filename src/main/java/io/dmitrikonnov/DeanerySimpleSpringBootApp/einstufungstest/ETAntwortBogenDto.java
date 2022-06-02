package io.dmitrikonnov.DeanerySimpleSpringBootApp.einstufungstest;

import lombok.Data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
/**
 * This class should be used as DTO only
 * */
@Data
public class ETAntwortBogenDto {

    private Long antwortBogenId;
    private Map<Integer, ArrayList<String>> aufgabenHashZuAntwortMap;

}
