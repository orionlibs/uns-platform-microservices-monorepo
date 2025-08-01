package io.github.orionlibs.unscli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.command.annotation.CommandScan;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@Configuration
@CommandScan
public class UnsCliApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(UnsCliApplication.class, args);
    }


    @Bean
    public WebClient unsWebClient()
    {
        //point to your API host; you can also externalize this to application.properties
        return WebClient.builder()
                        .baseUrl("http://localhost:8080")
                        .build();
    }
}
