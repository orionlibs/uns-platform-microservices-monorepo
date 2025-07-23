package io.github.orionlibs.user.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class AuthorityDAOTest
{
    @Autowired
    private AuthorityDAO authorityDAO;


    @Test
    void saveAuthority()
    {
        // given
        AuthorityModel authority1 = saveAuthority("ADMINISTRATOR");
        // then
        assertThat(authority1).isNotNull();
        assertThat(authority1.getId().toString()).isEqualTo("hello");
        assertThat(authority1.getAuthority()).isEqualTo("ADMINISTRATOR");
        assertThat(authority1.getUser()).isNull();
    }


    private AuthorityModel saveAuthority(String authority)
    {
        AuthorityModel authorityModel = new AuthorityModel(authority);
        return authorityDAO.save(authorityModel);
    }
}
