package si.recek.wealthbuild;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountVO;
import si.recek.wealthbuild.util.BankAccountTestFactory;
import si.recek.wealthbuild.util.HalUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
public class BankAccountWithRestTemplateTests extends RestTemplateEnabledTest {

    @Autowired
    BankAccountTestFactory bankAccountTestFactory;
    @Before
    public void setUp() {
        TestTransaction.end();
        assertThat(TestTransaction.isActive()).isFalse();
    }

    @Test
    public void testGetAllAccounts() {
        String url = "/bank-accounts/";
        Resources<Resource<BankAccountVO>> resources = restTemplate.getForObject(generateURL(url), Resources.class);
        assertThat(resources.getContent().isEmpty());

        String iban = "SI56 123 22154 8754";
        bankAccountTestFactory.insertBankAccount(iban);
        ResponseEntity<Resources<Resource<BankAccountVO>>> resourcesEntity = halTemplate.exchange(generateURL(url), HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Resource<BankAccountVO>>>() {
        });
        assertThat(resourcesEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(resourcesEntity.getBody().getContent().size()).isEqualTo(1);
        BankAccountVO ba = resourcesEntity.getBody().getContent().stream().map(bankAccountVOResource -> bankAccountVOResource.getContent()).findFirst().get();

        assertThat(ba.getIban()).isEqualTo(iban);
    }


}

