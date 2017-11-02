package gentree.server.service.validator;

import gentree.exception.AscendanceViolationException;
import gentree.exception.IncorrectStatusException;
import gentree.exception.NotExistingMemberException;
import gentree.exception.TooManyNullFieldsException;
import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.RelationEntity;

/**
 * Created by Martyna SZYMKOWIAK on 26/10/2017.
 */
public interface RelationValidator {

    /**
     * Check if Relation can be added to Family
     * @param relation
     * @param family
     * @return
     */
    boolean validate(RelationEntity relation, FamilyEntity family) throws AscendanceViolationException, TooManyNullFieldsException, IncorrectStatusException, NotExistingMemberException;


    boolean validateSimple(RelationEntity relation, FamilyEntity family);
}
