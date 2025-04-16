package com.fawy_internship.user_ldap_adapter.entities;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;

@Entry(
        base = "ou=users", objectClasses = { "user", "top" }
)
public class User {
    @Id
    private Name dn;

    @Attribute(name = "userid")
    private String username;

    @Attribute(name = "userPassword")
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.dn = LdapNameBuilder.newInstance("ou=users")
                .add("userid", username)
                .build();
        this.username = username;
        this.password = password;
    }

    public User(Name id, String username, String password) {
        this.dn = id;
        this.username = username;
        this.password = password;
    }

    public Name getDn() {
        return dn;
    }

    public void setDn(Name dn) {
        this.dn = dn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
