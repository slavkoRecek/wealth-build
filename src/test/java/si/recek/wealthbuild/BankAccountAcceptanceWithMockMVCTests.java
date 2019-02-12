package si.recek.wealthbuild;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.modelmapper.internal.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import si.recek.wealthbuild.bankaccount.bussines.model.AccountType;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankAccountRepository;
import si.recek.wealthbuild.bankaccount.bussines.service.BankAccountService;
import si.recek.wealthbuild.bankaccount.bussines.service.BankAccountServiceImpl;
import si.recek.wealthbuild.bankaccount.resource.BankAccountResourceAssembler;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountCreationVO;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountVO;
import si.recek.wealthbuild.util.GeneralEntityDtoMapper;
import si.recek.wealthbuild.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@SpringBootTest
@WebMvcTest
@AutoConfigureMockMvc(secure = false)
@Import({BankAccountServiceImpl.class, GeneralEntityDtoMapper.class, BankAccountResourceAssembler.class})
public class BankAccountAcceptanceWithMockMVCTests {

    @MockBean
    BankAccountRepository bankAccountRepository;

    @Autowired
    @InjectMocks
    BankAccountServiceImpl bankAccountService;

    @Autowired
    private MockMvc mvc;

    @Before
    public void initMocks(){
        when(bankAccountRepository.save(any(BankAccount.class))).then((InvocationOnMock invocation) -> this.bankAccountWithId(invocation.getArgument(0)));
    }

    private BankAccount bankAccountWithId(BankAccount bankAccount) {
        bankAccount.setId(1L);
        return bankAccount;
    }



    @Test
    public void testGetAllAccounts() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String url = "/bank-accounts/";
        String iban = "SI56 123 22154 8754";
        String name = "My first account";
        insertBankAccount(iban, name);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        assertAllBankAccountsContent(json);
        /*Resources<Resource<BankAccountVO>> bankAccountResources = mapper.readValue(json, new TypeReference<Resources<Resource<BankAccountVO>>>() {
        });
        List<Resource<BankAccountVO>> resourceList = Lists.from(bankAccountResources.getContent().iterator());
        assertThat(resourceList).hasSize(1);
        assertThat(resourceList.get(0).getLink("self").toString()).isEqualTo("");
        List<BankAccountVO> bankAccounts = resourceList.stream().map(Resource::getContent).collect(Collectors.toList());
        assertThat(bankAccounts).hasSize(1);
        assertThat(bankAccounts.get(0).getIban()).isEqualTo(iban);
        assertThat(bankAccounts.get(0).getId()).isEqualTo(1L);
        assertThat(bankAccounts.get(0).getAccountType()).isEqualTo(AccountType.OPERATIONAL.toString());
        assertThat(bankAccounts.get(0).getBalance()).isEqualTo(new BigDecimal("20"));
        assertThat(bankAccounts.get(0).getInitialBalance()).isEqualTo(new BigDecimal("20"));*/

    }

    private void assertAllBankAccountsContent(String json) {

        String expected = "{\n" +
                "  \"_embedded\": {\n" +
                "    \"bankAccounts\": [\n" +
                "      {\n" +
                "        \"iban\": \"SI56123221548754\",\n" +
                "        \"initialBalance\": 20,\n" +
                "        \"name\": \"Myfirstaccount\",\n" +
                "        \"accountType\": \"OPERATIONAL\",\n" +
                "        \"id\": 1,\n" +
                "        \"balance\": 20,\n" +
                "        \"_links\": {\n" +
                "          \"self\": {\n" +
                "            \"href\": \"http://localhost/bank-account/1\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"_links\": {\n" +
                "    \"self\": {\n" +
                "      \"href\": \"http://localhost/bank-accounts\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        expected = StringUtils.removeIrrelevantCharacters(expected);
        assertThat(StringUtils.removeIrrelevantCharacters(json)).isEqualTo(expected);
    }

    @Test
    public void testBankAccountCreation() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String url = "/bank-accounts/";
        String iban = "SI56 123 22154 8754";
        String name = "My first account";

        BankAccountCreationVO baDTO = new BankAccountCreationVO();
        baDTO.setIban(iban);
        baDTO.setAccountType(AccountType.OPERATIONAL.toString());
        baDTO.setName(name);
        baDTO.setInitialBalance(new BigDecimal("50"));

        String requestBody = mapper.writeValueAsString(baDTO);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestBody.getBytes()))
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        assertCreatedBankAccountJSON(json);


//        assertThat(bankAccountResource.getLink("self").toString()).isEqualTo("fdgf");
//        BankAccountVO createdAccount = bankAccountResource.getContent();
//        assertThat(createdAccount.getInitialBalance()).isEqualTo(baDTO.getInitialBalance());
//        assertThat(createdAccount.getBalance()).isEqualTo(baDTO.getInitialBalance());
//        assertThat(createdAccount.getName()).isEqualTo(baDTO.getName());
//        assertThat(createdAccount.getAccountType()).isEqualTo(baDTO.getAccountType());
//        assertThat(createdAccount.getIban()).isEqualTo(baDTO.getIban());

    }

    private void assertCreatedBankAccountJSON(String json) {
        String expected = "{\n" +
                "  \"iban\": \"SI56 123 22154 8754\",\n" +
                "  \"initialBalance\": 50,\n" +
                "  \"name\": \"My first account\",\n" +
                "  \"accountType\": \"OPERATIONAL\",\n" +
                "  \"id\": 1,\n" +
                "  \"balance\": 50,\n" +
                "  \"_links\": {\n" +
                "    \"self\": {\n" +
                "      \"href\": \"http://localhost/bank-account/1\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        expected = StringUtils.removeIrrelevantCharacters(expected);
        assertThat(StringUtils.removeIrrelevantCharacters(json)).isEqualTo(expected);
    }


    private void insertBankAccount(String iban, String name) {
        BankAccount ba = new BankAccount(new BigDecimal("20"));
        ba.setId(1L);
        ba.setIban(iban);
        ba.setName(name);
        ba.setAccountType(AccountType.OPERATIONAL);
        List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(ba);
        when(bankAccountRepository.findAll()).thenReturn(bankAccounts);
    }

}

