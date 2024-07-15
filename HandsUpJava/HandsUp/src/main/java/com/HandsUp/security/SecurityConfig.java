package com.HandsUp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http.csrf().disable()
    	.httpBasic().disable()
            .authorizeRequests()
                .requestMatchers("/static/asset/css/**", "/static/asset/js/**", "/static/asset/img/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/").permitAll() 
                .anyRequest().permitAll() 
            .and()
            .formLogin()
                .loginPage("/").permitAll() // Specifica la tua pagina di login personalizzata
                .defaultSuccessUrl("/index", true)
                .permitAll() // Permette a tutti di accedere alla pagina di login
            .and()
            .logout()
            .logoutUrl("/logout") // URL per effettuare il logout
            .logoutSuccessUrl("/") // URL a cui reindirizzare dopo il logout
            .invalidateHttpSession(true) // Invalida la sessione HTTP durante il logout
            .deleteCookies("JSESSIONID") // Cancella i cookie durante il logout
            .permitAll()
            .and()
            .exceptionHandling()
                .accessDeniedPage("/access-denied.html")
            .and()
            .sessionManagement()
                .sessionFixation().migrateSession()
                .maximumSessions(1)
                    .maxSessionsPreventsLogin(false);

        return http.build();
    }

}