package com.example.rekazfinalproject.Service;


import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.Question;
import com.example.rekazfinalproject.Repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getAllQuestion()
    {
        return questionRepository.findAll();
    }

    public void updateQuestion(Integer id,Question question)
    {
        Question question1 = questionRepository.findQuestionById(id);
        if(question1 == null)
        {
            throw new ApiException("Question not found");
        }
        question1.setQuestion(question.getQuestion());
        question1.setAnswer(question.getAnswer());
        questionRepository.save(question1);
    }


    public void deleteQuestion(Integer id)
    {
        if(questionRepository.findQuestionById(id) == null)
        {
            throw new ApiException("Question not found");
        }
        questionRepository.deleteById(id);
    }
}