package si.recek.wealthbuild.bankaccount.bussines.service;

import si.recek.wealthbuild.bankaccount.resource.model.BankAccountCreationVO;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountTransactionVO;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountVO;

import java.util.List;
import java.util.Optional;

public interface BankTransactionService {

    List<BankAccountTransactionVO> getAllTransactionsForAccount(Long id);
}
