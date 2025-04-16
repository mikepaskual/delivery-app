package com.mikepaskual.delivery.shared.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests ->
                requests.requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/login","/logout","/auth/register", "/auth/register/submit", "/h2-console/**", "/img/**", "/css/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated());

        http.formLogin(login -> {
            login.loginPage("/login")
                    .defaultSuccessUrl("/")
                    .permitAll();
        });

        http.requestCache(cache -> {
            HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
            requestCache.setMatchingRequestParameterName(null);
            cache.requestCache(requestCache);
        });

        http.logout(Customizer.withDefaults());

        http.csrf((csrf) -> {
            csrf.ignoringRequestMatchers("/h2-console/**");
        });

        http.headers((headers) ->
                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }
}
