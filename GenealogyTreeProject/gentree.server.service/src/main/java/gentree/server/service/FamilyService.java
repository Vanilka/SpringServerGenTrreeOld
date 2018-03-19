package gentree.server.service;

import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.OwnerEntity;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
public interface FamilyService {

    FamilyEntity addNewFamily(FamilyEntity family);

    FamilyEntity findFamilyById(Long id);

    FamilyEntity findFullFamilyById(Long id);

    List<FamilyEntity> findAllByOwner(OwnerEntity owner);

    FamilyEntity updateFamily(FamilyEntity family);
}
