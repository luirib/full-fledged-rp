package pt.aveiro.nariz.fullfledgedrp.security;

import java.util.Collections;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
@Import(SecurityConfiguration.class)
public class TestSecurityConfiguration {

    @Bean
    OAuth2UserService<OidcUserRequest, OidcUser> oidcLoginHandler() {
        return new OidcUserService();
    }

    @Bean
    OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2LoginHandler() {
        return new DefaultOAuth2UserService();
    }

    @Bean
    UserDetailsService testUsers() {
        InMemoryUserDetailsManager testUsers = new InMemoryUserDetailsManager();
        testUsers.createUser(User.builder()
            .username("test")
            .password("test")
            .authorities(Collections.emptyList())
            .build());
        return testUsers;
    }
}
