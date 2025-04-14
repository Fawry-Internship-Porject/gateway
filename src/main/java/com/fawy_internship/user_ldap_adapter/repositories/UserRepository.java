package com.fawy_internship.user_ldap_adapter.repositories;

import com.fawy_internship.user_ldap_adapter.entities.User;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends LdapRepository<User> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
}
