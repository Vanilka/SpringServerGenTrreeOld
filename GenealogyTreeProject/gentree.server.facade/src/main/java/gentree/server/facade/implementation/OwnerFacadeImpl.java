package gentree.server.facade.implementation;

import gentree.server.domain.entity.OwnerEntity;
import gentree.server.dto.OwnerDTO;
import gentree.server.facade.OwnerFacade;
import gentree.server.facade.converter.ConverterToDAO;
import gentree.server.facade.converter.ConverterToDTO;
import gentree.server.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 */
@Service
public class OwnerFacadeImpl implements OwnerFacade {

    @Autowired
    OwnerService ownerService;

    @Override
    public OwnerDTO addNewOwner(OwnerDTO owner) {
        OwnerEntity userAdded = ownerService.addNewOwner(ConverterToDAO.INSTANCE.convert(owner));
        return ConverterToDTO.INSTANCE.convert(userAdded);
    }

    @Override
    public List<OwnerDTO> findAllOwners() {
        return ConverterToDTO.INSTANCE.convertOwnerList(ownerService.findAllOwners());
    }

    @Override
    public OwnerDTO findOwnerByLoginToAuthProcess(String login) {
        return ConverterToDTO.INSTANCE.convertWithPassword(ownerService.findOperatorByLogin(login));
    }
}
