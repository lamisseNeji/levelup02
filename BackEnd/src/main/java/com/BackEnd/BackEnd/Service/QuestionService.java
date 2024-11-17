package com.BackEnd.BackEnd.Service;

import com.BackEnd.BackEnd.Entities.Question;
import com.BackEnd.BackEnd.Entities.Test;
import com.BackEnd.BackEnd.Repository.QuestionRepository;
import com.BackEnd.BackEnd.Repository.TestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TestRepository testRepository;

    // Créer une nouvelle question
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    // Récupérer toutes les questions
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Récupérer une question par son ID
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    // Mettre à jour une question
    public Question updateQuestion(Long id, Question questionDetails) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
        question.setQuestion(questionDetails.getQuestion());
        question.setOptionsReponse(questionDetails.getOptionsReponse());
        question.setReponseCorrecte(questionDetails.getReponseCorrecte());
        question.setTest(questionDetails.getTest());
        return questionRepository.save(question);
    }

    // Supprimer une question
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
    
    public List<Question> getQuestionByIdTest(Long id) {
    	Test t=testRepository.getById(id);
    	return this.questionRepository.findByTest(t);
    }
}
