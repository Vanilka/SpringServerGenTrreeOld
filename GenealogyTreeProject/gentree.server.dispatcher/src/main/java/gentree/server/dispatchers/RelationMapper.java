package gentree.server.dispatchers;

import gentree.exception.FamilyAccessDeniedException;
import gentree.server.domain.entity.RelationEntity;
import gentree.server.dto.MemberDTO;
import gentree.server.dto.OwnerExtendedDTO;
import gentree.server.facade.FamilyFacade;
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

/**
 * Created by Martyna SZYMKOWIAK on 17/10/2017.
 */
@RestController
@RequestMapping("/relations")
public class RelationMapper {

    @Autowired
    FamilyFacade familyFacade;

    @Autowired
    RelationRepository repository;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<RelationEntity>> getRelations() {

        List<RelationEntity> list = repository.findAll();

        return new ResponseEntity<List<RelationEntity>>(list, HttpStatus.OK);
    }
}
