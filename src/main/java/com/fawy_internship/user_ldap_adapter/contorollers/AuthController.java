package com.fawy_internship.user_ldap_adapter.contorollers;

import com.fawy_internship.user_ldap_adapter.dtos.LoginRequest;
import com.fawy_internship.user_ldap_adapter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("ssss");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (this.userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword()))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody LoginRequest loginRequest) {
       userService.create(loginRequest.getUsername(), loginRequest.getPassword());
       return ResponseEntity.ok().build();
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{username}")
//                .buildAndExpand(loginRequest.getUsername())
//                .toUri();
//
//       return ResponseEntity.created(location).build();
    }
}
