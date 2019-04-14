package si.recek.wealthbuild.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankAccountRepository;
import si.recek.wealthbuild.bankaccount.bussines.service.BankAccountService;
import si.recek.wealthbuild.util.ResourceNotFoundException;

import java.math.BigDecimal;

@Component
public class BankAccountQuery implements GraphQLQueryResolver {

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    BankAccountService bankAccountService;

    public Iterable<BankAccount> getBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public BankAccount getBankAccountByIban(String iban) {
        return bankAccountRepository.findByIban(iban);
    }

    public BankAccount getBankAccountByID(Long id){
        return bankAccountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id"));
    }

}
