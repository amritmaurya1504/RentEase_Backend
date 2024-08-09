package com.RentEase.RentEase_backend.config;

import com.RentEase.RentEase_backend.enums.Role;
import com.RentEase.RentEase_backend.security.CustomeUserDetailsService;
import com.RentEase.RentEase_backend.security.JWTAuthenticationFiler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Autowired
    private JWTAuthenticationFiler jwtAuthenticationFiler;

    @Autowired
    private CustomeUserDetailsService customeUserDetailsService;

    public static final String[] PUBLIC_MATCHERS = {
            "/api/v1/auth/**",
            "/swagger-ui/**",
            "/swagger-resources/*",
            "/v3/api-docs/**"
    };
    public static final String[] LANDLORD_MATCHERS = {
            "/api/v1/properties/**",
            "/api/v1/users/**"
    };
    public static final String[] TENANT_MATCHERS = {
            "/api/v1/occupation/**",
            "/api/v1/users/**"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(PUBLIC_MATCHERS).permitAll()
                        .requestMatchers(LANDLORD_MATCHERS).hasAnyAuthority(Role.Landlord.name())
                        .requestMatchers(TENANT_MATCHERS).hasAnyAuthority(Role.Tenant.name())
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFiler, UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this.customeUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws
            Exception{
        return configuration.getAuthenticationManager();
    }
}
