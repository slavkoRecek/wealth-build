package si.recek.wealthbuild.bankaccount.resource.model;

import lombok.Data;
import org.springframework.hateoas.core.Relation;

import java.math.BigDecimal;

@Data
@Relation(collectionRelation = "bankAccounts")
public class BankAccountVO extends AbstractBankAccountVO {

    private Long id;

    private BigDecimal balance;
}
