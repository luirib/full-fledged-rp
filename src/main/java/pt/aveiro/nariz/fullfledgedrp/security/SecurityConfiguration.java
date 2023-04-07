package pt.aveiro.nariz.fullfledgedrp.security;

import java.util.Collections;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .formLogin(Customizer.withDefaults())
            .oauth2Login(Customizer.withDefaults())
            .authorizeHttpRequests(c -> c.anyRequest().authenticated())
            .build();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService inMemoryUserDetailsService() {
        InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();
        UserDetails luis = new User("luis", passwordEncoder().encode("password"), Collections.emptyList());
        UserDetails toze = User.builder()
                .username("toze")
                .password(passwordEncoder()
                .encode("password"))
                .roles("USER")
            .build();

        users.createUser(luis);
        users.createUser(toze);

        return users;
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successLogger() {
        return event -> {
            log.info("success: {}", event.getAuthentication());
        };
    }
}
