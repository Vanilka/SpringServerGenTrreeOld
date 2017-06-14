package genealogytree.service.implementation;

import genealogytree.persist.entity.modules.administration.UserEntity;
import genealogytree.persist.entity.modules.tree.FamilyEntity;
import genealogytree.repository.FamilyRepository;
import genealogytree.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@ComponentScan("genealogytree")
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    FamilyRepository repository;

    public List<FamilyEntity> getAllFamilies() {
        List<FamilyEntity> list = this.repository.findAll();
        return list;
    }

    @Override
    public List<FamilyEntity> getFamillies(UserEntity user) {
        List<FamilyEntity> List = this.repository.findAllFamillyByOwner(user);
        return List;
    }

    public FamilyEntity addFamily(FamilyEntity family) {

        return this.repository.save(family);
    }

    @Override
    public FamilyEntity updateFamily(FamilyEntity family) {
        return this.repository.saveAndFlush(family);
    }

    @Override
    public FamilyEntity getFamily(Long id) {

        return this.repository.findOne(id);
    }

    @Override
    public boolean exist(FamilyEntity family) {
        return this.repository.exists(family.getId());
    }

}
