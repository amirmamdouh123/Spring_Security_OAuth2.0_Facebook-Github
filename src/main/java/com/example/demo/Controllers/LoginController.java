package com.example.demo.Controllers;

import com.example.demo.Entities.LoginBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("loginJWT")
public class LoginController {

    @Autowired
    ProviderManager providerManager;

    @PostMapping("log")
    public ResponseEntity<Void> login(@RequestBody LoginBody credentials){
        System.out.println("loginJWT");
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        Authentication user=new UsernamePasswordAuthenticationToken(username,password);
        Authentication auth =providerManager.authenticate(user);


        SecurityContextHolder.getContext().setAuthentication(auth);
        return ResponseEntity.ok().build();
    }

    @GetMapping("log")
    public String c(){
        return "d5lt";
    }

}


