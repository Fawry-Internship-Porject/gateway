package com.fawy_internship.user_ldap_adapter.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    LdapConfig ldapConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                //    .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize

                                .requestMatchers("/auth/**", "/login").permitAll()
                                .requestMatchers("/users", "/users/**").authenticated()
//                        .requestMatchers("/mngrs/**").hasRole("manager")
//                        .requestMatchers("/cmngrs/**").hasRole("companymanager")
//
//                        .anyRequest().authenticated()
                )
         //       .csrf(Customizer.withDefaults())
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Always create session
//                        .sessionFixation().migrateSession() // Prevent session fixation
//                        .maximumSessions(1) // Allow only 1 session per user
//                )
      //          .formLogin(withDefaults())
                .formLogin(form -> form
                        .loginProcessingUrl("/auth/login")
                        .successHandler((request, response, authentication) -> {
                        })
                        .failureHandler((request, response, exception) -> {
                            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Login failed");
                        })
                )
//                .logout(logout -> logout
//                        .logoutUrl("/auth/logout")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                )
//                .formLogin(form -> form
//                        .loginProcessingUrl("/auth/login")
//                        .successHandler((request, response, authentication) -> {
//                        })
//                )
            //    .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//                        .sessionFixation().migrateSession()
//                        .maximumSessions(1)
//                )
              //  .formLogin(withDefaults())
                .authenticationManager(authenticationManager(ldapAuthenticationProvider()))
//                .addFilterBefore(sessionAuthFilter(), AnonymousAuthenticationFilter.class) // Critical!
//                .anonymous(AbstractHttpConfigurer::disable)
                //  .authenticationManager(authenticationManager(ldapConfig.contextSource()))
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

//    @Bean
//    public SessionAuthFilter sessionAuthFilter() {
//        return new SessionAuthFilter();
//    }
//    @Bean
//    ActiveDirectoryLdapAuthenticationProvider authenticationProvider() {
//        return new ActiveDirectoryLdapAuthenticationProvider("example.com", "ldap://company.example.com/");
//    }
//
//    @Bean
//    LdapAuthoritiesPopulator authorities(BaseLdapPathContextSource contextSource) {
//        String groupSearchBase = "ou=groups";
//        DefaultLdapAuthoritiesPopulator authorities =
//                new DefaultLdapAuthoritiesPopulator(contextSource, groupSearchBase);
//        authorities.setGroupSearchFilter("member={0}");
//        return authorities;
//    }
//
//    @Bean
//    AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource) {
//        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
//        factory.setUserDnPatterns("userid={0},ou=users");
//        return factory.createAuthenticationManager();
//    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        return new LdapAuthenticationProvider();
//    }

//    @Bean
//    public LdapUserDetailsService ldapUserDetailsService() {
//        LdapUserSearch userSearch = new LdapUserSearch(
//                "ou=users", "(userid={0})", ldapTemplate);
//        LdapUserDetailsService ldapUserDetailsService = new LdapUserDetailsService(ldapUserSearch);
//        return ldapUserDetailsService;
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> UserDetailsImpl.build(userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found")), "employee");
//    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//
//        authenticationProvider.setUserDetailsService(userDetailsService());
//
//        return authenticationProvider;
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserDetailsServiceImpl();
//    }

//    @Autowired
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .ldapAuthentication()
//                .userDnPatterns("userid={0},ou=users")
//                .groupSearchBase(base)
//                .contextSource()
//                .url(url)
//                .and()
//                .passwordCompare()
//                .passwordAttribute("userPassword");
//    }


//    @Autowired
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .ldapAuthentication()
//                .userDnPatterns("uid={0},ou=people")
//                .groupSearchBase("ou=groups")
//                .contextSource()
//                .url("ldap://localhost:8389/dc=springframework,dc=org")
//                .and()
//                .passwordCompare()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .passwordAttribute("userPassword");
//    }

}

//@Configuration
//public class WebSecurityConfig {

//}
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//public class SecurityConfig {
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new InMemoryUserDetailsManager(
//                User.withUsername("ldapuser")
//                        .password("{noop}password") // Use {noop} for plain text passwords
//                        .roles("USER")
//                        .build()
//        );
//    }
//
//    @Bean
//    public AuthenticationProvider ldapAuthenticationProvider() {
//        return new ActiveDirectoryLdapAuthenticationProvider(
//                "example.com",
//                "ldap://localhost:8389/"
//        );
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
//        return http.build();
//    }
//}
