package si.recek.wealthbuild.bankaccount.resource.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import si.recek.wealthbuild.bankaccount.bussines.service.BankTransactionServiceImpl;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountTransactionVO;
import si.recek.wealthbuild.bankaccount.resource.resourceassembler.BankTransactionResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/bank-accounts")
@Slf4j
public class BankTransactionController {

    @Autowired
    BankTransactionResourceAssembler transactionResourceAssembler;

    @Autowired
    BankTransactionServiceImpl bankTransactionService;


    @GetMapping(value = "/{id}/transactions")
    public Resources<Resource<BankAccountTransactionVO>> getBankTransactions(@PathVariable Long id) {
        List<Resource<BankAccountTransactionVO>> resourceList = bankTransactionService.getAllTransactionsForAccount(id)
                .stream()
                .map(bankAccountTransactionVO ->  transactionResourceAssembler.toResource(bankAccountTransactionVO))
                .collect(Collectors.toList());
        Resources<Resource<BankAccountTransactionVO>> resources = new Resources<>(resourceList, linkTo(methodOn(BankTransactionController.class).getBankTransactions(id)).withSelfRel());
        return resources;
    }
}
