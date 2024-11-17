package com.BackEnd.BackEnd.Repository;

import com.BackEnd.BackEnd.Entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    Optional<Etudiant> findByEmailAndMotDePasse(String email, String motDePasse);
    Optional<Etudiant> findByEmail(String email);
}
