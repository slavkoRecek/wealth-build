package si.recek.wealthbuild.bankaccount.bussines.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class AssetTransfer {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    BankTransaction sourceTransaction;

    @OneToOne
    BankTransaction targetTransaction;
}
