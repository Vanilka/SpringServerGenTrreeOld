package gentree.server.dispatchers;

import gentree.exception.*;
import gentree.server.domain.entity.RelationEntity;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.OwnerExtendedDTO;
import gentree.server.dto.RelationDTO;
import gentree.server.facade.FamilyFacade;
import gentree.server.facade.OwnerFacade;
import gentree.server.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * Created by Martyna SZYMKOWIAK on 17/10/2017.
 */
@RestController
@RequestMapping("/relations")
public class RelationMapper {

    @Autowired
    FamilyFacade familyFacade;

    @Autowired
    OwnerFacade ownerFacade;

    @Autowired
    RelationRepository repository;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<RelationEntity>> getRelations() {
        List<RelationEntity> list = repository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Add Relation
     *
     * @param relation
     * @param auth
     * @return
     * @throws FamilyAccessDeniedException
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity<List<RelationDTO>> addRelation(@RequestBody RelationDTO relation, Authentication auth)
            throws FamilyAccessDeniedException, TooManyNullFieldsException, AscendanceViolationException, IncorrectStatusException, NotExistingMemberException {
        if (!isOwnerOf(relation, auth)) throw new FamilyAccessDeniedException();
        List<RelationDTO> list = familyFacade.addRelation(relation);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Update Relation
     *
     * @param relation
     * @param auth
     * @return
     * @throws FamilyAccessDeniedException
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResponseEntity<List<RelationDTO>> updateRelation(@RequestBody RelationDTO relation, Authentication auth) throws FamilyAccessDeniedException {
        if (!isOwnerOf(relation, auth)) throw new FamilyAccessDeniedException();
        List<RelationDTO> list = familyFacade.updateRelation(relation);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    /**
     * Delete Relation
     *
     * @param relation
     * @param auth
     * @return
     * @throws FamilyAccessDeniedException
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<List<RelationDTO>> deleteRelation(@RequestBody RelationDTO relation, Authentication auth) throws FamilyAccessDeniedException {
        if (!isOwnerOf(relation, auth)) throw new FamilyAccessDeniedException();
        List<RelationDTO> list = familyFacade.deleteRelation(relation);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    private boolean isOwnerOf(RelationDTO r, Authentication auth) {
        OwnerExtendedDTO owner = this.ownerFacade.findExtendedOwnerByLogin(auth.getName());
        return (isOwnerOf(owner, r.getFamily()));
    }


    private boolean isOwnerOf(OwnerExtendedDTO owner, FamilyDTO f) {
        return owner.getFamilyList().stream().filter(family -> Objects.equals(family.getId(), f.getId())).count() > 0;
    }
}
