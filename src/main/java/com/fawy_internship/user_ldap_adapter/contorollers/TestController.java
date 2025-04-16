package com.fawy_internship.user_ldap_adapter.contorollers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class TestController {
    @GetMapping
    public String get() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
