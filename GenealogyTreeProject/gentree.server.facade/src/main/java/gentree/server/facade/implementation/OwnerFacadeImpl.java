package gentree.server.facade.implementation;

import gentree.server.domain.entity.OwnerEntity;
import gentree.server.dto.OwnerDTO;
import gentree.server.dto.OwnerExtendedDTO;
import gentree.server.facade.OwnerFacade;
import gentree.server.facade.converter.ConverterToEntity;
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

    @Autowired
    ConverterToDTO converterToDTO;

    @Autowired
    ConverterToEntity converterToEntity;

    @Override
    public OwnerDTO addNewOwner(OwnerDTO owner) {
        OwnerEntity userAdded = ownerService.addNewOwner(converterToEntity.convert(owner));
        return converterToDTO.convert(userAdded, false);
    }

    @Override
    public List<OwnerDTO> findAllOwners() {
        return converterToDTO.convertOwnerList(ownerService.findAllOwners());
    }

    @Override
    public OwnerDTO findOwnerByLoginToAuthProcess(String login) {
        return converterToDTO.convertWithPassword(ownerService.findOperatorByLogin(login));
    }

    @Override
    public OwnerDTO findOwnerByLogin(String login) {
        return converterToDTO.convert(ownerService.findOperatorByLogin(login), false);
    }

    @Override
    public OwnerExtendedDTO findExtendedOwnerByLogin(String login) {
        return (OwnerExtendedDTO) converterToDTO.convert(ownerService.findOperatorByLogin(login), true);
    }
}
