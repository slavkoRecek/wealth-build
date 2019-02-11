package si.recek.wealthbuild.bankaccount.resource.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class BankAccountVO extends AbstractBankAccountVO {

    private Long id;

    private BigDecimal balance;
}
