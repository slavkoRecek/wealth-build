package si.recek.wealthbuild.bankaccount.resource.model;

import lombok.Data;
import org.springframework.hateoas.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BankAccountTransactionVO {

    private Long id;

    private LocalDateTime entryDate;

    private LocalDateTime bookingDate;

    private String description;

    private BigDecimal amount;

    private BigDecimal balanceAfter;
}
