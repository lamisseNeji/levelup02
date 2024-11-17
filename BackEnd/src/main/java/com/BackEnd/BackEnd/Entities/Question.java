package com.BackEnd.BackEnd.Entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.List;
import com.BackEnd.BackEnd.Entities.Test;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Getter
@Setter
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;

    @ElementCollection
    private List<String> optionsReponse;
    private String reponseCorrecte;

    @ManyToOne
    @JoinColumn(name = "test_id")
    @JsonBackReference
    private Test test;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getOptionsReponse() {
		return optionsReponse;
	}

	public void setOptionsReponse(List<String> optionsReponse) {
		this.optionsReponse = optionsReponse;
	}

	public String getReponseCorrecte() {
		return reponseCorrecte;
	}

	public void setReponseCorrecte(String reponseCorrecte) {
		this.reponseCorrecte = reponseCorrecte;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}
    
    
}

