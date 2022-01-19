package io.dmitrikonnov.DeanerySimpleSpringBootApp.topic;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.converter.CycleAvoidingMappingContext;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.converter.TopicMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final static TopicMapper mapper = TopicMapper.TOPIC_MAPPER;

    @Override
    public TopicDto getTopicById(Long id) {
        return mapper.entityToDto(topicRepository.findById(id).orElseThrow(), new CycleAvoidingMappingContext());
    }

    @Override
    public Page<TopicDto> getAllPageable(Pageable page) {

        return topicRepository.findAll(page)
                .map(topicEntity -> mapper.entityToDto(topicEntity, new CycleAvoidingMappingContext()));
    }

    @Override
    public void deleteTopicById(Long id) {
        topicRepository.deleteById(id);
    }

    @Override
    public void addTopic(TopicDto topicDto) {
        topicRepository.save(mapper.dtoToEntity(topicDto/*, new CycleAvoidingMappingContext()*/));
    }

    @Override
    public void addTopicTest(TopicEntity topicEntity) {
        topicRepository.save(topicEntity);
    }

    @Override
    public void updateTopic(TopicDto topicDto) {
        topicRepository.save(mapper.dtoToEntity(topicDto/*, new CycleAvoidingMappingContext()*/));
    }



    @Override
    public Page<TopicDto> getTopicsByName(String name, Pageable page) {
        return topicRepository.findTopicEntitiesByName(name, page)
                .map(topicEntity -> mapper.entityToDto(topicEntity, new CycleAvoidingMappingContext()));

       /* TopicEntity topicEntity = topicRepository.findTopicEntityByName(name).orElseThrow();

        return mapper.entityToDto(topicEntity, new CycleAvoidingMappingContext());*/

    }

    @Override
    public List<TopicEntity> getAll() {
        return topicRepository.findAll();
    }

    @Override
    public List<TopicEntity> getAllByName(String name) {
        return topicRepository.findTopicEntitiesByName(name);
    }

    @Override
    public List<TopicEntity> getAllByDescription(String description) {
        return topicRepository.findTopicEntitiesByDescriptionContaining(description);
    }

    @Override
    public List<TopicEntity> getAllByCourseId(Long courseId) {
        return topicRepository.findTopicEntitiesByCoursesId(courseId);}

    @Override
    public List<TopicEntity> getAllByCourseName(String courseName) {
        return topicRepository.findTopicEntitiesByCoursesName(courseName);
    }
}
