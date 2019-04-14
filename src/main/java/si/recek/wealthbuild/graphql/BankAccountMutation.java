package si.recek.wealthbuild.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.model.BankTransaction;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankAccountRepository;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankTransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class BankAccountMutation implements GraphQLMutationResolver {

    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    BankTransactionRepository transactionRepository;

    public BankAccount createBankAccount(String name, String iban){
        BankAccount ba = new BankAccount(BigDecimal.ZERO);
        ba.setName(name);
        ba.setIban(iban);
        return bankAccountRepository.save(ba);
    }

    public BankTransaction addTransaction(Long accountId, TransactionInput transactionInput){
        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setAmount(transactionInput.getAmount());
        bankTransaction.setBookingDate(LocalDateTime.now());
        bankTransaction.setEntryDate(LocalDateTime.now());
        BankAccount bankAccount = bankAccountRepository.getOne(accountId);
        bankAccount.addTransaction(bankTransaction);
        transactionRepository.save(bankTransaction);
        return bankTransaction;
    }
}

