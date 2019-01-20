package si.recek.wealthbuild.bankaccount.bussines.model;




import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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

    private BigDecimal initialBalance;

    @Setter(AccessLevel.PRIVATE)
    private BigDecimal balance;

    public BankAccount() {
        initialBalance = BigDecimal.ZERO;
        balance = BigDecimal.ZERO;
    }
}
