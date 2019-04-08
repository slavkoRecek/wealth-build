package si.recek.wealthbuild.bankaccount.resource.resourceassembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import si.recek.wealthbuild.bankaccount.resource.model.BankAccountTransactionVO;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class BankTransactionResourceAssembler implements ResourceAssembler<BankAccountTransactionVO, Resource<BankAccountTransactionVO>> {
    @Override
    public Resource<BankAccountTransactionVO> toResource(BankAccountTransactionVO bankAccountTransactionVO) {
        return new Resource<BankAccountTransactionVO>(bankAccountTransactionVO);
    }
}
