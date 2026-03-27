package com.example.demo.repositpories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.Etudiant;

//import jakarta.validation.constraints.NotNull;

@Repository
public interface EtudiantRepo extends JpaRepository<Etudiant, Long>{

     Optional<Etudiant> findOneByAccountCreationToken(String token);
   

}
