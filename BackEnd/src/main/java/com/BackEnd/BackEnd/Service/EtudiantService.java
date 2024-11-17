package com.BackEnd.BackEnd.Service;

import com.BackEnd.BackEnd.Entities.Etudiant;
import com.BackEnd.BackEnd.Repository.EtudiantRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EtudiantService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    public Etudiant creerEtudiant(Etudiant etudiant) {

        validerEtudiant(etudiant);

        String motDePasseChiffre = passwordEncoder.encode(etudiant.getMotDePasse());
        etudiant.setMotDePasse(motDePasseChiffre);
        return etudiantRepository.save(etudiant);
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    public Optional<Etudiant> getEtudiantById(Long id) {
        return etudiantRepository.findById(id);
    }

    public Optional<Etudiant> findByEmail(String email) {
        return etudiantRepository.findByEmail(email);
    }


    public Etudiant updateEtudiant(Long id, Etudiant etudiantDetails) {

        validerEtudiant(etudiantDetails);

        return etudiantRepository.findById(id).map(etudiant -> {

            etudiant.setNomPrenom(etudiantDetails.getNomPrenom());
            etudiant.setEmail(etudiantDetails.getEmail());

            if (etudiantDetails.getMotDePasse() != null && !etudiantDetails.getMotDePasse().isEmpty()) {
                String motDePasseChiffre = passwordEncoder.encode(etudiantDetails.getMotDePasse());
                etudiant.setMotDePasse(motDePasseChiffre);
            }

            return etudiantRepository.save(etudiant);
        }).orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));
    }

    public void deleteEtudiant(Long id) {
        etudiantRepository.deleteById(id);
    }

    public Optional<Etudiant> findByEmailAndMotDePasse(String email, String motDePasse) {
        Optional<Etudiant> etudiant = etudiantRepository.findByEmail(email);

        if (etudiant.isPresent()) {
            if (passwordEncoder.matches(motDePasse, etudiant.get().getMotDePasse())) {
                return etudiant;
            }
        }
        return Optional.empty();
    }

    private void validerEtudiant(Etudiant etudiant) {

        if (etudiant.getNomPrenom() == null || etudiant.getNomPrenom().trim().isEmpty()) {
            throw new IllegalArgumentException("Nom et prénom sont obligatoires");
        }


        if (etudiant.getEmail() == null || !isValidEmail(etudiant.getEmail())) {
            throw new IllegalArgumentException("Email invalide");
        }

        if (etudiant.getMotDePasse() == null || etudiant.getMotDePasse().length() < 8) {
            throw new IllegalArgumentException("Le mot de passe doit comporter au moins 8 caractères");
        }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
