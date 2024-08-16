package com.rentease_server.server.security;

import com.rentease_server.server.entities.User;
import com.rentease_server.server.repositories.LandlordRepo;
import com.rentease_server.server.repositories.TenantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomeUserDetailsService implements UserDetailsService {

    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private LandlordRepo landlordRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return tenantRepo.findByUserName(username)
                .map(tenant -> (User) tenant)
                // Attempt to find the user in LandlordRepo if not found in TenantRepo
                .orElseGet(() -> landlordRepo.findByUserName(username)
                        .map(landlord -> (User) landlord)
                        .orElseThrow(() -> new UsernameNotFoundException("Authentication Failure!!")));
    }
}
