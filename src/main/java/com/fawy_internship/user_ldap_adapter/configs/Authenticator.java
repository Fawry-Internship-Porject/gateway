//package com.fawy_internship.user_ldap_adapter.configs;
//
//import com.fawy_internship.user_ldap_adapter.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ldap.AuthenticationException;
//import org.springframework.ldap.core.DirContextOperations;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.ldap.authentication.LdapAuthenticator;
//
//import java.util.List;
//
//public class Authenticator implements AuthenticationProvider {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) {
//        String username = authentication.getPrincipal().toString();
//        String password = authentication.getCredentials().toString();
//
//        if (userRepository.findByUsernameAndPassword(username, password).isEmpty())
//            throw new AuthenticationException();
//        return new UsernamePasswordAuthenticationToken(username, password, List.of(new SimpleGrantedAuthority("employee")));
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return false;
//    }
//}
