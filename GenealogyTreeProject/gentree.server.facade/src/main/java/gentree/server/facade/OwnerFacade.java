package gentree.server.facade;

import gentree.server.dto.OwnerDTO;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 */
public interface OwnerFacade {


    OwnerDTO addNewOwner(OwnerDTO owner);
    List<OwnerDTO> findAllOwners();
    OwnerDTO findOwnerByLoginToAuthProcess(String login);
    OwnerDTO findOwnerByLogin(String login);
}
