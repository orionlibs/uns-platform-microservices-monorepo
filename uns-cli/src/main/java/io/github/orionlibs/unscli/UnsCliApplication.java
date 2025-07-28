package io.github.orionlibs.unscli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@Configuration
@CommandScan
public class UnsCliApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(UnsCliApplication.class, args);
    }
}
