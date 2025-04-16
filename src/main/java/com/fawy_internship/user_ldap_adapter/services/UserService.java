package com.fawy_internship.user_ldap_adapter.services;

import com.fawy_internship.user_ldap_adapter.entities.User;
import com.fawy_internship.user_ldap_adapter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    //    @Autowired
//    private GroupRepository groupRepository;
    @Autowired
    private LdapTemplate ldapTemplate;
//    @Autowired
//    private AuthenticationManager authenticationManager;

//    public void authenticate(LoginRequest loginRequest) throws AuthenticationException {
//        Authentication name = SecurityContextHolder.getContext().getAuthentication();
//
//        SecurityContextHolder.getContext().setAuthentication(
//                authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                loginRequest.getUsername(),
//                                loginRequest.getPassword()
//                        )
//                )
//        );
  //  }

    public void create(String username, String password) {
        User user = new User(username, password);

        DirContextAdapter context = new DirContextAdapter(user.getDn());
        context.setAttributeValues("objectClass", new String[]{"top", "user"});
        context.setAttributeValue("uid", username);
        context.setAttributeValue("userPassword", password);

        ldapTemplate.bind(context);

        addUserToGroup(user.getDn(), "cn=employees,ou=groups");
    }

    public void addUserToGroup(Name userDn, String groupDn) {
        Attribute member = new BasicAttribute("member", userDn + ",dc=example,dc=com");
        ModificationItem item = new ModificationItem(DirContext.ADD_ATTRIBUTE, member);

        ldapTemplate.modifyAttributes(groupDn, new ModificationItem[]{item});
//        groupRepository.findByCn(groupCn).ifPresentOrElse(
//                group -> {
//                    group.getMembers().add(userDn);
//                    groupRepository.save(group);
//                },
//                () -> {
//                    throw new IllegalArgumentException("Group not found: " + groupCn);
//                }
//        );
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
