package com.BackEnd.BackEnd.Repository;

import com.BackEnd.BackEnd.Entities.Enseignant;
import com.BackEnd.BackEnd.Entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    Optional<Enseignant> findByEmailAndMotDePasse(String email, String motDePasse);
    Optional<Enseignant> findByEmail(String email);
}
