package si.recek.wealthbuild.bankaccount.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import si.recek.wealthbuild.bankaccount.bussines.service.BankAccountService;
import si.recek.wealthbuild.bankaccount.bussines.service.BankAccountServiceImpl;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountDTO;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class BankAccountController {

    @Autowired
    BankAccountService bankAccountService;

    @GetMapping(value = "/bank-accounts", produces = "application/json")
    public List<BankAccountDTO> getBankAccounts() {
        return bankAccountService.getAllBankAccounts();
    }
}
