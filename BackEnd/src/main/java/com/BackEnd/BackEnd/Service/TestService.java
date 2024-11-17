package com.BackEnd.BackEnd.Service;

import com.BackEnd.BackEnd.Entities.Test;
import com.BackEnd.BackEnd.Repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    // Créer un nouveau test
    public Test createTest(Test test) {
        return testRepository.save(test);
    }

    // Récupérer tous les tests
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    // Récupérer un test par son ID
    public Optional<Test> getTestById(Long id) {
        return testRepository.findById(id);
    }

    // Mettre à jour un test
    public Test updateTest(Long id, Test testDetails) {
        Test test = testRepository.findById(id).orElseThrow(() -> new RuntimeException("Test not found"));
        test.setLangue(testDetails.getLangue());
        test.setEnseignant(testDetails.getEnseignant());
        return testRepository.save(test);
    }

    // Supprimer un test
    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }
}
