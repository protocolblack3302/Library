package com.coderme.Library.Controllers;

import com.coderme.Library.Domains.Authorities;
import com.coderme.Library.Domains.User;
import com.coderme.Library.Repository.AuthorityRepository;
import com.coderme.Library.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;


    @GetMapping
    public String getHomePage(User user , Model model){
        model.addAttribute("user",user);
        return "Register";
    }

    @PostMapping
    public String registerUser(@Valid User user , Errors error){
        if(error.hasErrors() || userRepository.existsById(user.getEmail())){
            return "/Register";
        }
        Authorities authority = new Authorities();
        authority.setUsername(user.getUsername());
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        authorityRepository.save(authority);
        return "login";
    }


}
