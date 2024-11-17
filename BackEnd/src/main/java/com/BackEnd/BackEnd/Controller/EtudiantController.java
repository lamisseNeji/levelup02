package com.BackEnd.BackEnd.Controller;

import com.BackEnd.BackEnd.Entities.Etudiant;
import com.BackEnd.BackEnd.Service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    // Create
    @PostMapping
    public ResponseEntity<Etudiant> creerEtudiant(@RequestBody Etudiant etudiant) {
        return ResponseEntity.ok(etudiantService.creerEtudiant(etudiant));
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        return ResponseEntity.ok(etudiantService.getAllEtudiants());
    }

    // Read by ID
    @GetMapping("/{email}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Etudiant> getEtudiantByEmail(@PathVariable String email) {
        Optional<Etudiant> etudiant = etudiantService.findByEmail(email);
        return etudiant.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).build());
    }


    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Etudiant> updateEtudiant(@PathVariable Long id, @RequestBody Etudiant etudiantDetails) {
        return ResponseEntity.ok(etudiantService.updateEtudiant(id, etudiantDetails));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.noContent().build();
    }

    // Authentication
    @PostMapping("/authenticate")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Etudiant> authenticate(@RequestParam String email, @RequestParam String motDePasse) {
        Optional<Etudiant> etudiant = etudiantService.findByEmailAndMotDePasse(email, motDePasse);
        return etudiant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).build());
    }
}
