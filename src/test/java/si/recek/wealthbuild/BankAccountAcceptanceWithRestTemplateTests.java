package si.recek.wealthbuild;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountVO;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest
@Transactional
public class BankAccountAcceptanceWithRestTemplateTests {

    protected TestRestTemplate restTemplate;
    @Value("${local.server.port}")
    protected int port;
    @Autowired
    EntityManager entityManager;

    @PostConstruct
    public void initialize() {
        restTemplate = new TestRestTemplate();
    }

    protected String generateURL(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Before
    public void setUp(){
        TestTransaction.end();
        assertThat(TestTransaction.isActive()).isFalse();
    }

	@Test
    @Ignore
	public void testGetAllAccounts() {
        String url = "/bank-accounts/";
        ResponseEntity<BankAccountVO[]> baEntity = restTemplate.getForEntity (generateURL(url), BankAccountVO[].class);
        List<BankAccountVO> accountDTOS = Arrays.asList(baEntity.getBody());
        assertThat(accountDTOS).isEmpty();
        String iban = "SI56 123 22154 8754";
        insertBankAccount(iban);

        baEntity = restTemplate.getForEntity (generateURL(url), BankAccountVO[].class);
        accountDTOS = Arrays.asList(baEntity.getBody());
        assertThat(accountDTOS).hasSize(1);
        assertThat(accountDTOS.get(0).getIban()).isEqualTo(iban);
	}

	private void insertBankAccount(String iban) {
        TestTransaction.start();
        TestTransaction.flagForCommit();
        BankAccount ba = new BankAccount(new BigDecimal("0"));
        ba.setIban(iban);
        entityManager.persist(ba);
        TestTransaction.end();
    }

}

