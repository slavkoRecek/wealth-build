package si.recek.wealthbuild.bankaccount.resource.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class BankAccountDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String iban;

    private BigDecimal balance;

    @NotNull
    private BigDecimal initialBalance;

    private String name;

    private String accountType;
}
