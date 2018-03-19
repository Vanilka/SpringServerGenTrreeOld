package gentree.server.service.Implementation;

import gentree.common.configuration.enums.RelationType;
import gentree.exception.NotExistingRelationException;
import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.RelationEntity;
import gentree.server.repository.RelationRepository;
import gentree.server.service.RelationService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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

        System.out.println("Relation to persist : " + relation);
        updateOtherRelationToFalse(relation);
        relation = repository.saveAndFlush(relation);
        //Delete nulls
        removeOrphans(relation.getFamily().getId());
        return relation;
    }

    @Override
    public RelationEntity updateRelation(RelationEntity relation) throws NotExistingRelationException {
        if (!repository.existsById(relation.getId())) throw new NotExistingRelationException();
        updateOtherRelationToFalse(relation);
        relation = repository.saveAndFlush(relation);
        System.out.println("remove orphans");
        //removeOrphans(relation.getFamily().getId());
        return relation;
    }

    @Override
    public RelationEntity addNewBornRelation(MemberEntity memberEntity) {

        RelationEntity relationEntity = null;
        try {
            relationEntity = repository.saveAndFlush(new RelationEntity(null, null, memberEntity, memberEntity.getFamily()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return relationEntity;
    }

    @Override
    public RelationEntity deleteRelation(RelationEntity relation) {

        relation = repository.findById(relation.getId()).orElse(null);

        if (relation != null) {
            if (relation.getChildren() == null || relation.getChildren().isEmpty()) {
                System.out.println("relation children are null");
                repository.delete(relation);

            } else {
                relation.setLeft(null);
                relation.setRight(null);
                relation.setType(RelationType.NEUTRAL);
                System.out.println("children are not null");
                repository.save(relation);
            }
        }
        repository.flush();
        return relation;
    }


    @Override
    public RelationEntity findRelationBySimLeftAndSimRight(MemberEntity simLeft, MemberEntity simRight) {
        List<RelationEntity> list = repository.findByLeftAndRight(simLeft, simRight);

        RelationEntity target;

        if (list == null || list.isEmpty()) return null;

        if (list.size() > 1) {
            target = mergingRelationList(list);
             repository.flush();
        } else {
            target = list.get(0);

        }

        return target;
    }

    @Override
    public void forcedeleteRelation(RelationEntity relation) {
        relation = repository.findById(relation.getId()).orElse(null);

        if (relation != null) {
            repository.delete(relation);
            repository.flush();
        }
    }

    @Override
    public RelationEntity findRelationById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<RelationEntity> findAllRelationsByFamilyId(Long id) {
        List<RelationEntity> list = repository.findAllByFamilyId(id);
        list.forEach(element -> {
            element.getChildren().forEach(Hibernate::initialize);
        });
        return list;
    }

    @Override
    public void removeOrphans(Long familyID) {
        List<RelationEntity> listToDelete = repository.findAllByFamilyId(familyID)
                .stream()
                .filter(r -> (r.getLeft() == null || r.getRight() == null))
                .filter(r -> r.getChildren() == null || r.getChildren().isEmpty()).collect(Collectors.toList());
        System.out.println(listToDelete);
        repository.deleteAll(listToDelete);
        repository.flush();
    }


    @Override
    public List<RelationEntity> findBornRelations(MemberEntity child) {
        return repository.findBornRelatons(child);
    }

    private RelationEntity mergingRelationList(List<RelationEntity> mergingList) {
        RelationEntity toMerge = mergingList.get(0);

        mergingList.remove(toMerge);

        mergingList.forEach(element -> {
            element.getChildren().forEach(child -> {
                if (!toMerge.getChildren().contains(child)) toMerge.getChildren().add(child);
            });
        });

        repository.deleteAll(mergingList);
        return toMerge;
    }

    private void updateOtherRelationToFalse(RelationEntity entity) {

        if (entity.isActive() && !Objects.equals(entity.getType(), RelationType.NEUTRAL)) {
            List<RelationEntity> relationList = repository.findAllByFamilyId(entity.getFamily().getId());
            relationList.stream()
                    .filter(r -> !Objects.equals(entity, r))
                    .filter(r -> r.getLeft() != null && r.getRight() != null && r.getType() != RelationType.NEUTRAL)
                    .filter(r -> r.compareLeft(entity.getLeft())
                            || r.compareLeft(entity.getRight())
                            || r.compareRight(entity.getRight())
                            || r.compareRight(entity.getLeft()))
                    .forEach(r -> r.setActive(false));
        }
    }

    @Override
    public void saveandflush() {
        repository.flush();
    }
}
