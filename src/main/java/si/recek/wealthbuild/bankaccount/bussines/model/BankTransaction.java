package si.recek.wealthbuild.bankaccount.bussines.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    private BigDecimal balanceAfter;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name ="BANK_ACCOUNT_ID")
    private BankAccount bankAccount;

    @OneToOne
    private AssetTransfer transfer;

    public BankTransaction() {}

    public BigDecimal getCredit(){
        return amount.compareTo(BigDecimal.ZERO) > 0 ? amount : BigDecimal.ZERO;
    }

    public BigDecimal getDebit(){
        return amount.compareTo(BigDecimal.ZERO) > 0 ? BigDecimal.ZERO : amount.negate();
    }

    LocalDate getBookingDate(){
        return bookingDate.toLocalDate();
    }

}
