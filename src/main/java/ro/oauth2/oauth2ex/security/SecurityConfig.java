package ro.oauth2.oauth2ex.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(registry -> registry
                .requestMatchers("/", "/webjars/**").permitAll()
                .anyRequest().authenticated())
                .oauth2Login(
                        oauth2 -> oauth2
                                .loginPage("/oauth2/authorization/github")
                                .defaultSuccessUrl("/user", true)) // Redirect after successful login)
                // if you uncomment will cancel the .loginPage *** !!!!!
                // .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(new
                // HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .build();
    }
}
