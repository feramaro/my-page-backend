package dev.feramaro.mysiteapi.config;

import dev.feramaro.mysiteapi.config.middlewares.JwtAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;
    private final CorsConfiguration corsConfiguration;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfiguration(AuthenticationProvider authenticationProvider, JwtAuthFilter jwtAuthFilter, CorsConfiguration corsConfiguration, CorsConfigurationSource corsConfigurationSource) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
        this.corsConfiguration = corsConfiguration;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> {
                    auth
                            .requestMatchers("/auth/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/post").permitAll()
                            .anyRequest().authenticated();
                })
                .authenticationProvider(authenticationProvider)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sess -> sess.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ));

        return http.build();
    }
}
