package si.recek.wealthbuild.bankaccount.bussines.service;

import si.recek.wealthbuild.bankaccount.resource.model.BankAccountDTO;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {

    List<BankAccountDTO> getAllBankAccounts();

    BankAccountDTO createBankAccount(BankAccountDTO bankAccountDTO);

    Optional<BankAccountDTO> getBankAccountById(Long id);
}
