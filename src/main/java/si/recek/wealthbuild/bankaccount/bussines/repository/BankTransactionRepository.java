package si.recek.wealthbuild.bankaccount.bussines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.model.BankTransaction;

import java.util.List;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

    @Query("SELECT t FROM BankTransaction t WHERE t.bankAccount.id = :id ORDER BY t.id desc")
    List<BankTransaction> getTransactionsForAccount(Long id);
}
