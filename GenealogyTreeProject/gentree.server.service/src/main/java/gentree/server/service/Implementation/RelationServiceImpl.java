package gentree.server.service.Implementation;

import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.RelationEntity;
import gentree.server.repository.RelationRepository;
import gentree.server.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Service
@Transactional
public class RelationServiceImpl implements RelationService {

    @Autowired
    RelationRepository repository;

    @Override
    public RelationEntity addNewRelation(RelationEntity relation) {
        return repository.save(relation);
    }

    @Override
    public RelationEntity addNewBornRelation(MemberEntity memberEntity) {

        RelationEntity relationEntity = null;
        try {
            relationEntity = repository.saveAndFlush(new RelationEntity(null, null, memberEntity, memberEntity.getFamily()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("DONE");
        return relationEntity;
    }
}
