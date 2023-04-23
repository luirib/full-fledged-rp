package pt.aveiro.nariz.fullfledgedrp.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2LoginHandler,
        OAuth2UserService<OidcUserRequest, OidcUser> oidcLoginHandler) throws Exception {
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
                    .userService(oauth2LoginHandler)
                    .oidcUserService(oidcLoginHandler)))
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
