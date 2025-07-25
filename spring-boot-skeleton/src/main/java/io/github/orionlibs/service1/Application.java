package io.github.orionlibs.service1;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.github.orionlibs.core.api.GlobalExceptionHandler;
import io.github.orionlibs.core.document.json.JSONService;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = "io.github.orionlibs")
@Import(GlobalExceptionHandler.class)
@Configuration
public class Application extends SpringBootServletInitializer implements WebMvcConfigurer
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }


    @Bean(name = "apiObjectMapper")
    public ObjectMapper objectMapper()
    {
        ObjectMapper mapper = new Jackson2ObjectMapperBuilder().serializationInclusion(Include.NON_NULL)
                        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                                        SerializationFeature.FAIL_ON_EMPTY_BEANS,
                                        SerializationFeature.FAIL_ON_SELF_REFERENCES)
                        .build();
        return mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }


    @Bean
    @DependsOn("apiObjectMapper")
    public JSONService jsonService(ObjectMapper objectMapper)
    {
        return new JSONService(objectMapper);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS", "websocket", "ws")
                        .allowedHeaders("*");
    }
}