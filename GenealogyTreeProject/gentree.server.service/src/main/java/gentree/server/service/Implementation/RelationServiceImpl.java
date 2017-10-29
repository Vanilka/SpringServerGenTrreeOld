package gentree.server.service.Implementation;

import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.RelationEntity;
import gentree.server.repository.RelationRepository;
import gentree.server.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Service
public class RelationServiceImpl implements RelationService {

    @Autowired
    RelationRepository repository;

    @Override
    public RelationEntity addNewRelation(RelationEntity relation) {
        relation = repository.saveAndFlush(relation);
        //Delete nulls
        removeOrphans(relation.getFamily().getId());
        return relation;
    }

    @Override
    public RelationEntity addNewBornRelation(MemberEntity memberEntity) {

        RelationEntity relationEntity = null;
        try {
            System.out.println("Member  : " +memberEntity);
            System.out.println("MemberEntity family : " +memberEntity.getFamily());
            relationEntity = repository.saveAndFlush(new RelationEntity(null, null, memberEntity, memberEntity.getFamily()));
        } catch (Exception e) {
            System.out.println("Add  born Relation error");
            e.printStackTrace();
        }

        return relationEntity;
    }

    @Override
    public List<RelationEntity> findAllRelationsByFamilyId(Long id) {
        return repository.findAllByFamilyId(id);
    }

    @Override
    public void removeOrphans(Long familyID) {
        List<RelationEntity> listToDelete = repository.findAllByFamilyId(familyID)
                .stream()
                .filter(r -> (r.getLeft() == null || r.getRight() == null))
                .filter(r -> r.getChildren() == null || r.getChildren().isEmpty()).collect(Collectors.toList());
        repository.deleteAll(listToDelete);
        repository.flush();
    }

}
