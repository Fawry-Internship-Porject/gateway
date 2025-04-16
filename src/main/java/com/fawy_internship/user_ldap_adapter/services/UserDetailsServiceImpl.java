//package com.fawy_internship.user_ldap_adapter.services;
//
//import com.fawy_internship.user_ldap_adapter.entities.User;
//import com.fawy_internship.user_ldap_adapter.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.Optional;
//
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    private UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = userRepository.findByUsername(username);
//        if (user.isPresent()) {
//            return org.springframework.security.core.userdetails.User
//                    .withUsername(username)
//                    .password(user.get().getPassword())
//                    .roles("ROLE_employee")
//                    .build();
//        }
//        throw new UsernameNotFoundException("not found");
//
//    }
//}
