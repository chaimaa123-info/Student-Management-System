package com.example.demo.entities;



import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_account")
public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "username", nullable = false, unique=true,  length =100)
    private String username;

    @Column(name = "password", nullable = false, length =100)
    private String password;
     
    @Column(name = "role", nullable = false)
    private String role="USER"; //tous ce qui va faire sign up son role va etre tjrs USER

    @OneToOne(fetch = FetchType.EAGER, optional = false) 
    @JoinColumn(name = "etudiant_id", unique=true, nullable = false)
    private Etudiant etudiant;

    public Collection<? extends GrantedAuthority> getAuthorities(){
       // ← CORRECTION : Retourne une collection avec le rôle
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }
    

     @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
} 
