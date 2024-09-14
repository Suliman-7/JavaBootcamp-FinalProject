package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Model.Question;
import com.example.rekazfinalproject.Service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/get-all-questions")
    public ResponseEntity getAllQuestion()
    {
        return ResponseEntity.status(200).body(questionService.getAllQuestion());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateQuestion(@PathVariable Integer id, @Valid @RequestBody Question question)
    {
        questionService.updateQuestion(id, question);
        return ResponseEntity.status(200).body("Question updated");
    }

     @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteQuestion(@PathVariable Integer id)
    {
        questionService.deleteQuestion(id);
        return ResponseEntity.status(200).body("Question deleted");
    }


}