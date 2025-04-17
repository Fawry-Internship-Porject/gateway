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
    public ResponseEntity<?> add(@RequestBody UserDTO userDTO) throws Exception {
        this.userService.add(userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole());
        return ResponseEntity.ok(Map.of("message", "User added successfully"));
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> update(@PathVariable String username, @RequestBody UserDTO userDTO) {
        userService.modify(username, userDTO.getPassword(), userDTO.getRole());
        return ResponseEntity.ok(Map.of("message", "User updated successfully"));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> delete(@PathVariable String username) {
        userService.delete(username);
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
}
