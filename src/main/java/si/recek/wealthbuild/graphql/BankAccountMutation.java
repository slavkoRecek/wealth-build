package si.recek.wealthbuild.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankAccountRepository;

import java.math.BigDecimal;

@Service
public class BankAccountMutation implements GraphQLMutationResolver {

    @Autowired
    BankAccountRepository bankAccountRepository;

    public BankAccount createBankAccount(String name, String iban){
        BankAccount ba = new BankAccount(BigDecimal.ZERO);
        ba.setName(name);
        ba.setIban(iban);
        return bankAccountRepository.save(ba);
    }
}

