package com.example.calendarize.config;


import com.example.calendarize.filter.JwtGeneratorFilter;
import com.example.calendarize.filter.JwtValidatorFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private final EmailPwdAuthenticationProvider authenticationProvider;

    @Autowired
    public AppSecurityConfig(@Lazy EmailPwdAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        /*CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration config = new CorsConfiguration();
                                config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
                                config.setAllowedMethods(Collections.singletonList("*"));
                                config.setAllowCredentials(true);
                                config.setAllowedHeaders(Collections.singletonList("*"));
                                config.setExposedHeaders(Arrays.asList("Authorization"));
                                config.setMaxAge(3600L);
                                return config;
                            }
                        })).csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));*/
        http
                .cors(co -> co.disable())
                .csrf(cs->cs.disable())
               // .addFilterBefore(new JwtValidatorFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JwtGeneratorFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        request ->
                                request.requestMatchers("/api/auth/**").permitAll()
                                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())

        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        return authenticationManagerBuilder.build();
    }
}

