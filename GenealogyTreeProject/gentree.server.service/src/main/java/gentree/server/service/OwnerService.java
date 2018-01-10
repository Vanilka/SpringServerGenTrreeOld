package gentree.server.service;

import gentree.server.domain.entity.OwnerEntity;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 */
public interface OwnerService {

    OwnerEntity addNewOwner(OwnerEntity owner);

    List<OwnerEntity> findAllOwners();

    OwnerEntity findOperatorByLogin(String login);
}
