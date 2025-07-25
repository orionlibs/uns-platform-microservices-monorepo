package io.github.orionlibs.user;

import io.github.orionlibs.user.authentication.JWTFilter;
import io.github.orionlibs.user.authentication.JWTService;
import io.github.orionlibs.user.authentication.PostAuthenticationChecks;
import io.github.orionlibs.user.authentication.PreAuthenticationChecks;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter.HeaderValue;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration
{
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public Customizer<CsrfConfigurer<HttpSecurity>> csrfCustomizer()
    {
        return csrf -> csrf.disable();
        /*return csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/**");*/
    }


    @Bean
    public Customizer<SessionManagementConfigurer<HttpSecurity>> sessionManagementCustomizer()
    {
        return http -> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Bean
    public Customizer<HeadersConfigurer<HttpSecurity>.CacheControlConfig> cacheControlCustomizer()
    {
        return http -> http.disable();
    }


    @Bean
    public Customizer<HeadersConfigurer<HttpSecurity>.ContentSecurityPolicyConfig> contentSecurityCustomizer()
    {
        return http -> http.policyDirectives("frame-src 'none'");
    }


    @Bean
    public Customizer<HeadersConfigurer<HttpSecurity>.HstsConfig> hstsCustomizer()
    {
        return http -> http.includeSubDomains(true).preload(true);
    }


    @Bean
    public Customizer<HeadersConfigurer<HttpSecurity>.XXssConfig> xssCustomizer()
    {
        return http -> http.headerValue(HeaderValue.ENABLED_MODE_BLOCK);
    }


    @Bean
    public Customizer<HeadersConfigurer<HttpSecurity>.ReferrerPolicyConfig> referrerPolicyCustomizer()
    {
        return http -> http.policy(ReferrerPolicy.STRICT_ORIGIN);
    }


    @Bean
    public Customizer<HeadersConfigurer<HttpSecurity>> headersCustomizer()
    {
        return http -> http.referrerPolicy(referrerPolicyCustomizer())
                        .xssProtection(xssCustomizer())
                        .httpStrictTransportSecurity(hstsCustomizer())
                        .contentSecurityPolicy(contentSecurityCustomizer())
                        .cacheControl(cacheControlCustomizer());
    }


    @Bean
    public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizeHttpRequestsCustomizer()
    {
        return http -> http.anyRequest().permitAll();
        /*return http -> http.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers("/**").permitAll()
                        .anyRequest().permitAll();*/
    }


    @Bean
    public Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer()
    {
        return cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            //config.setAllowedOrigins(Arrays.asList("*"));
            config.setAllowedOriginPatterns(Arrays.asList("*"));
            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS", "websocket", "ws"));
            config.setAllowedHeaders(Arrays.asList("*"));
            config.setAllowCredentials(true);
            config.setMaxAge(3600L);
            return config;
        });
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        JWTFilter jwtFilter = new JWTFilter(jwtService, userDetailsService);
        http.cors(corsCustomizer())
                        .csrf(csrfCustomizer())
                        .headers(headersCustomizer())
                        .sessionManagement(sessionManagementCustomizer())
                        .authorizeHttpRequests(authorizeHttpRequestsCustomizer())
                        .authenticationProvider(daoAuthenticationProvider())
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setHideUserNotFoundExceptions(false);
        authProvider.setPreAuthenticationChecks(new PreAuthenticationChecks());
        authProvider.setPostAuthenticationChecks(new PostAuthenticationChecks());
        return authProvider;
    }
}
