//package com.RentEase.RentEase_backend.security;
//
//import com.RentEase.RentEase_backend.repositories.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomeUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepo userRepo;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return this.userRepo.findByUserName(username).orElseThrow(
//                () -> new UsernameNotFoundException("Authentication Failure!!")
//        );
//    }
//}
