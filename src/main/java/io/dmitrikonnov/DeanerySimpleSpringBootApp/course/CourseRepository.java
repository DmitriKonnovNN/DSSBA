package io.dmitrikonnov.DeanerySimpleSpringBootApp.course;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.course.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CourseRepository extends JpaRepository<CourseEntity,Long>{


    Optional<CourseEntity> findCourseEntityByName(String name);
    List<CourseEntity> findAllByName(String name);
    List<CourseEntity> findAllByDurationInUnities (Integer durationUnities);
    List<CourseEntity> findAllByTeachingMaterialContainsOrderByName (String teachingMaterial);
    List<CourseEntity> findCourseEntitiesByTopicId (Long topicId);
    List<CourseEntity> findCourseEntitiesByTopicName (String topicName);
}
