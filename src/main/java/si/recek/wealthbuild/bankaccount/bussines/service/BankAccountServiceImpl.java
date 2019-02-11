package si.recek.wealthbuild.bankaccount.bussines.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import si.recek.wealthbuild.bankaccount.bussines.model.AccountType;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankAccountRepository;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountCreationVO;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountVO;
import si.recek.wealthbuild.util.GeneralEntityDtoMapper;

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
    public Optional<BankAccountVO> getBankAccountById(Long id) {
        return bankAccountRepository.findById(id).map(bankAccount -> generalMapper.map(bankAccount, BankAccountVO.class));
    }


}
