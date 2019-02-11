package si.recek.wealthbuild.bankaccount.resource;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountVO;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class BankAccountResourceAssembler implements ResourceAssembler<BankAccountVO, Resource<BankAccountVO>> {
    @Override
    public Resource<BankAccountVO> toResource(BankAccountVO bankAccountVO) {
        return new Resource<BankAccountVO>(bankAccountVO,
                linkTo(methodOn(BankAccountController.class).getOne(bankAccountVO.getId())).withSelfRel());
    }
}
