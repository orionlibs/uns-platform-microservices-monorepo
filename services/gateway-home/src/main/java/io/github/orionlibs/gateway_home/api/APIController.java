package io.github.orionlibs.gateway_home.api;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController
{
    @GetMapping("/v1/home")
    public ResponseEntity<String> welcome()
    {
        return ok("Welcome to this CI/CD test");
    }
}