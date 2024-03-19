package com.htn.blog.config;

import com.htn.blog.security.jwt.JwtEntryPoint;
import com.htn.blog.security.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    UserDetailsService userDetailsService;
    @Autowired
    private JwtEntryPoint jwtEntryPoint;
    @Autowired
    private JwtFilter jwtFilter;

    @Value("${blog.cors.url}")
    private String corsUrl;

    public SecurityConfig(UserDetailsService userDetailsService, JwtEntryPoint jwtEntryPoint, JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtEntryPoint = jwtEntryPoint;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                .requestMatchers("/image/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/api/v1/posts/post-view/**").permitAll()
                                .requestMatchers("/api/v1/contacts/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/posts/post-view/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/contacts/send-idea").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/contacts/send-contact").permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling( exception -> exception
                        .authenticationEntryPoint(jwtEntryPoint)
                ).sessionManagement( session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        http.cors(cors -> cors
                .configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of(corsUrl));
                    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    configuration.setAllowCredentials(true);
                    configuration.setMaxAge(3600L);
                    configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    return configuration;
                })
        );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
