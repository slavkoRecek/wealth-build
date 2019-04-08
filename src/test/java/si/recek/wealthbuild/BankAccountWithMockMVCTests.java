package si.recek.wealthbuild;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import si.recek.wealthbuild.bankaccount.bussines.model.AccountType;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankAccountRepository;
import si.recek.wealthbuild.bankaccount.bussines.service.BankAccountServiceImpl;
import si.recek.wealthbuild.bankaccount.resource.endpoint.BankAccountController;
import si.recek.wealthbuild.bankaccount.resource.resourceassembler.BankAccountResourceAssembler;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountCreationVO;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountVO;
import si.recek.wealthbuild.util.GeneralEntityDtoMapper;
import si.recek.wealthbuild.util.HalUtils;
import si.recek.wealthbuild.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BankAccountController.class)
@Import({BankAccountServiceImpl.class, GeneralEntityDtoMapper.class, BankAccountResourceAssembler.class})
public class BankAccountWithMockMVCTests {

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

    }

    @Test
    public void testGetAccount() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String url = "/bank-account/1";
        String iban = "SI56 123 22154 8754";
        String name = "My first account";
        insertBankAccount(iban, name);
        ResultActions result = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON));
        result
            .andExpect(status().isOk())
            .andReturn();
        String json = result.andReturn().getResponse().getContentAsString();
        assertAllBankAccountContent(json);
        result.andExpect(jsonPath("iban", is(iban)));
        result.andExpect(jsonPath("name", is(name)));
        result.andExpect(jsonPath("accountType", is("OPERATIONAL")));
        result.andExpect(jsonPath("initialBalance", is(20)));
        result.andExpect(jsonPath("balance", is(20)));
        result.andExpect(jsonPath("id", is(1 )));
        result.andExpect(jsonPath("_links.self.href", is("http://localhost/bank-account/1")));


    }

    private void assertAllBankAccountContent(String json) {
        String expected = "{\n" +
                "        \"iban\": \"SI56123221548754\",\n" +
                "        \"initialBalance\": 20,\n" +
                "        \"name\": \"My first account\",\n" +
                "        \"accountType\": \"OPERATIONAL\",\n" +
                "        \"id\": 1,\n" +
                "        \"balance\": 20,\n" +
                "        \"_links\": {\n" +
                "          \"self\": {\n" +
                "            \"href\": \"http://localhost/bank-account/1\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n";
        expected = StringUtils.removeIrrelevantCharacters(expected);
        assertThat(StringUtils.removeIrrelevantCharacters(json)).isEqualTo(expected);
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
        ObjectMapper objectMapper = HalUtils.getHalObjectMapper();
        Resource<BankAccountVO> bankAccountVOResource  = objectMapper.readValue(json, new TypeReference<Resource<BankAccountVO>>() {});
        assertThat(bankAccountVOResource.getContent().getIban()).isEqualTo(iban);
        assertThat(bankAccountVOResource.getLinks()).hasSize(1);

        assertThat(bankAccountVOResource.getLinks().get(0).getHref()).isEqualTo( "http://localhost/bank-account/1");
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
        when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.of(ba));
    }

}

