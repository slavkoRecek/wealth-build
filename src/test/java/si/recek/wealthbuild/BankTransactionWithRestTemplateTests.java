package si.recek.wealthbuild;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountTransactionVO;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountVO;
import si.recek.wealthbuild.util.BankAccountTestFactory;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
public class BankTransactionWithRestTemplateTests extends RestTemplateEnabledTest {

    @Autowired
    BankAccountTestFactory bankAccountTestFactory;

    @Before
    public void setUp() {
        TestTransaction.end();
        assertThat(TestTransaction.isActive()).isFalse();
    }

    @Test
    public void testGetBankTransactions() {

        String iban = "SI56 123 22154 8754";
        Long bankAccountId = bankAccountTestFactory.insertBankAccount(iban);
        String url = "/bank-accounts/{id}/transactions";
        ResponseEntity<Resources<Resource<BankAccountTransactionVO>>> resourcesEntity = halTemplate.exchange(generateURL(url),HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Resource<BankAccountTransactionVO>>>() {}, bankAccountId);
        assertThat(resourcesEntity.getStatusCode().is2xxSuccessful());

    }


}

