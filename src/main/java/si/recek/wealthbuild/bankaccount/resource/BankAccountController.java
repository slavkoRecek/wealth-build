package si.recek.wealthbuild.bankaccount.resource;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.recek.wealthbuild.bankaccount.bussines.service.BankAccountService;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountCreationVO;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountVO;
import si.recek.wealthbuild.util.ResourceNotFoundException;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/")
@Slf4j
public class BankAccountController {

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    BankAccountResourceAssembler resourceAssembler;


    @GetMapping(value = "/bank-accounts")
    public Resources<Resource<BankAccountVO>> getAll() {
        List<Resource<BankAccountVO>> resourceList = bankAccountService.getAllBankAccounts().stream()
                                                        .map(bankAccountDTO -> resourceAssembler.toResource(bankAccountDTO))
                                                        .collect(Collectors.toList());
        Resources<Resource<BankAccountVO>> resources = new Resources<>(resourceList, linkTo(methodOn(BankAccountController.class).getAll()).withSelfRel());

        return resources;
    }

    @PostMapping(value = "/bank-accounts")
    public ResponseEntity<Resource<BankAccountVO>> createBankAccount(@RequestBody BankAccountCreationVO bankAccountVO) throws URISyntaxException {
        BankAccountVO result = bankAccountService.createBankAccount(bankAccountVO);
        Resource resource = resourceAssembler.toResource(result);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);

    }

    @GetMapping(value = "/bank-account/{id}")
    public Resource<BankAccountVO> getOne(@PathVariable Long id) {
        Resource resource = bankAccountService.getBankAccountById(id)
                                .map(bankAccountDTO -> resourceAssembler.toResource(bankAccountDTO))
                                .orElseThrow(() -> new ResourceNotFoundException(id));
        return resource;


    }
}
