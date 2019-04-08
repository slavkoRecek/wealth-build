package si.recek.wealthbuild.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.transaction.TestTransaction;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

@Component
public class BankAccountTestFactory {

    @Autowired
    EntityManager entityManager;

    public Long insertBankAccount(String iban) {
        TestTransaction.start();
        TestTransaction.flagForCommit();
        BankAccount ba = new BankAccount(new BigDecimal("0"));
        ba.setIban(iban);
        entityManager.persist(ba);
        TestTransaction.end();
        return ba.getId();
    }
}
