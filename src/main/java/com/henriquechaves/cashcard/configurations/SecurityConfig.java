package com.henriquechaves.cashcard.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
                    auth.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    public UserDetailsService usersForTest(){
        var sarah1 = User.builder()
                .username("sara1")
                .password(PasswordEncoderFactories
                        .createDelegatingPasswordEncoder()
                        .encode("1234"))
                .roles("OWNER")
                .build();

        var anyUser = User.builder()
                .username("any")
                .password("")
                .roles("NO-ONWER")
                .build();

        var ana2 = User.builder()
                .username("ana2")
                .password(PasswordEncoderFactories
                        .createDelegatingPasswordEncoder()
                        .encode("4321"))
                .roles("OWNER")
                .build();
        return new InMemoryUserDetailsManager(sarah1, anyUser, ana2);
    }
}
