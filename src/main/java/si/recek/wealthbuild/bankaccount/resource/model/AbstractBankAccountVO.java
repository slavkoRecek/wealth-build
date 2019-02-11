package si.recek.wealthbuild.bankaccount.resource.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public abstract class AbstractBankAccountVO {

    @NotNull
    protected String iban;

    @NotNull
    protected BigDecimal initialBalance;

    @NotNull
    protected String name;

    @NotNull
    protected String accountType;
}
