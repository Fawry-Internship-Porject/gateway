package com.fawy_internship.user_ldap_adapter.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@EnableLdapRepositories
public class LdapConfig {
    @Value("${spring.ldap.urls}")
    private String url;
    @Value("${spring.ldap.base}")
    private String base;
    @Value("${spring.ldap.username}")
    private String username;
    @Value("${spring.ldap.password}")
    private String password;

    @Bean
    public LdapTemplate ldapTemplate() {
        LdapContextSource contextSource = new LdapContextSource();

        contextSource.setUrl(url);
        contextSource.setBase(base);
        contextSource.setUserDn(username);
        contextSource.setPassword(password);
        contextSource.afterPropertiesSet();

        return new LdapTemplate(contextSource);
    }
}
