package com.example.demo.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entities.UserAccount;
import com.example.demo.exception.CustomResponseException;
import com.example.demo.repositpories.UserAccountRepo;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService{

// l'interface UserDetailsService rah fardha 3lina mn Spring Security. 
// Ila ma implementinahach, Spring Security ma ghadi yakhdemch l'authentification.
    @Autowired
    private UserAccountRepo userAccountRepo;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<UserAccount> account = userAccountRepo.findOneByUsername(username);

        if(account.isEmpty()){
            throw CustomResponseException.BadCredentials();
        }

        UserAccount user = account.get();
        return User.builder() //بدينا البناء
               .username(user.getUsername())
               .password(user.getPassword())
               .roles(user.getRole())
               .build();
    }
}
