package io.github.orionlibs.documents;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration
{
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/health/**", "/api/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, ControllerUtils.baseAPIPath + "/documents/**")
                        .hasRole(DocumentUserAuthority.DOCUMENT_MANAGER.name())
                        .anyRequest().hasAuthority("USER"));
        //.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }
}
