package si.recek.wealthbuild;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import si.recek.wealthbuild.bankaccount.bussines.model.AccountType;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankAccountRepository;
import si.recek.wealthbuild.bankaccount.bussines.service.BankAccountService;
import si.recek.wealthbuild.bankaccount.bussines.service.BankAccountServiceImpl;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountDTO;
import si.recek.wealthbuild.util.GeneralEntityDtoMapper;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@SpringBootTest
@WebMvcTest
@AutoConfigureMockMvc(secure = false )
@Import({BankAccountServiceImpl.class, GeneralEntityDtoMapper.class})
public class BankAccountAcceptanceWithMockMVCTests {

    @MockBean
    BankAccountRepository bankAccountRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    @InjectMocks
    BankAccountServiceImpl bankAccountService;

	@Test
	public void testGetAllAccounts() throws Exception {
        String url = "/bank-accounts/";
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
        String iban = "SI56 123 22154 8754";
        String name = "My first account";
        insertBankAccount(iban, name);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<BankAccountDTO> bankAccounts = mapper.readValue(json, new TypeReference<List<BankAccountDTO>>(){});
        assertThat(bankAccounts).hasSize(1);
        assertThat(bankAccounts.get(0).getIban()).isEqualTo(iban);
        assertThat(bankAccounts.get(0).getAccountType()).isEqualTo(AccountType.OPERATIONAL.toString());
        assertThat(bankAccounts.get(0).getBalance()).isEqualTo(new BigDecimal("20"));
        assertThat(bankAccounts.get(0).getInitialBalance()).isEqualTo(new BigDecimal("20"));

	}


    private void insertBankAccount(String iban, String name) {
        BankAccount ba = new BankAccount(new BigDecimal("20"));
        ba.setIban(iban);
        ba.setName(name);
        ba.setAccountType(AccountType.OPERATIONAL);
        bankAccountRepository.save(ba);
        List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(ba);
        when(bankAccountRepository.findAll()).thenReturn(bankAccounts);
    }

}

