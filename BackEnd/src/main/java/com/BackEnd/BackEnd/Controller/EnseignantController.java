package com.BackEnd.BackEnd.Controller;

import com.BackEnd.BackEnd.Entities.Enseignant;
import com.BackEnd.BackEnd.Service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enseignants")
public class EnseignantController {

    @Autowired
    private EnseignantService enseignantService;

    // Créer un enseignant
    @PostMapping
    public ResponseEntity<Enseignant> creerEnseignant(@RequestBody Enseignant enseignant) {
        return ResponseEntity.ok(enseignantService.creerEnseignant(enseignant));
    }

    // Lire tous les enseignants
    @GetMapping
    public ResponseEntity<List<Enseignant>> getAllEnseignants() {
        return ResponseEntity.ok(enseignantService.getAllEnseignants());
    }

    // Lire un enseignant par email
    @GetMapping("/{email}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Enseignant> getEnseignantByEmail(@PathVariable String email) {
        Optional<Enseignant> enseignant = enseignantService.findByEmail(email);
        return enseignant.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).build());
    }

    // Mettre à jour un enseignant
    @PutMapping("/{id}")
    public ResponseEntity<Enseignant> updateEnseignant(@PathVariable Long id, @RequestBody Enseignant enseignantDetails) {
        return ResponseEntity.ok(enseignantService.updateEnseignant(id, enseignantDetails));
    }

    // Supprimer un enseignant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnseignant(@PathVariable Long id) {
        enseignantService.deleteEnseignant(id);
        return ResponseEntity.noContent().build();
    }

    // Authentification
    @PostMapping("/authenticate")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Enseignant> authenticate(@RequestParam String email, @RequestParam String motDePasse) {
        Optional<Enseignant> enseignant = enseignantService.findByEmailAndMotDePasse(email, motDePasse);
        return enseignant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).build());
    }
}
