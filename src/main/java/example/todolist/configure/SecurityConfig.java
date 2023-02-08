package example.todolist.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] ACCESS_ALLOW_APIS = {"/", "/health", "/login"};
    private final JwtAuthenticationEntryPoint authEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(
            JwtAuthenticationEntryPoint authEntryPoint,
            JwtRequestFilter jwtRequestFilter,
            JwtAccessDeniedHandler accessDeniedHandler
    ) {
        this.authEntryPoint = authEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(ACCESS_ALLOW_APIS)
                            .permitAll()
                        .requestMatchers(HttpMethod.POST, "/users")
                            .permitAll()
                        .requestMatchers(HttpMethod.GET, "/users")
                            .hasRole("ADMIN")
                        .anyRequest()
                            .authenticated()
                )
                .csrf().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(authEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
                    .and()
                .build();
    }
}
