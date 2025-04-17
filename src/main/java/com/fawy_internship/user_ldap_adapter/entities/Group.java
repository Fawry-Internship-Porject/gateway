package com.fawy_internship.user_ldap_adapter.entities;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.util.Set;

@Entry(objectClasses = {"groupOfNames", "top"})
public class Group {
    @Id
    private Name dn;

    @Attribute(name = "cn")
    private String name;

    @Attribute(name = "member")
    private Set<Name> members;

    public Group() {
    }

    public Group(Name dn, String name, Set<Name> members) {
        this.dn = dn;
        this.name = name;
        this.members = members;
    }

    public Name getDn() {
        return dn;
    }

    public void setDn(Name dn) {
        this.dn = dn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Name> getMembers() {
        return members;
    }

    public void setMembers(Set<Name> members) {
        this.members = members;
    }
}
