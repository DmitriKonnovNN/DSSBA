package io.dmitrikonnov.DeanerySimpleSpringBootApp.einstufungstest;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;


public interface ETAufgabenRepo extends CrudRepository<EinstufungsTestAufgabe, Long> {

    Set<EinstufungsTestAufgabe>findAllByAufgabenNiveau(String niveau);

}
