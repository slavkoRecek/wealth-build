package si.recek.wealthbuild.bankaccount.bussines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
