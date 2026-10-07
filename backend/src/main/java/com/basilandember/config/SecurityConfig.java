package com.basilandember.config;

import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;

@Configuration
public class SecurityConfig {
    @Bean UserDetailsService users(@Value("${app.admin.username}") String username,@Value("${app.admin.password}") String password) {
        if(password.length()<8) throw new IllegalArgumentException("ADMIN_PASSWORD must contain at least 8 characters.");
        return new InMemoryUserDetailsManager(User.withUsername(username).password(new BCryptPasswordEncoder().encode(password)).roles("ADMIN").build());
    }
    @Bean org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean SecurityFilterChain security(HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/foods","/api/foods/**","/api/health").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/cart/quote").permitAll()
                .requestMatchers("/error").permitAll().anyRequest().hasRole("ADMIN"))
            .httpBasic(b -> b.authenticationEntryPoint((req,res,e) -> {res.setStatus(401); res.setContentType("application/json"); res.getWriter().write("{\"message\":\"Please sign in with valid administrator credentials.\"}");}))
            .build();
    }
    @Bean CorsConfigurationSource cors(@Value("${app.cors.origins}") String origins) {
        var config=new CorsConfiguration(); config.setAllowedOrigins(Arrays.asList(origins.split(",")));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization","Content-Type")); config.setMaxAge(3600L);
        var source=new UrlBasedCorsConfigurationSource(); source.registerCorsConfiguration("/**",config); return source;
    }
}
