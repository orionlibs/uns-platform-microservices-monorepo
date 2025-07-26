package io.github.orionlibs.documents;

import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration
{
    @Bean
    public JwtDecoder jwtDecoder(@Value("${jwt.secret}") String base64Secret)
    {
        SecretKey key = new SecretKeySpec(
                        Base64.getDecoder().decode(base64Secret),
                        SignatureAlgorithm.HS512.getJcaName()
        );
        return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS512).build();
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter()
    {
        JwtGrantedAuthoritiesConverter authConverter = new JwtGrantedAuthoritiesConverter();
        authConverter.setAuthoritiesClaimName("authorities");
        authConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authConverter);
        return converter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtDecoder jwtDecoder, JwtAuthenticationConverter authConverter) throws Exception
    {
        http.csrf(csrf -> {
                            csrf.disable();
                        })
                        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests(authorize -> authorize.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                                        .requestMatchers("/health/**", "/api/**", "/v1/**").permitAll()
                                        .anyRequest().authenticated())
                        .oauth2ResourceServer(oauth2 -> oauth2
                                        .jwt(jwt -> jwt
                                                        .decoder(jwtDecoder)
                                                        .jwtAuthenticationConverter(authConverter)
                                        )
                        );
        return http.build();
    }
}
