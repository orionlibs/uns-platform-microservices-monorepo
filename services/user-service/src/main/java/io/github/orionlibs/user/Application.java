package io.github.orionlibs.user;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.github.orionlibs.core.api.GlobalExceptionHandler;
import io.github.orionlibs.core.document.json.JSONService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@SpringBootApplication
@Import(GlobalExceptionHandler.class)
@Configuration
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
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
}