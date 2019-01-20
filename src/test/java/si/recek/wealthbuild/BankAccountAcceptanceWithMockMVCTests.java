package si.recek.wealthbuild;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankAccountRepository;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountDTO;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false )
public class BankAccountAcceptanceWithMockMVCTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    BankAccountRepository bankAccountRepository;

	@Test
	public void testGetAllAccounts() throws Exception {
        String url = "/bank-accounts/";
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
        String iban = "SI56 123 22154 8754";
        String name = "My first account";
        insertBankAccount(iban, name);

        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"iban\":\"SI56 123 22154 8754\"}]"))
                .andExpect(jsonPath("$[0].iban").value(iban))
                .andExpect(jsonPath("$").isArray());

	}


    private void insertBankAccount(String iban, String name) {
        BankAccount ba = new BankAccount();
        ba.setIban(iban);
        ba.setName(name);
        ba.setInitialBalance(new BigDecimal("20"));
        bankAccountRepository.save(ba);

        bankAccountRepository.flush();
    }

}

