package com.fawy_internship.user_ldap_adapter.services;

import com.fawy_internship.user_ldap_adapter.entities.User;
import com.fawy_internship.user_ldap_adapter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LdapTemplate ldapTemplate;

    public Boolean authenticate(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password).isPresent();
    }

    public void create(String username, String password) {
        User user = new User(username, password);

        DirContextAdapter context = new DirContextAdapter(user.getDn());
        context.setAttributeValues("objectClass", new String[] { "top", "user" });
        context.setAttributeValue("uid", username);
        context.setAttributeValue("userPassword", password);

        ldapTemplate.bind(context);
    }

    public void modify(String username, String password) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setPassword(password);
            userRepository.save(user);
        });
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
