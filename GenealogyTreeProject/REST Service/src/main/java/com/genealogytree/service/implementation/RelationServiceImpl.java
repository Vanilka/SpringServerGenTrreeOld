package genealogytree.service.implementation;

import genealogytree.ExceptionManager.exception.*;
import genealogytree.persist.entity.modules.tree.FamilyEntity;
import genealogytree.persist.entity.modules.tree.MemberEntity;
import genealogytree.persist.entity.modules.tree.RelationsEntity;
import genealogytree.repository.RelationRepository;
import genealogytree.service.RelationService;
import genealogytree.service.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by vanilka on 30/12/2016.
 */
@Service
@Transactional
@ComponentScan("genealogytree")
public class RelationServiceImpl implements RelationService {

    @Autowired
    RelationRepository repository;

    @Autowired
    Validator validator;


    @Override
    public List<RelationsEntity> getRelations(FamilyEntity family) {
        return this.repository.findAllRelationsByFamily(family);
    }

    @Override
    public RelationsEntity getRelation(Long id) {
        return null;
    }

    @Override
    public RelationsEntity addRelation(RelationsEntity relation) throws IncorrectSex, NoValidMembers, TooManyNullFields, IncorrectStatus, IntegrationViolation {

        RelationsEntity result;
       /*
            Validations
        */
        //Validation of Sex
        if (!validator.validMemberSex(relation)) {
            throw new IncorrectSex();
        }

        //Validation of Members
        if (!validator.validMembers(relation)) {
            throw new NoValidMembers();
        }

        //Validation of Null Fields
        if (!validator.validNull(relation)) {
            throw new TooManyNullFields();
        }

        //Validation of Status
        if (!validator.validStatus(relation)) {
            throw new IncorrectStatus();
        }


        List<RelationsEntity> list = repository.findAllRelationsByFamily(relation.getOwnerF());
        //RelationWithSameSimLeftAndRightExist ?
        RelationsEntity existing = list.stream()
                .filter(p -> p.getSimLeft() != null && p.getSimRight() != null)
                .filter(p -> p.getSimLeft().equals(relation.getSimLeft()) && p.getSimRight().equals(relation.getSimRight()))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            //If Exist Merge
            RelationsEntity.merge(existing, relation);
            result = existing;
        } else {
            result = relation;
        }

        //Check Descendance
        if (!validator.validDescendence(list, relation, relation.getSimLeft())
                || !validator.validDescendence(list, relation, relation.getSimRight())) {
            throw new IntegrationViolation();
        }

        //Check Ascendance
        if (!validator.validAscendance(list, relation.getSimLeft(), relation.getSimLeft())
                || !validator.validAscendance(list, relation.getSimRight(), relation.getSimRight())) {
            throw new IntegrationViolation();
        }

        return this.repository.save(result);
    }


    @Override
    public RelationsEntity getRelationBySimLeftAndSimRight(MemberEntity simLeft, MemberEntity simRight) {
        if (simLeft == null && simRight == null) {
            return null;
        }
        return this.repository.findRelationBySimLeftAndSimRight(simLeft, simRight);
    }

    public static Predicate<RelationsEntity> SimLeftEqualsSimRight(RelationsEntity relation) {


       return null;
    }
}
