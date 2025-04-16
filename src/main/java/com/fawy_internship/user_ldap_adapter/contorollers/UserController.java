package com.fawy_internship.user_ldap_adapter.contorollers;

import com.fawy_internship.user_ldap_adapter.dtos.UserDTO;
import com.fawy_internship.user_ldap_adapter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ldap/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody UserDTO userDTO) {

            this.userService.add(userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole());
            return ResponseEntity.ok(Map.of("message", "User added successfully"));

    }
}
