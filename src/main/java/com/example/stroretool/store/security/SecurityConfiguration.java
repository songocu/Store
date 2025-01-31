package com.example.stroretool.store.security;

import com.example.stroretool.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Profile("security-enabled")
public class SecurityConfiguration {

    @Autowired
    private UserRepository userRepository;

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user").password("user").roles("USER").build());
//        manager.createUser(User.withUsername("admin").password("admin").roles("ADMIN").build());
//        return manager;
//    }


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByName(username)
                .map(user -> User.withUsername(user.getName())
                        .password(user.getPassword())
                        .roles(user.getRole())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    //Best practice is to use a password encoder to not save the original password in the database
    //This is just for demonstration purposes
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/h2-console").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/products/home").permitAll()
                .requestMatchers("/api/products/modify/**").hasRole("ADMIN")
                .requestMatchers("/api/products/info/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
        ).formLogin(withDefaults());

        return http.build();
    }
}