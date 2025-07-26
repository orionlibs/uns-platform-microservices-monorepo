package io.github.orionlibs.documents;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration
{
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers("/health/**", "/api/**", "/v1/**").permitAll())
                        //.requestMatchers(HttpMethod.POST, ControllerUtils.baseAPIPath + "/documents/**").hasAuthority(DocumentUserAuthority.DOCUMENT_MANAGER.name())
                        //.requestMatchers(HttpMethod.PUT, ControllerUtils.baseAPIPath + "/documents/**").hasAuthority(DocumentUserAuthority.DOCUMENT_MANAGER.name())
                        //.requestMatchers(HttpMethod.DELETE, ControllerUtils.baseAPIPath + "/documents/**").hasAuthority(DocumentUserAuthority.DOCUMENT_MANAGER.name())
                        //.anyRequest().hasAuthority("USER"));
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }
}
