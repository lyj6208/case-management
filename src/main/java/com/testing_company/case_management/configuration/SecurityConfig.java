package com.testing_company.case_management.configuration;

import com.testing_company.case_management.enums.Role;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/mlb/**","/api/**", "/swagger-ui/**","/v3/api-docs/**","/swagger-ui.html").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/api/user/**").hasRole(Role.HR_MEMBER.name())
//                        .requestMatchers(HttpMethod.GET,"/api/user/**").hasRole(Role.HR_MEMBER.name())
//                        .requestMatchers(HttpMethod.POST,"/api/jobLevel/**").hasRole(Role.HR_MEMBER.name())
//                        .requestMatchers(HttpMethod.POST,"/api/team/**").hasRole(Role.HR_MEMBER.name())
//                        .requestMatchers(HttpMethod.POST,"/api/department/**").hasRole(Role.HR_MEMBER.name())
//                        .requestMatchers(HttpMethod.POST,"/api/testCase/**").hasAnyRole(Role.CASE_HANDLE_MEMBER.name(),Role.LAB_MEMBER.name(),Role.LAB_LEADER.name(),Role.REPORT_MEMBER.name())
//                        .requestMatchers(HttpMethod.GET,"/api/testCase/**").hasAnyRole(Role.CASE_HANDLE_MEMBER.name(),Role.LAB_MEMBER.name(),Role.LAB_LEADER.name(),Role.REPORT_MEMBER.name())
//                        .requestMatchers(HttpMethod.POST,"/api/testItem/**").hasRole(Role.CASE_HANDLE_MEMBER.name())
//                        .requestMatchers(HttpMethod.GET,"/api/testItem/**").hasRole(Role.CASE_HANDLE_MEMBER.name())
//                        .requestMatchers(HttpMethod.POST,"/api/pointOfContact/**").hasRole(Role.CASE_HANDLE_MEMBER.name())
//                        .requestMatchers(HttpMethod.GET,"/api/pointOfContact/**").hasRole(Role.CASE_HANDLE_MEMBER.name())
//                        .requestMatchers(HttpMethod.POST,"/api/customer/**").hasRole(Role.CASE_HANDLE_MEMBER.name())
//                        .requestMatchers(HttpMethod.GET,"/api/customer/**").hasRole(Role.CASE_HANDLE_MEMBER.name())

                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
