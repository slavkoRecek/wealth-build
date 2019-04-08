package si.recek.wealthbuild.bankaccount.bussines.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.recek.wealthbuild.bankaccount.bussines.model.AccountType;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankAccountRepository;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountCreationVO;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountTransactionVO;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountVO;
import si.recek.wealthbuild.util.GeneralEntityDtoMapper;
import si.recek.wealthbuild.util.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService{

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    GeneralEntityDtoMapper generalMapper;

    @Override
    public List<BankAccountVO> getAllBankAccounts() {

        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return generalMapper.mapList(bankAccounts, BankAccountVO.class);
    }

    @Override
    public BankAccountVO createBankAccount(BankAccountCreationVO bankAccountCreationVO) {
        BankAccount bankAccount = new BankAccount(bankAccountCreationVO.getInitialBalance());
        bankAccount.setAccountType(AccountType.valueOf(bankAccountCreationVO.getAccountType()));
        bankAccount.setName(bankAccountCreationVO.getName());
        bankAccount.setIban(bankAccountCreationVO.getIban());
        bankAccount = bankAccountRepository.save(bankAccount);
        return generalMapper.map(bankAccount, BankAccountVO.class);
    }

    @Override
    public BankAccountVO getBankAccountById(Long id) {
        return generalMapper.map(getBankAccount(id), BankAccountVO.class);
    }

    @Override
    public BankAccount getBankAccount(Long id) {
        return bankAccountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

}
