package io.dmitrikonnov.DeanerySimpleSpringBootApp.topic;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopicService {

    public void deleteTopicById (Long id);
    public void addTopic (TopicDto topicDto);
    public void updateTopic(TopicDto topicDto);
    public TopicDto getTopicById (Long id);
    public Page<TopicDto> getTopicsByName(String name, Pageable page);
    public List<TopicEntity> getAll();
    public List <TopicEntity> getAllByName(String name);
    public List <TopicEntity> getAllByDescription (String description);
    public List <TopicEntity> getAllByCourseId (Long courseId);
    public List <TopicEntity> getAllByCourseName (String courseName);
    public Page<TopicDto> getAllPageable(Pageable page);

    void addTopicTest(TopicEntity topicEntity);

}
