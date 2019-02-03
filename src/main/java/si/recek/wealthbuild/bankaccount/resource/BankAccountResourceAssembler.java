package si.recek.wealthbuild.bankaccount.resource;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountDTO;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class BankAccountResourceAssembler implements ResourceAssembler<BankAccountDTO, Resource<BankAccountDTO>> {
    @Override
    public Resource<BankAccountDTO> toResource(BankAccountDTO bankAccountDTO) {
        return new Resource<BankAccountDTO>(bankAccountDTO,
                linkTo(methodOn(BankAccountController.class).getOne(bankAccountDTO.getId())).withSelfRel());
    }
}
