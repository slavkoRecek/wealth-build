package si.recek.wealthbuild.bankaccount.bussines.service;

import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountCreationVO;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountTransactionVO;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountVO;
import si.recek.wealthbuild.util.GeneralEntityDtoMapper;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {

    List<BankAccountVO> getAllBankAccounts();

    BankAccountVO createBankAccount(BankAccountCreationVO bankAccountCreationVO);

    BankAccountVO getBankAccountById(Long id);

    BankAccount getBankAccount(Long id);
}
