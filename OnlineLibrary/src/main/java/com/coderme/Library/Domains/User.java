package com.coderme.Library.Domains;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Collections;


@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {
    @Column(name="enabled",nullable = false)
    private Boolean enabled=true;
    @NotBlank
    @Column(name = "fullname" ,nullable = false)
    @Size(min=2 , max =50 ,message = "please enter a valid name")
    private String fullname;
    @Id
    @NotBlank
    @Email(message = "Please enter a valid email address")
    @Column(name = "email" ,nullable = false)
    private String email;
    @NotBlank
    @Column(name = "password" ,nullable = false)
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Password should be min lenght of 8 and a combination of uppercase , lower , numbers and symbols")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return  Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
