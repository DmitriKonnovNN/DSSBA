package io.dmitrikonnov.DeanerySimpleSpringBootApp.PersistenceService;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity.ExamEntity;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Repositories.ExamRepository;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final static String EXAM_NOT_FOUND_MGS = "exam with id %s not found";

    @Override
    public ExamEntity getExamById (String id) {
        return examRepository.findById(id).orElseThrow(()-> new NotFoundException(String.format(EXAM_NOT_FOUND_MGS,id)));
    }

    @Override
    public List<ExamEntity> getExamByName(String name) {
        return new ArrayList<>(examRepository.findExamEntitiesByExamNameOrderByExamNameAsc (name));
    }

    @Override
    public List<ExamEntity> getAll() {
        return new ArrayList<>(examRepository.findAll());
    }

    @Override
    public ExamEntity addExam(ExamEntity examEntity) {
        return examRepository.save(examEntity);
    }

    @Override
    public void deleteById(String id) {
        examRepository.deleteById(id);
    }

    @Override
    public ExamEntity update(ExamEntity examEntity) {
        return examRepository.save(examEntity);
    }


}
