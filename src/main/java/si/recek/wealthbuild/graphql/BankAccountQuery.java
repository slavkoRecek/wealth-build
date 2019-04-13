package si.recek.wealthbuild.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankAccountRepository;

@Component
public class BankAccountQuery implements GraphQLQueryResolver {

    @Autowired
    BankAccountRepository bankAccountRepository;

    public Iterable<BankAccount> findBankAccounts() {
        return bankAccountRepository.findAll();
    }
}
