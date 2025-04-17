package com.fawy_internship.user_ldap_adapter.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    LdapConfig ldapConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/ldap/**").access((authentication, context) -> {
                            String gatewayHeader = context.getRequest().getHeader("X-GATEWAY-AUTH");
                            return "my-secret-token".equals(gatewayHeader)
                                    ? new AuthorizationDecision(true)
                                    : new AuthorizationDecision(false);
                        })
                        .requestMatchers("/logout", "/login").permitAll()
                        .requestMatchers("/users/**").hasRole("EMPLOYEES")
                        .requestMatchers("/employee/**").hasAnyRole("EMPLOYEES", "MANAGERS")
                        .requestMatchers("/manager/**").hasAnyRole("COMPANYMANAGERS", "MANAGERS")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .successHandler((request, response, authentication) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter().write(
                                    "{\"status\":\"success\", \"username\":\"" +
                                            authentication.getName() + "\"}"
                            );
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.getWriter().write(
                                    "{\"status\":\"error\", \"message\":\"Authentication failed\"}"
                            );
                        })
                )
                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter().write(
                                    "{\"status\":\"success\", \"message\":\"Logout successful\"}"
                            );
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.getWriter().write(
                                    "{\"status\":\"error\", \"message\":\"Unauthorized\"}"
                            );
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.getWriter().write(
                                    "{\"status\":\"error\", \"message\":\"Access denied\"}"
                            );
                        })
                )
                .authenticationManager(authenticationManager(ldapAuthenticationProvider()))
                .build();
    }

    @Bean
    public LdapAuthenticationProvider ldapAuthenticationProvider() {
        BindAuthenticator authenticator = new BindAuthenticator(ldapConfig.contextSource());
        authenticator.setUserDnPatterns(new String[]{"userid={0},ou=users"});

        DefaultLdapAuthoritiesPopulator authoritiesPopulator =
                new DefaultLdapAuthoritiesPopulator(ldapConfig.contextSource(), "ou=groups");

        authoritiesPopulator.setGroupSearchFilter("(member={0})");
        authoritiesPopulator.setGroupRoleAttribute("cn");
        authoritiesPopulator.setRolePrefix("ROLE_");

        return new LdapAuthenticationProvider(authenticator, authoritiesPopulator);
    }

    @Bean
    public AuthenticationManager authenticationManager(LdapAuthenticationProvider ldapAuthenticationProvider) {
        return new ProviderManager(List.of(ldapAuthenticationProvider));
    }
}
