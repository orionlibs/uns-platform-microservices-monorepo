package io.github.orionlibs.document;

import io.github.orionlibs.core.api.GlobalExceptionHandler;
import io.github.orionlibs.core.event.EventPublisher;
import io.github.orionlibs.core.event.EventPublisher.EventPublisherFake;
import io.github.orionlibs.core.json.JSONObjectMapper;
import io.github.orionlibs.core.json.JSONService;
import io.github.orionlibs.core.observability.BuildInfo;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = "io.github.orionlibs")
@Configuration
@Import({GlobalExceptionHandler.class, KafkaProducerConfiguration.class})
public class Application extends SpringBootServletInitializer implements WebMvcConfigurer
{
    @Value("${version}")
    private String version;
    @Value("${environment}")
    private String environment;


    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }


    @Bean
    public JSONService jsonService(JSONObjectMapper objectMapper)
    {
        return new JSONService(objectMapper.getMapper());
    }


    @Bean
    public EventPublisher eventPublisherFake()
    {
        return new EventPublisherFake();
    }


    @Bean
    public BuildInfo buildInfo()
    {
        return new BuildInfo("build", version, environment);
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