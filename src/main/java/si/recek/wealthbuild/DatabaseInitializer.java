package si.recek.wealthbuild;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.model.BankTransaction;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankAccountRepository;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankTransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankTransactionRepository transactionRepository;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 1; i++){
            createBankAccount();
        }
        System.out.println();
    }

    private void createBankAccount() {
        UUID uuid = UUID.randomUUID();
        BankAccount bankAccount = new BankAccount(BigDecimal.ZERO);
        bankAccount.setName("Account " + uuid.toString().subSequence(0, 10));
        bankAccount.setIban("SI56" + Long.valueOf(System.currentTimeMillis()).toString().subSequence(1, 12));
        bankAccountRepository.save(bankAccount);
        addTransactions(bankAccount);
    }

    private void addTransactions(BankAccount bankAccount) {
        Random r = new Random();
        int noOfTransactions = 5;
        IntStream.rangeClosed(1, noOfTransactions).forEach(value -> {
            BankTransaction bankTransaction = new BankTransaction();
            bankTransaction.setAmount(BigDecimal.valueOf(r.nextFloat()));
            bankTransaction.setBookingDate(LocalDateTime.now().minusDays(r.nextInt(50)));
            bankTransaction.setEntryDate(LocalDateTime.now().minusDays(r.nextInt(50)));
            bankAccount.addTransaction(bankTransaction);
            transactionRepository.save(bankTransaction);
            bankTransaction.setBankAccount(bankAccount);
            transactionRepository.save(bankTransaction);

        });

    }

}
