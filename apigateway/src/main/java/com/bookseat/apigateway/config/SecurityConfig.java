package com.bookseat.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity servletSecurity) {
        servletSecurity.csrf(csrf -> csrf.disable())
            .authorizeExchange(exchanges ->
                    exchanges.pathMatchers(HttpMethod.GET).permitAll()
                            .pathMatchers("/eureka/**").permitAll()
                            .pathMatchers("/h2-console/**").permitAll()
                            .anyExchange().authenticated())
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(Customizer.withDefaults()));

        return servletSecurity.build();
    }
}
