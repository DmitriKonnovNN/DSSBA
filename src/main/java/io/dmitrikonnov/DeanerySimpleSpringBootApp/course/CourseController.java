package io.dmitrikonnov.DeanerySimpleSpringBootApp.course;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.PrePostProcTestFeatures.Quoter;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.annotation.RequestDTO;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.exception.BadRequestParameterException;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.exception.FileEmptyException;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.exception.NotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("api/v1/courses")
@AllArgsConstructor
public class CourseController {



    // TODO: add @InitBinder public void initBinder (){} ???
    // TODO: try out get Map: ...stream().map(x->x.dosmth).collect(Collectors.toMap(someClass::getSomething, Function.identity()))
    private final CourseService courseService;
    private final Quoter quoter;





    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById (@PathVariable Long id) {
        quoter.say();
                return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseById(id));
    }

    @ApiOperation(value = "get all courses by some parameters or without any",
            notes = "only one or zero parameters are allowed")
    @GetMapping ("/row/all")
    public ResponseEntity getAllEntities(@ApiParam (allowEmptyValue = true)
                                     @RequestParam (required = false) Map<String, String> paramsMap){
        List<CourseEntity> tempCourseEntities;
        if (paramsMap.isEmpty()) return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCourseEntities());
        if (paramsMap.size()>1) throw new BadRequestParameterException("More than 1 parameter is not allowed.");

        if (paramsMap.containsKey("name"))tempCourseEntities = courseService.getAllByName(paramsMap.get("name"));
        else if (paramsMap.containsKey("topic"))tempCourseEntities =  courseService.getAllByTopicName(paramsMap.get("topic"));
        else if (paramsMap.containsKey("duration"))tempCourseEntities = courseService.getAllByDurationUnities(Integer.valueOf(paramsMap.get("duration")));
        else if (paramsMap.containsKey("topicId"))tempCourseEntities = courseService.getAllByTopicId(Long.valueOf(paramsMap.get("topicId")));
        else if (paramsMap.containsKey("material"))tempCourseEntities =  courseService.getAllByTeachingMaterial(paramsMap.get("material"));

        else throw new BadRequestParameterException(String.format("Search by parameter %s not implemented or impossible.", paramsMap.keySet()));

        return ResponseEntity.status(HttpStatus.OK).body(tempCourseEntities);
    }

    @GetMapping ("/all")
    public ResponseEntity<Page<CourseDto>> getAllPageable (Pageable page) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllPageable(page));
    }

   @ApiOperation(value = "search for a course by its name",
            notes = "provide the whole name of the course",
            response = CourseDto.class)
    @GetMapping ("/")
    public ResponseEntity<CourseDto> getCourseByName (@ApiParam(value = "String name", required = true) @RequestParam String name) {
       return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseByName(name));
    }

    @PutMapping("/{id}/")
        public ResponseEntity<String> addProfileImage(@RequestBody MultipartFile file, @PathVariable Long id) throws IOException {
        if (file==null || file.isEmpty()) {
            throw new FileEmptyException("File to be uploaded is empty or not found!");
        }
        try {
            courseService.updateProfileImageById(file, id);
        } catch (Exception ex){
            if (ex instanceof IOException) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());}
            if (ex instanceof NotFoundException){ throw new NoSuchObjectException(ex.getMessage());}
        }
        return ResponseEntity.status(HttpStatus.OK).body(file.getOriginalFilename());
    }


    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCourse ( @Validated @RequestBody CourseDto courseDto) {

        courseService.addCourse(courseDto);
    }


        @PutMapping ("/")
    public void updateCourse( @RequestDTO(CourseDto.class) @Validated CourseEntity courseEntity) {
        courseService.updateCourse(courseEntity);
    }

    @PatchMapping("/")
    public void updateCoursePartly (@RequestDTO(CourseDto.class) @Validated CourseEntity courseEntity) {
        courseService.updateCoursePartly(courseEntity);
    }


    @DeleteMapping("/{id}")
    public void deleteCourseById (@PathVariable Long id) {
        courseService.deleteCourseById(id);
    }

    /*@ExceptionHandler ({NotFoundException.class})
    public ResponseEntity <String> notFound (NotFoundException ex){
        return ResponseEntity.status(HttpStatus.OK).body("");
    }*/
}
