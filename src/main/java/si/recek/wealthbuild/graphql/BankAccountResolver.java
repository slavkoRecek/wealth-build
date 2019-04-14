package si.recek.wealthbuild.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.model.BankTransaction;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankTransactionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountResolver implements GraphQLResolver<BankAccount> {

    @Autowired
    BankTransactionRepository bankTransactionRepository;

    public List<BankTransaction> getTransactions(BankAccount bankAccount){
        return bankTransactionRepository.getTransactionsForAccount(bankAccount.getId());
    }
}
