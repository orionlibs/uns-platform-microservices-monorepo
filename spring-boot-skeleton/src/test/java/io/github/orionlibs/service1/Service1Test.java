package io.github.orionlibs.service1;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest
{
    @Autowired Service1 service1;


    @BeforeEach
    void setup()
    {
        //
    }


    @Test
    void someTest1()
    {
        UserDetails user = service1.method1();
        assertThat(user.getPassword().isEmpty()).isFalse();
    }
}
