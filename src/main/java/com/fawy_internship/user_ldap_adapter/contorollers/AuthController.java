//package com.fawy_internship.user_ldap_adapter.contorollers;
//
//import com.fawy_internship.user_ldap_adapter.dtos.LoginRequest;
//import com.fawy_internship.user_ldap_adapter.services.UserService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private AuthenticationManager authenticationManager;
////    @GetMapping("/login")
////    public Authentication login() {
////        Authentication authentication = this.authenticationManager.authenticate(
////                UsernamePasswordAuthenticationToken.unauthenticated(
////                        "emptest",
////                        "12345"
////                )
////        );
////        SecurityContextHolder.getContext().setAuthentication(
////                authentication
////        );
////        return SecurityContextHolder.getContext().getAuthentication();
////    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
//        return ResponseEntity.ok("Login successful");
//    }
////        SecurityContextHolder.getContext().setAuthentication(
////                this.authenticationManager.authenticate(
////                        UsernamePasswordAuthenticationToken.unauthenticated(
////                                loginRequest.getUsername(),
////                                loginRequest.getPassword()
//                        )A
////                )
////        );
////        HttpSession session = request.getSession();
////        String sessionId = session.getId();
////
////        return ResponseEntity.ok(Map.of(
////                "sessionId", sessionId,
////                "message", "Login successful"
////        ));
////
////        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getName());
////    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> signup(@RequestBody LoginRequest loginRequest) {
//        userService.create(loginRequest.getUsername(), loginRequest.getPassword());
//        return ResponseEntity.ok().build();
////
////        URI location = ServletUriComponentsBuilder
////                .fromCurrentRequest()
////                .path("/{username}")
////                .buildAndExpand(loginRequest.getUsername())
////                .toUri();
////
////       return ResponseEntity.created(location).build();
//    }
//}
