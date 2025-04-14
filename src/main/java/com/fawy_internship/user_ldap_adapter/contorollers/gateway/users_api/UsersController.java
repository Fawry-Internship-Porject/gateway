package com.fawy_internship.user_ldap_adapter.contorollers.gateway.users_api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Make a controller just like the controllers in your api but keep the body empty
@RestController
@RequestMapping("/users")
public class UsersController {
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        // I will do it
        return ResponseEntity.ok().build();
    }
}
