package io.dmitrikonnov.DeanerySimpleSpringBootApp.course;



import io.dmitrikonnov.DeanerySimpleSpringBootApp.converter.CourseMapper;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.converter.CycleAvoidingMappingContext;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@Transactional
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

   private final CourseRepository courseRepository;
   private final CourseLobRepository courseLobRepository;
   private final static String ID_NOT_FOUND_MSG = "course with id %d not found. Inner cause : %s.";

   private final static Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);
   private final static CourseMapper mapper = CourseMapper.COURSE_MAPPER;

/**
 * Update only fields given in RequestBody.
 * Null/empty/blank fields in RequestBody will be ignored: Corresponding persisted fields won't be concerned by update.
 * */
    @Override
    public void updateCoursePartly(CourseEntity courseEntity) {
        CourseEntity persistedEntity = courseRepository
                .findById(courseEntity.getId())
                .orElseThrow(()->new NotFoundException(String.format(ID_NOT_FOUND_MSG , courseEntity.getId())));

        Method [] persMethods = persistedEntity.getClass().getMethods();
        Field [] toSaveFields = courseEntity.getClass().getDeclaredFields();
        ArrayList<String> toSaveFieldNames = new ArrayList<>();
        for (Field field: toSaveFields) {
            String tempName = field.getName();
            String name = tempName.substring(0,1).toUpperCase() + tempName.substring(1);
            toSaveFieldNames.add(name);
        }
        Method [] toSaveMethods = courseEntity.getClass().getMethods();
        for (int j = 0; j < toSaveFieldNames.size(); j++) {
            String toSaveFieldName = toSaveFieldNames.get(j);

            for (int k = 0; k < toSaveMethods.length; k++) {
                Object toPersistValue = new Object();

                if (toSaveMethods[k].getName().startsWith("get") &&
                        toSaveMethods[k].getName().contains(toSaveFieldName) &&
                        !toSaveMethods[k].getName().contains("Id") ) {
                    try {
                        toPersistValue = toSaveMethods[k].invoke(courseEntity, new Object[]{});
                        if (toPersistValue == null) continue;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error(e.getMessage());
                    }
                    for (int i = 0; i < persMethods.length; i++) {
                        if (persMethods [i].getName().startsWith("set") &&
                                persMethods[i].getName().contains(toSaveFieldName) &&
                                !toSaveMethods[i].getName().contains("Id") ) {
                            try {
                                persMethods [i].invoke(persistedEntity, toPersistValue );
                                break;
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                log.error(e.getMessage());
                            }
                        }
                    }

                }

            }

        }
        courseRepository.save(persistedEntity);
    }



    @Override
    public void addCourse(CourseDto courseDto) {
        courseRepository.save(mapper.dtoToEntity(courseDto));
    }

    @Override
    public void updateProfileImageById(MultipartFile file, Long id) throws IOException {
        try {
            if (courseLobRepository.existsById(id)) {
                  courseLobRepository.updateProfileImageById(id, getBytes(file));
            }
            else courseLobRepository.save(new CourseLobs(getCourseEntityById(id),getBytes(file)));
            }
        catch (Exception ex ) {
            if (ex instanceof IOException) {
                log.error(ex.getMessage());
                throw ex;
            }
           if (ex instanceof NotFoundException) {
                throw new NotFoundException(ex.getMessage());
            }
            else log.error(ex.getMessage());
        }
    }

    @Override
    public CourseDto getCourseById (Long id){

        try {

            return mapper.entityToDto(courseRepository.findById(id).orElseThrow(), new CycleAvoidingMappingContext()) ;

        } catch (Exception ex) {
            if (ex instanceof NoSuchElementException) {
                String rootCauseMsg = ExceptionUtils.getRootCauseMessage(ex);
                log.error(String.format(ID_NOT_FOUND_MSG, id, rootCauseMsg));
                throw new NotFoundException(String.format(ID_NOT_FOUND_MSG, id, rootCauseMsg));
            }
            else log.error(ExceptionUtils.getRootCauseMessage(ex));
            throw ex;
        }
    }

    protected CourseEntity getCourseEntityById (Long id){

        try {

            return courseRepository.findById(id).orElseThrow();

        } catch (Exception ex) {
            if (ex instanceof NoSuchElementException) {
                String rootCauseMsg = ExceptionUtils.getRootCauseMessage(ex);
                log.error(String.format(ID_NOT_FOUND_MSG, id, rootCauseMsg));
                throw new NotFoundException(String.format(ID_NOT_FOUND_MSG, id, rootCauseMsg));
            }
            else log.error(ExceptionUtils.getRootCauseMessage(ex));
            throw ex;
        }
    }


    private byte[] getBytes(MultipartFile multipartFile) throws IOException {
        return multipartFile.getBytes();
    }

    @Override
    public void updateCourse(CourseEntity courseEntity) {
        courseRepository.save(courseEntity);
    }


    @Override
    public void deleteCourseById (Long id){
        courseRepository.deleteById(id);
    }


    @Override
    public CourseDto getCourseByName(String name) {
        CourseEntity courseEntity = courseRepository.findCourseEntityByName(name).orElseThrow();
        return mapper.entityToDto(courseEntity,new CycleAvoidingMappingContext());

    }

    @Override
    public List<CourseEntity> getAllCourseEntities() {
         return courseRepository.findAll();
    }

    @Override
    public Page<CourseDto> getAllPageable(Pageable page) {
        return courseRepository.findAll(page)
                .map(courseEntity -> mapper.entityToDto(courseEntity, new CycleAvoidingMappingContext()));
    }

    @Override
    public List<CourseEntity> getAllByName(String name) {
        return courseRepository.findAllByName(name);
    }

    @Override
    public List<CourseEntity> getAllByDurationUnities(Integer durationUnities) {
        return courseRepository.findAllByDurationInUnities(durationUnities);
    }

    @Override
    public List<CourseEntity> getAllByTeachingMaterial(String teachingMaterial) {
        return courseRepository.findAllByTeachingMaterialContainsOrderByName(teachingMaterial);
    }

    @Override
    public List<CourseEntity> getAllByTopicId(Long topicId) {
        return courseRepository.findCourseEntitiesByTopicId(topicId);
    }

    @Override
    public List<CourseEntity> getAllByTopicName(String topicName) {
        return courseRepository.findCourseEntitiesByTopicName(topicName);
    }


}
