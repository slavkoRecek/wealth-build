package si.recek.wealthbuild;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountDTO;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.math.BigDecimal;
import java.util.ArrayList;
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
	public void testGetAllAccounts() {
        String url = "/bank-accounts/";
        ResponseEntity<BankAccountDTO[]> baEntity = restTemplate.getForEntity (generateURL(url), BankAccountDTO[].class);
        List<BankAccountDTO> accountDTOS = Arrays.asList(baEntity.getBody());
        assertThat(accountDTOS).isEmpty();
        String iban = "SI56 123 22154 8754";
        insertBankAccount(iban);

        baEntity = restTemplate.getForEntity (generateURL(url), BankAccountDTO[].class);
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

