package io.dmitrikonnov.DeanerySimpleSpringBootApp.topic;


import io.dmitrikonnov.DeanerySimpleSpringBootApp.exception.NotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/topics")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }



    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopicById (@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getTopicById(id));
    }

    @ApiOperation(value = "get all topics by some parameters or without any", notes = "only one or zero parameters are allowed" +
            "otherwise only the first parameter will be passed")
    @GetMapping ("/row/all")
    public ResponseEntity getAllEntities(@ApiParam(allowEmptyValue = true)
                                 @RequestParam(required = false) Map<String, String> paramsMap){
        List<TopicEntity> tempTopicEntities;
        if (paramsMap.containsKey("name"))tempTopicEntities = topicService.getAllByName(paramsMap.get("name"));
        else if (paramsMap.containsKey("course")) tempTopicEntities = topicService.getAllByCourseName(paramsMap.get("course"));
        else if (paramsMap.containsKey("courseId")) tempTopicEntities = topicService.getAllByCourseId(Long.valueOf(paramsMap.get("courseId")));
        else if (paramsMap.containsKey("description"))tempTopicEntities =  topicService.getAllByDescription(paramsMap.get("description"));
        else return ResponseEntity.status(HttpStatus.OK).body(topicService.getAll());

        if (tempTopicEntities.isEmpty()) throw new NotFoundException("No matching request parameter found");
        return ResponseEntity.status(HttpStatus.OK).body(tempTopicEntities);
    }

   /* @GetMapping
    public ResponseEntity<Page<TopicEntity>> getAllPaged (@RequestParam int pageSize, @RequestParam (required = false, defaultValue = "0") int pageNumber){
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getAllPaged(pageSize, pageNumber));
    }*/

    @GetMapping("/all")
    public ResponseEntity<Page<TopicDto>> getAllPageable(/*@PageableDefault()*/ Pageable page){

        //@PageableDefault (args) takes such arguments as size etc that override concerned properties in application.yml file.
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getAllPageable(page));
    }
    @ApiOperation(value = "search for a topic by its name",
            notes = "provide the whole name of the topic",
            response = TopicEntity.class)
    @GetMapping ("/")
    public ResponseEntity<Page<TopicDto>> getTopicsByName(@ApiParam(value = "String name", required = true) @RequestParam String name, Pageable page) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getTopicsByName(name, page));
    }

   /* @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTopic (@Valid @RequestDTO(TopicDto.class) @Validated TopicEntity topicEntity) {
        topicService.addTopicTest(topicEntity);
    }*/

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTopic (@Valid @Validated @RequestBody TopicDto topicDto) {
        topicService.addTopic(topicDto);
    }

    @PutMapping ("/")
    public void updateTopic(@Valid @Validated  @RequestBody TopicDto topicDto) {
        topicService.updateTopic(topicDto);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.OK)
    public void deleteTopicById (@PathVariable Long id) {
        topicService.deleteTopicById(id);
    }
    
}
