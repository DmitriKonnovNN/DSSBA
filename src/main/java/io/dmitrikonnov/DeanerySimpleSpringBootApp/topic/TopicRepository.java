package io.dmitrikonnov.DeanerySimpleSpringBootApp.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TopicRepository extends JpaRepository<TopicEntity, Long> {



    Page<TopicEntity> findTopicEntitiesByName (String name, Pageable page);
    List<TopicEntity> findTopicEntitiesByName(String name);
    List<TopicEntity> findTopicEntitiesByDescriptionContaining (String name);
    List <TopicEntity> findTopicEntitiesByCoursesId (Long Id);
    List <TopicEntity> findTopicEntitiesByCoursesName (String courseName);
}
