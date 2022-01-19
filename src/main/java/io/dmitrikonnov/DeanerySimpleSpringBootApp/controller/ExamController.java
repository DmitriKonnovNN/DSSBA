package io.dmitrikonnov.DeanerySimpleSpringBootApp.controller;


import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Appointment.ExamDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Entity.ExamEntity;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.PersistenceService.ExamService;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.annotation.RequestDTO;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/exams")
public class ExamController {

    private final ExamService examService;

    @GetMapping("/id")
    public ResponseEntity<ExamEntity> getExamById (@RequestParam String id) {
        return ResponseEntity.status(HttpStatus.OK).body(examService.getExamById(id));
    }
    @GetMapping("/name")
    public ResponseEntity<List<ExamEntity>> getExamsByName (@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(examService.getExamByName(name));
    }
    @GetMapping("/all")
    public ResponseEntity<List<ExamEntity>> getAll (){
        return ResponseEntity.status(HttpStatus.OK).body(examService.getAll());
    }

    @PostMapping("/")
    public ResponseEntity<ExamEntity> addExam (@Validated @RequestDTO(ExamDto.class) ExamEntity examEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examService.addExam(examEntity));
    }

    @PutMapping("/")
    public ResponseEntity <ExamEntity> updateExam(@Validated @RequestDTO(ExamDto.class) ExamEntity examEntity){
        return ResponseEntity.status(HttpStatus.OK).body(examService.update(examEntity));
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteExam (@PathVariable String id){
        examService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Exam with id "+id +" has been deleted");
    }


}
