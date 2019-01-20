package si.recek.wealthbuild.bankaccount.bussines.service;

import si.recek.wealthbuild.bankaccount.resource.model.BankAccountDTO;

import java.util.List;

public interface BankAccountService {

    List<BankAccountDTO> getAllBankAccounts();
}
