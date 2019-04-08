package si.recek.wealthbuild.bankaccount.resource.resourceassembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;
import si.recek.wealthbuild.bankaccount.resource.endpoint.BankAccountController;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountVO;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class BankAccountResourceAssembler implements ResourceAssembler<BankAccountVO, Resource<BankAccountVO>> {
    @Override
    public Resource<BankAccountVO> toResource(BankAccountVO bankAccountVO) {
        return new Resource<BankAccountVO>(bankAccountVO,
                ControllerLinkBuilder.linkTo(methodOn(BankAccountController.class).getOneBankAccount(bankAccountVO.getId())).withSelfRel());
    }
}
