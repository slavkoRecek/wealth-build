package si.recek.wealthbuild;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestTemplate;
import si.recek.wealthbuild.util.HalUtils;

import javax.annotation.PostConstruct;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class RestTemplateEnabledTest {

    @Value("${local.server.port}")
    protected int port;

    protected TestRestTemplate restTemplate;
    protected RestTemplate halTemplate;

    @PostConstruct
    public void initialize() {
        restTemplate = new TestRestTemplate();
        halTemplate = HalUtils.getRestTemplateWithHalMessageConverter();
    }

    protected String generateURL(String uri) {
        return "http://localhost:" + port + uri;
    }
}
