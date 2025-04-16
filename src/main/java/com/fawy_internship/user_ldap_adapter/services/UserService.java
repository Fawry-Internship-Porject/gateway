package com.fawy_internship.user_ldap_adapter.services;

import com.fawy_internship.user_ldap_adapter.dtos.UserDTO;
import com.fawy_internship.user_ldap_adapter.entities.Role;
import com.fawy_internship.user_ldap_adapter.entities.User;
import com.fawy_internship.user_ldap_adapter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LdapTemplate ldapTemplate;

    public Optional<User> getByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public void add(String username, String password, Role role) {
        String dn = getUserDn(username);
        DirContextAdapter context = new DirContextAdapter(dn);
        context.setAttributeValues("objectClass", new String[] {"top", "user"});
        context.setAttributeValue("userid", username);
        context.setAttributeValue("userPassword", password);

        ldapTemplate.bind(context);

        addUserToGroup(dn, role);
    }

    private void addUserToGroup(String dn, Role role) {
        String groupDn = getGroupDnForRole(role);

        ModificationItem[] modifications = new ModificationItem[] {
                new ModificationItem(DirContext.ADD_ATTRIBUTE,
                        new BasicAttribute("member", dn))
        };

        ldapTemplate.modifyAttributes(groupDn, modifications);
    }

    private String getGroupDnForRole(Role role) {
        String groupName = null;
        if (role.equals(Role.EMPLOYEE))
            groupName = "EMPLOYEES";
        else if (role.equals(Role.MANAGER))
            groupName = "MANAGERS";
        else if (role.equals(Role.COMPANY_MANAGER))
            groupName = "COMPANYMANAGERS";

        return LdapNameBuilder.newInstance("ou=groups")
                .add("cn", groupName)
                .build()
                .toString();
    }

    private String getUserDn(String username) {
        return LdapNameBuilder.newInstance("ou=users")
                .add("userid", username)
                .build()
                .toString();
    }
}
