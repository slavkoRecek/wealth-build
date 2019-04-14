package si.recek.wealthbuild;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankAccountRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 5; i++){
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
    }

}
