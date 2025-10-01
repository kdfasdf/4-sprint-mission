package com.sprint.mission.discodeit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.constant.AuthErrorCode;
import com.sprint.mission.discodeit.entity.Role;
import com.sprint.mission.discodeit.exception.ErrorResponse;
import com.sprint.mission.discodeit.security.HttpStatusReturningLogoutSuccessHandler;
import com.sprint.mission.discodeit.security.LoginFailureHandler;
import com.sprint.mission.discodeit.security.LoginSuccessHandler;
import com.sprint.mission.discodeit.security.SpaCsrfTokenRequestHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final HttpStatusReturningLogoutSuccessHandler httpStatusReturningLogoutSuccessHandler;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers( "/*","/assets/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/users").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/auth/logout").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/auth/csrf-token").permitAll()
                                .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()

                                .requestMatchers(HttpMethod.POST,"/api/channels/public").hasAnyRole("CHANNEL_MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.POST,"/api/channels/private").hasAnyRole("CHANNEL_MANAGER","ADMIN")
                                .requestMatchers(HttpMethod.PATCH,"/api/channels/").hasAnyRole("CHANNEL_MANAGER","ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/channels/").hasAnyRole("CHANNEL_MANAGER","ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/api/auth/me").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            if (request.getRequestURI().startsWith("/api/")) {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.setContentType("application/json;charset=UTF-8");
                                response.getWriter().write(
                                        objectMapper.writeValueAsString(ErrorResponse.of(AuthErrorCode.FORBIDDEN))
                                );
                            } else {
                                response.sendRedirect("/");
                            }
                        })
                )
                .csrf(csrf ->
                        csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler()))
                .formLogin(Customizer.withDefaults())
                .formLogin(login -> login
                        .loginPage("/")
                        .loginProcessingUrl("/api/auth/login")
                        .failureUrl("/")
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                )
                .logout(
                        logout -> logout
                        .logoutUrl("/api/auth/logout")
                                .logoutSuccessHandler(httpStatusReturningLogoutSuccessHandler)
                )
                .build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
//        RoleHierarchyImpl hierarchy  = new RoleHierarchyImpl();
//
//        hierarchy.setHierarchy(
//                "ROLE_ADMIN>ROLE_CHANNEL_MANAGER\n" +         //한 줄단위로 하지 않는 경우 RoleHierarchyImpl이 파싱 제대로 못함
//                        "ROLE_CHANNEL_MANAGER>ROLE_USER"
//        );
//        return hierarchy;

        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role(Role.ADMIN.name())
                .implies(Role.CHANNEL_MANAGER.name(), Role.USER.name())

                .role(Role.CHANNEL_MANAGER.name())
                .implies(Role.USER.name())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
