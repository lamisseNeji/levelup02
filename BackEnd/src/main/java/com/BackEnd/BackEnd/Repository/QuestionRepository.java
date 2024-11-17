package com.BackEnd.BackEnd.Repository;

import com.BackEnd.BackEnd.Entities.Question;
import com.BackEnd.BackEnd.Entities.Test;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire
	List<Question> findByTest(Test test); // Requête dérivée basée sur la relation entre Question et Test

}
