package gentree.server.service.Implementation;

import gentree.common.configuration.enums.RelationType;
import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.RelationEntity;
import gentree.server.repository.RelationRepository;
import gentree.server.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

        relation = repository.saveAndFlush(relation);
        //Delete nulls
        removeOrphans(relation.getFamily().getId());
        return relation;
    }

    @Override
    public RelationEntity updateRelation(RelationEntity relation) {
        return repository.saveAndFlush(relation);
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
                repository.delete(relation);
            } else {
                relation.setLeft(null);
                relation.setRight(null);
                relation.setType(RelationType.NEUTRAL);
                repository.saveAndFlush(relation);
            }
        }

        return relation;
    }

    @Override
    public RelationEntity findRelationBysimLeftAndsimRight(MemberEntity simLeft, MemberEntity simRight) {
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


}
