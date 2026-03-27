package com.example.demo.services;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.LoginRequest;
import com.example.demo.DTOs.ResetPasswordRequest;
import com.example.demo.DTOs.SignupRequest;
import com.example.demo.config.JwtHelper;
import com.example.demo.entities.Etudiant;
import com.example.demo.entities.PasswordRestToken;
import com.example.demo.entities.UserAccount;
import com.example.demo.exception.CustomResponseException;
import com.example.demo.repositpories.EtudiantRepo;
import com.example.demo.repositpories.PasswordRestRepo;
import com.example.demo.repositpories.UserAccountRepo;

import jakarta.transaction.Transactional;

@Service
public class AuthService  {

    @Autowired
    private UserAccountRepo userAccountRepo;

    @Autowired
    private EtudiantRepo etudiantRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private PasswordRestRepo passwordRestRepo;

    @Autowired
    private EmailService emailService;

    @Transactional  // ← Ila 7sal chi erreur, 7ta 7aja mat7fedch f DB
    public void signup(SignupRequest signupRequest, String token){

        Etudiant etudiant = etudiantRepo.findOneByAccountCreationToken(token)

                .orElseThrow(() -> CustomResponseException.ResourceNotFound( "Invalid token"));

         if (etudiant.isVerified()){
            throw CustomResponseException.BadRequest("Account already created");
         }       
        UserAccount account = new UserAccount();
        account.setUsername(signupRequest.username());
        account.setPassword(passwordEncoder.encode(signupRequest.password())); //faire hash de password
        account.setEtudiant(etudiant);

        userAccountRepo.save(account);

        etudiant.setVerified(true);
        etudiant.setAccountCreationToken(null);
        etudiantRepo.save(etudiant);
    }

    public String login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.username(),
            loginRequest.password()
        )
    );

    UserAccount user = userAccountRepo.findOneByUsername(loginRequest.username())
        .orElseThrow(() -> CustomResponseException.BadCredentials());

    Map<String, Object> customClaims = new HashMap<>();
    customClaims.put("userId", user.getId());
    customClaims.put("role", user.getRole());
    return jwtHelper.generateToken(customClaims, user);

  }

     
   //Forgot & Reset Password
   @Transactional
   public void initiatePasswordRest(String username){

  try{

    //Vérifier que l'utilisateur existe avant de créer un token
          UserAccount user = userAccountRepo.findOneByUsername(username)
          .orElseThrow(() -> CustomResponseException.ResourceNotFound("Account not found"));

    //Générer un token unique &&  Définir la date d'expiration    
      String token = UUID.randomUUID().toString();
      LocalDateTime expiry = LocalDateTime.now().plusMinutes(15);

    //Créer l'objet token
      PasswordRestToken resetToken = new PasswordRestToken();
      resetToken.setToken(token);
      resetToken.setExpiryDate(expiry);
      resetToken.setUser(user);

    // Sauvegarder en base de données 
      passwordRestRepo.save(resetToken);
      
      emailService.sendPasswordRestEmail(user.getEtudiant().getEmail(), token);
 
}  catch(Exception e) {
      throw CustomResponseException.BadRequest("Sending reset password email failed");

   }

}



  public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
    PasswordRestToken restToken = passwordRestRepo.findOneByToken(resetPasswordRequest.token())
        .orElseThrow(() -> CustomResponseException.BadRequest("Invalid token"));
 
    boolean isTokenExpired = restToken.getExpiryDate().isBefore(LocalDateTime.now());
    if (isTokenExpired) {
      passwordRestRepo.delete(restToken);
      throw CustomResponseException.BadRequest("Token has expired, request a new one");
    }
 
    UserAccount user = restToken.getUser();
    user.setPassword(passwordEncoder.encode(resetPasswordRequest.newPassword()));
    userAccountRepo.save(user);
    passwordRestRepo.delete(restToken);
  }

}
