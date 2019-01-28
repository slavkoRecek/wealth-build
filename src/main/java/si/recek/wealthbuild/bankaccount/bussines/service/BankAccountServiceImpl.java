package si.recek.wealthbuild.bankaccount.bussines.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import si.recek.wealthbuild.bankaccount.bussines.model.BankAccount;
import si.recek.wealthbuild.bankaccount.bussines.repository.BankAccountRepository;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountDTO;
import si.recek.wealthbuild.util.GeneralEntityDtoMapper;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService{

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    GeneralEntityDtoMapper generalMapper;
    @Override
    public List<BankAccountDTO> getAllBankAccounts() {

        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        return generalMapper.map(bankAccounts, BankAccountDTO.class);

    }
}
