package com.RentEase.RentEase_backend.security;

import com.RentEase.RentEase_backend.entities.Tenant;
import com.RentEase.RentEase_backend.entities.User;
import com.RentEase.RentEase_backend.repositories.LandlordRepo;
import com.RentEase.RentEase_backend.repositories.TenantRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomeUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomeUserDetailsService.class);

    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private LandlordRepo landlordRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Attempt to find the user in TenantRepo
        User appUser = tenantRepo.findByUserName(username)
                .map(tenant -> (User) tenant)
                // Attempt to find the user in LandlordRepo if not found in TenantRepo
                .orElseGet(() -> landlordRepo.findByUserName(username)
                        .map(landlord -> (User) landlord)
                        .orElseThrow(() -> new UsernameNotFoundException("Authentication Failure!!")));
        logger.info("Loaded user data: {} " + appUser.getRole() + " " +
                appUser.getCity() + " " + appUser.isUserActivated());
        return appUser;
    }
}
