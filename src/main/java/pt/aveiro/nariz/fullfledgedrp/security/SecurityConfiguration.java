package pt.aveiro.nariz.fullfledgedrp.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AppUserService appUserService) throws Exception {
        return http
            // Linking it to /templates/app-user/login.html
            .formLogin(c -> c.loginPage("/login")
                //Default is /login, using instead
                //  .loginProcessingUrl("authenticate"))
                //Default is username, using username
                //.usernameParameter("user")
                .defaultSuccessUrl("/user"))
            .oauth2Login(oc -> oc.loginPage("/login")
                .defaultSuccessUrl("/user")
                .userInfoEndpoint(ui -> ui
                    .userService(appUserService.oauth2LoginHandler())
                    .oidcUserService(appUserService.oidcLoginHandler())))
            .logout(c -> c.logoutSuccessUrl("/?logout"))
            .authorizeHttpRequests(c -> c
                .requestMatchers("/images/**", "/**.css").permitAll()
                .requestMatchers("/", "/login").permitAll()
                .anyRequest().authenticated())
            .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successLogger() {
        return event -> {
            log.info("success: {}", event.getAuthentication());
        };
    }
}
