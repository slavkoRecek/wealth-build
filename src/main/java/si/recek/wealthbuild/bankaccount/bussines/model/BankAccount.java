package si.recek.wealthbuild.bankaccount.bussines.model;




import lombok.*;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankTransactionRepository;
import si.recek.wealthbuild.util.AutowireHelper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class BankAccount {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String iban;

    private String name;

    @Setter(AccessLevel.PRIVATE)
    private BigDecimal initialBalance;

    @Setter(AccessLevel.PRIVATE)
    private BigDecimal balance;

    private AccountType accountType;


    private BankAccount() {}

    public BankAccount(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
        balance = initialBalance;
    }

    public void addTransaction(BankTransaction bankTransaction) {
        balance = balance.add(bankTransaction.getAmount());
    }

}
