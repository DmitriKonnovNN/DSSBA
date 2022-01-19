package io.dmitrikonnov.DeanerySimpleSpringBootApp.Repositories;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public interface ExamRepository extends JpaRepository<ExamEntity,String> {

    List<ExamEntity> findExamEntitiesByExamNameOrderByExamNameAsc (String name);
}
