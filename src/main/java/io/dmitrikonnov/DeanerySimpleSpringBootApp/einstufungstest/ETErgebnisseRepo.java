package io.dmitrikonnov.DeanerySimpleSpringBootApp.einstufungstest;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ETErgebnisseRepo extends CrudRepository<ETErgebnisse, UUID> {
}
