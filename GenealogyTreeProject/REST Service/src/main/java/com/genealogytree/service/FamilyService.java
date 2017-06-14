package genealogytree.service;

import genealogytree.ExceptionManager.exception.NotFoundFamilyException;
import genealogytree.persist.entity.modules.administration.UserEntity;
import genealogytree.persist.entity.modules.tree.FamilyEntity;

import java.util.List;

public interface FamilyService {


    List<FamilyEntity> getAllFamilies();

    List<FamilyEntity> getFamillies(UserEntity user);

    FamilyEntity addFamily(FamilyEntity family);

    FamilyEntity updateFamily(FamilyEntity family);

    FamilyEntity getFamily(Long id);

    boolean exist(FamilyEntity family);


}
