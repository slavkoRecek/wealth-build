package si.recek.wealthbuild.bankaccount.bussines.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class BankTransaction {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime entryDate;

    private LocalDateTime bookingDate;

    private String description;

    private BigDecimal amount;

    @Setter(AccessLevel.PRIVATE)
    private BigDecimal balance;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name ="BANK_ACCOuNT_ID")
    private BankAccount bankAccount;

    @OneToOne
    private AssetTransfer transfer;



    private BankTransaction() {}

}
