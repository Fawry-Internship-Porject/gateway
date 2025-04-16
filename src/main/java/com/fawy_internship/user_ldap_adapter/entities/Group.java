//package com.fawy_internship.user_ldap_adapter.entities;
//
//import org.springframework.ldap.odm.annotations.Attribute;
//import org.springframework.ldap.odm.annotations.Entry;
//import org.springframework.ldap.odm.annotations.Id;
//
//import javax.naming.Name;
//import java.util.List;
//
//@Entry(
//        base = "ou=groups",
//        objectClasses = {"groupOfNames"}
//)
//public class Group {
//    @Id
//    private Name dn;
//
//    @Attribute(name = "cn")
//    private String cn;
//
//    @Attribute(name = "member")
//    private List<String> members;
//
//    public Group() {
//    }
//
//    public Group(Name dn, String cn, List<String> members) {
//        this.dn = dn;
//        this.cn = cn;
//        this.members = members;
//    }
//
//    public Name getDn() {
//        return dn;
//    }
//
//    public void setDn(Name dn) {
//        this.dn = dn;
//    }
//
//    public String getCn() {
//        return cn;
//    }
//
//    public void setCn(String cn) {
//        this.cn = cn;
//    }
//
//    public List<String> getMembers() {
//        return members;
//    }
//
//    public void setMembers(List<String> members) {
//        this.members = members;
//    }
//}
