package com.RentEase.RentEase_backend.entities;

import com.RentEase.RentEase_backend.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user_table")
public class User implements UserDetails {

    @Id
    private String userId;
    private String fullName;
    private String userName;
    private String password;
    private String email;
    private String photoUrl;
    private Long phone;
    private String address;
    private String city;
    private int pinCode;
    private String state;
    private Role role;

    // Relationships
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Landlord landlord;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Tenant tenant;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.userName;
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
