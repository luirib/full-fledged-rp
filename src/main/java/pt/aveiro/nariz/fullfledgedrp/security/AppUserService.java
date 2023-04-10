package pt.aveiro.nariz.fullfledgedrp.security;

import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Value
public class AppUserService implements UserDetailsService {
    PasswordEncoder passwordEncoder;

    Map<String, AppUser> users = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.get(username);
    }

    @PostConstruct
    private void createHardCodedUsers() {
        var luis = AppUser.builder()
            .username("luis")
            .provider(LoginProvider.APP)
            .imageUrl("https://i.pravatar.cc/150?img=3")
            .password(passwordEncoder.encode("password"))
            .authorities(List.of(new SimpleGrantedAuthority("read")))
            .build();

        var toze = AppUser.builder()
            .username("toze")
            .provider(LoginProvider.APP)
            .password(passwordEncoder.encode("password"))
            .authorities(List.of(new SimpleGrantedAuthority("read")))
            .build();

        createUser(luis);
        createUser(toze);
    }

    private void createUser(AppUser user) {
        this.users.putIfAbsent(user.username, user);
    }

    OAuth2UserService<OidcUserRequest, OidcUser> oidcLoginHandler() {
        return userRequest -> {
            // The only OIDC provider is SwissID
            LoginProvider provider = getProvider(userRequest.getClientRegistration());
            OidcUserService delegate = new OidcUserService();
            //Here you can intercept the oidc authenticated user
            OidcUser oidcUser = delegate.loadUser(userRequest);
            return AppUser
                .builder()
                .username(oidcUser.getAttribute("given_name"))
                .name(oidcUser.getFullName())
                .email(oidcUser.getEmail())
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .userId(oidcUser.getSubject())
                .provider(provider)
                .attributes(oidcUser.getAttributes())
                .authorities(oidcUser.getAuthorities())
                .build();
        };
    }

    private LoginProvider getProvider(ClientRegistration registration) {
        return LoginProvider.valueOf(registration.getRegistrationId().toUpperCase());
    }

    OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2LoginHandler() {
        return userRequest -> {
            // The only OAuth2 provider is Github
            LoginProvider provider = getProvider(userRequest.getClientRegistration());
            DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
            //Here you can intercept the oauth2 authenticated user
            OAuth2User oAuth2User = delegate.loadUser(userRequest);
            return AppUser.builder()
                .provider(provider)
                .username(oAuth2User.getAttribute("login"))
                .name(oAuth2User.getAttribute("login"))
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .userId(oAuth2User.getName())
                .imageUrl(oAuth2User.getAttribute("avatar_url"))
                .attributes(oAuth2User.getAttributes())
                .authorities(oAuth2User.getAuthorities())
                .build();
        };
    }
}
