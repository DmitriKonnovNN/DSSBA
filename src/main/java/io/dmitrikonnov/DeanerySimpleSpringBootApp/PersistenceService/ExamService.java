package io.dmitrikonnov.DeanerySimpleSpringBootApp.PersistenceService;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity.ExamEntity;

import java.util.List;
import java.util.Set;


public interface ExamService {


    List<ExamEntity> getAll();
    ExamEntity getExamById(String id);
    List<ExamEntity> getExamByName (String name);
    ExamEntity addExam(ExamEntity examEntity);
    void deleteById(String id);
    ExamEntity update (ExamEntity examEntity);



}
