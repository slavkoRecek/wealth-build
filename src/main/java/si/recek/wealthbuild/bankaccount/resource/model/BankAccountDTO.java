package si.recek.wealthbuild.bankaccount.resource.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountDTO {

    private String iban;

    private BigDecimal balance;

    private BigDecimal initialBalance;

    private String name;

    private String accountType;
}
