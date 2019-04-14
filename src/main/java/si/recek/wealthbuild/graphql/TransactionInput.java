package si.recek.wealthbuild.graphql;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionInput {
    Float credit;
    Float debit;
    String description;

    public BigDecimal getAmount(){
        return BigDecimal.valueOf(credit).subtract(BigDecimal.valueOf(debit));
    }
}
