package com.fawy_internship.user_ldap_adapter.repositories;

import com.fawy_internship.user_ldap_adapter.entities.User;
import org.springframework.data.ldap.repository.LdapRepository;

import java.util.Optional;

public interface UserRepository extends LdapRepository<User> {
    Optional<User> findByUsername(String username);
}
