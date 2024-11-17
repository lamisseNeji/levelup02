package com.BackEnd.BackEnd.Service;

import com.BackEnd.BackEnd.Entities.Enseignant;
import com.BackEnd.BackEnd.Repository.EnseignantRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EnseignantService(EnseignantRepository enseignantRepository) {
        this.enseignantRepository = enseignantRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Créer un enseignant
    public Enseignant creerEnseignant(Enseignant enseignant) {

        validerEnseignant(enseignant);

        // Chiffrer le mot de passe
        String motDePasseChiffre = passwordEncoder.encode(enseignant.getMotDePasse());
        enseignant.setMotDePasse(motDePasseChiffre);
        return enseignantRepository.save(enseignant);
    }

    // Récupérer tous les enseignants
    public List<Enseignant> getAllEnseignants() {
        return enseignantRepository.findAll();
    }

    // Récupérer un enseignant par son ID
    public Optional<Enseignant> getEnseignantById(Long id) {
        return enseignantRepository.findById(id);
    }

    // Trouver un enseignant par email
    public Optional<Enseignant> findByEmail(String email) {
        return enseignantRepository.findByEmail(email);
    }

    // Mettre à jour les informations d'un enseignant
    public Enseignant updateEnseignant(Long id, Enseignant enseignantDetails) {

        validerEnseignant(enseignantDetails);

        return enseignantRepository.findById(id).map(enseignant -> {

            enseignant.setNomPrenom(enseignantDetails.getNomPrenom());
            enseignant.setEmail(enseignantDetails.getEmail());
            enseignant.setSpecialite(enseignantDetails.getSpecialite());

            if (enseignantDetails.getMotDePasse() != null && !enseignantDetails.getMotDePasse().isEmpty()) {
                String motDePasseChiffre = passwordEncoder.encode(enseignantDetails.getMotDePasse());
                enseignant.setMotDePasse(motDePasseChiffre);
            }

            return enseignantRepository.save(enseignant);
        }).orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));
    }

    // Supprimer un enseignant
    public void deleteEnseignant(Long id) {
        enseignantRepository.deleteById(id);
    }

    // Trouver un enseignant par email et mot de passe
    public Optional<Enseignant> findByEmailAndMotDePasse(String email, String motDePasse) {
        Optional<Enseignant> enseignant = enseignantRepository.findByEmail(email);

        if (enseignant.isPresent()) {
            if (passwordEncoder.matches(motDePasse, enseignant.get().getMotDePasse())) {
                return enseignant;
            }
        }
        return Optional.empty();
    }

    // Valider les informations de l'enseignant
    private void validerEnseignant(Enseignant enseignant) {

        if (enseignant.getNomPrenom() == null || enseignant.getNomPrenom().trim().isEmpty()) {
            throw new IllegalArgumentException("Nom et prénom sont obligatoires");
        }

        if (enseignant.getEmail() == null || !isValidEmail(enseignant.getEmail())) {
            throw new IllegalArgumentException("Email invalide");
        }

        if (enseignant.getMotDePasse() == null || enseignant.getMotDePasse().length() < 8) {
            throw new IllegalArgumentException("Le mot de passe doit comporter au moins 8 caractères");
        }

        if (enseignant.getSpecialite() == null || enseignant.getSpecialite().trim().isEmpty()) {
            throw new IllegalArgumentException("La spécialité est obligatoire");
        }
    }

    // Validation de l'email
    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
