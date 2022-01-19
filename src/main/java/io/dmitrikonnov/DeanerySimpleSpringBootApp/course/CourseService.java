package io.dmitrikonnov.DeanerySimpleSpringBootApp.course;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.course.CourseDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.course.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseService {


    public void updateCoursePartly (CourseEntity courseEntity);
    public void deleteCourseById (Long id);
    public void addCourse (CourseDto courseDto);
    public void updateCourse(CourseEntity courseEntity);
    public void updateProfileImageById(MultipartFile file, Long id) throws IOException;
    public CourseDto getCourseById (Long id);
    public CourseDto getCourseByName (String name);
    public Page <CourseDto> getAllPageable (Pageable page);
   public List<CourseEntity> getAllCourseEntities();
   public List <CourseEntity> getAllByName(String name);
   public List <CourseEntity> getAllByDurationUnities(Integer durationUnities);
   public List <CourseEntity> getAllByTeachingMaterial (String teachingMaterial);
   public List <CourseEntity> getAllByTopicId (Long id);
   public List <CourseEntity> getAllByTopicName (String topicName);

}

