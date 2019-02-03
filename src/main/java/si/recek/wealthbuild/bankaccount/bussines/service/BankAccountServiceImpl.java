package si.recek.wealthbuild.bankaccount.bussines.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import si.recek.wealthbuild.bankaccount.bussines.model.AccountType;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankAccountRepository;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountDTO;
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
    public List<BankAccountDTO> getAllBankAccounts() {

        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return generalMapper.mapList(bankAccounts, BankAccountDTO.class);
    }

    @Override
    public BankAccountDTO createBankAccount(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = new BankAccount(bankAccountDTO.getInitialBalance());
        bankAccount.setAccountType(AccountType.valueOf(bankAccountDTO.getAccountType()));
        bankAccount.setName(bankAccountDTO.getName());
        bankAccount.setIban(bankAccountDTO.getIban());
        bankAccount = bankAccountRepository.save(bankAccount);
        return generalMapper.map(bankAccount, BankAccountDTO.class);
    }

    @Override
    public Optional<BankAccountDTO> getBankAccountById(Long id) {
        return bankAccountRepository.findById(id).map(bankAccount -> generalMapper.map(bankAccount, BankAccountDTO.class));
    }


}
