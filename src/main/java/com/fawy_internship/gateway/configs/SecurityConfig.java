package com.fawy_internship.gateway.configs;

import com.fawy_internship.gateway.dtos.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
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
//                        .requestMatchers("/ldap/**").access((authentication, context) -> {
//                            String gatewayHeader = context.getRequest().getHeader("X-GATEWAY-AUTH");
//                            return "my-secret-token".equals(gatewayHeader)
//                                    ? new AuthorizationDecision(true)
//                                    : new AuthorizationDecision(false);
//                        })
                                .requestMatchers("/logout", "/login").permitAll()

                                // CYCLES
                                .requestMatchers("/cycles").hasRole("COMPANYMANAGERS")
                                .requestMatchers("/cycles/Asc").hasRole("COMPANYMANAGERS")
                                .requestMatchers("/cycles/Desc").hasRole("COMPANYMANAGERS")
                                .requestMatchers("/cycles/pass/**").hasRole("COMPANYMANAGERS")
                                .requestMatchers("/cycles/close/**").hasRole("COMPANYMANAGERS")
                                .requestMatchers("/cycles/open/**").hasRole("COMPANYMANAGERS")
                                .requestMatchers("/cycles/{id:[\\d]+}").hasAnyRole("COMPANYMANAGERS", "MANAGERS", "EMPLOYEES")
                                .requestMatchers("/cycles/Latest").hasAnyRole("COMPANYMANAGERS", "MANAGERS", "EMPLOYEES")


                                // === KPI ACCESS CONTROL ===
                                .requestMatchers("/kpis/{userId:[\\d]+}").hasRole("COMPANYMANAGERS")
                                .requestMatchers("/kpis/{id:[\\d]+}/cycle/{cycleId:[\\d]+}").hasRole("COMPANYMANAGERS")
                                .requestMatchers("/kpis/{kpiId:[\\d]+}/role/**").hasRole("COMPANYMANAGERS")
                                .requestMatchers("/kpis/{id:[\\d]+}").hasAnyRole("COMPANYMANAGERS", "MANAGERS", "EMPLOYEES")
                                .requestMatchers("/kpis/cycle/{cycleId:[\\d]+}").hasAnyRole("COMPANYMANAGERS", "MANAGERS", "EMPLOYEES")
                                .requestMatchers("/kpis").hasAnyRole("COMPANYMANAGERS", "MANAGERS", "EMPLOYEES")
                                .requestMatchers("/kpis/{id:[\\d]+}").hasRole("COMPANYMANAGERS")

                                // === OBJECTIVE ROUTES ===
                                .requestMatchers(HttpMethod.POST, "/objectives").hasRole("MANAGERS")
                                .requestMatchers(HttpMethod.PUT, "/objectives/{id:[\\d]+}").hasRole("MANAGERS")
                                .requestMatchers(HttpMethod.DELETE, "/objectives/{assignId:[\\d]+}/{objectiveId:[\\d]+}").hasRole("MANAGERS")
                                .requestMatchers(HttpMethod.GET, "/objectives/{id:[\\d]+}").hasAnyRole("COMPANYMANAGERS", "MANAGERS", "EMPLOYEES")
                                .requestMatchers(HttpMethod.PUT, "/objectives/state/{id:[\\d]+}/complete").hasRole("EMPLOYEES")

                                // === RATING ROUTES ===
                                .requestMatchers(HttpMethod.POST, "/ratings").hasRole("COMPANYMANAGERS")
                                .requestMatchers(HttpMethod.GET, "/ratings/{id:[\\d]+}").hasAnyRole("COMPANYMANAGERS", "MANAGERS", "EMPLOYEES")
                                .requestMatchers(HttpMethod.GET, "/ratings/kpi/{kpiId:[\\d]+}").hasAnyRole("COMPANYMANAGERS", "MANAGERS", "EMPLOYEES")
                                .requestMatchers(HttpMethod.GET, "/ratings/cycle/{cycleId:[\\d]+}").hasAnyRole("COMPANYMANAGERS", "MANAGERS", "EMPLOYEES")
                                .requestMatchers(HttpMethod.GET, "/ratings/ratedPerson/{ratedPersonId:[\\d]+}").hasAnyRole("COMPANYMANAGERS", "MANAGERS", "EMPLOYEES")
                                .requestMatchers(HttpMethod.GET, "/ratings/cycle/{cycleId:[\\d]+}/ratedPerson/{ratedPersonId:[\\d]+}").hasAnyRole("COMPANYMANAGERS", "MANAGERS", "EMPLOYEES")
                                .requestMatchers(HttpMethod.GET, "/ratings/cycle/rate/{cycleId:[\\d]+}").hasRole("COMPANYMANAGERS")
                                .requestMatchers(HttpMethod.DELETE, "/ratings/{id:[\\d]+}").hasAnyRole("COMPANYMANAGERS", "MANAGERS", "EMPLOYEES")

                                // === ROLE ROUTES ===
                                .requestMatchers(HttpMethod.POST, "/roles").hasAnyRole("COMPANYMANAGERS", "MANAGERS", "EMPLOYEES")
                                .requestMatchers(HttpMethod.GET, "/roles").hasAnyRole("COMPANYMANAGERS", "MANAGERS")
                                .requestMatchers(HttpMethod.GET, "/roles/{name}/{level}").hasAnyRole("COMPANYMANAGERS", "MANAGERS")
                                .requestMatchers(HttpMethod.GET, "/roles/{name}/{level}/kpis").hasRole("COMPANYMANAGERS")
                                .requestMatchers(HttpMethod.DELETE, "/roles/{id:[\\d]+}").hasRole("COMPANYMANAGERS")

                                // === REPORT ROUTES ===
                                .requestMatchers(HttpMethod.GET, "/api/reports/download/cycle/{id:[\\d]+}")
                                .hasRole("COMPANYMANAGERS")


                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .successHandler((request, response, authentication) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter().write(
                                    GenericResponse.builder()
                                            .status("success")
                                            .message("User authenticated")
                                            .build()
                                            .toString()
                            );
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter().write(
                                    GenericResponse.builder()
                                            .status("error")
                                            .message("Authentication failed")
                                            .build()
                                            .toString()
                            );
                        })
                )
                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter().write(
                                    GenericResponse.builder()
                                            .status("success")
                                            .message("Logout successful")
                                            .build()
                                            .toString()
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
                                    GenericResponse.builder()
                                            .status("error")
                                            .message("Incorrect username or password")
                                            .build()
                                            .toString()
                            );
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.getWriter().write(
                                    GenericResponse.builder()
                                            .status("error")
                                            .message("Access denied")
                                            .build()
                                            .toString()
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
