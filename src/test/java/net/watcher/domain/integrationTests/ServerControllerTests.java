package net.watcher.domain.integrationTests;

import net.watcher.domain.entities.User;
import net.watcher.domain.responses.EmailLoginsResponse;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Integrations tests
 *
 * Please see the {@link User} class for true identity
 * @author Kostia
 *
 */
@ActiveProfiles("qa")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServerControllerTests {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test() {
        ResponseEntity<EmailLoginsResponse> responseEntity = restTemplate.getForEntity("http://localhost:38375/allLoginsAndEmails", EmailLoginsResponse.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        EmailLoginsResponse body = responseEntity.getBody();
        assertThat(body.getEmails().size() > 0, is(true));
        assertThat(body.getLogins().size() > 0, is(true));
    }
}
