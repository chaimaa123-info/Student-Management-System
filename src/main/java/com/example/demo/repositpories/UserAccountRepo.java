package com.example.demo.repositpories;

import java.util.Optional;

//import org.springframework.boot.security.autoconfigure.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.UserAccount;

public interface UserAccountRepo extends JpaRepository<UserAccount, Long>{


    //on cherche  le compte par username et pas par id de etudiant car :
    //-> L'authentifiaction se fait par username et pas id , il se connecte (login) par username et password
    Optional<UserAccount> findOneByUsername(String username);

  @Query("""
      SELECT COUNT(u) > 0 FROM UserAccount u
      WHERE u.username = :username AND u.etudiant.id = :etudiantId 
      """)
      //(:id) c est un id qui devient de parameters 
  public boolean isOwner(@Param("username") String username, @Param("id") Long etudiantId);
}