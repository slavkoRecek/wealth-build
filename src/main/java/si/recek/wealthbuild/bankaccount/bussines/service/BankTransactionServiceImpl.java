package si.recek.wealthbuild.bankaccount.bussines.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.recek.wealthbuild.bankaccount.bussines.model.AccountType;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.model.BankTransaction;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankAccountRepository;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankTransactionRepository;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountCreationVO;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountTransactionVO;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountVO;
import si.recek.wealthbuild.util.GeneralEntityDtoMapper;
import si.recek.wealthbuild.util.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class BankTransactionServiceImpl implements BankTransactionService {

    @Autowired
    GeneralEntityDtoMapper generalMapper;

    @Autowired
    BankTransactionRepository bankTransactionRepository;

    @Autowired
    BankAccountService bankAccountService;
    
    @Override
    @Transactional
    public List<BankAccountTransactionVO> getAllTransactionsForAccount(Long id) {
        BankAccount bankAccount = bankAccountService.getBankAccount(id);
        return generalMapper.mapList(this.getAllTransactionsForAccount(bankAccount), BankAccountTransactionVO.class);
    }

    public List<BankTransaction> getAllTransactionsForAccount(BankAccount bankAccount) {
        return bankTransactionRepository.getTransactionsForAccount(bankAccount.getId());
    }


}
