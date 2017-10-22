package gentree.server.dispatchers;

import gentree.exception.FamilyAccessDeniedException;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.OwnerDTO;
import gentree.server.dto.RelationDTO;
import gentree.server.facade.FamilyFacade;
import gentree.server.facade.OwnerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Martyna SZYMKOWIAK on 17/10/2017.
 */
@RestController
@RequestMapping("/member")
public class MemberMapper {

    @Autowired
    OwnerFacade ownerFacade;

    @Autowired
    FamilyFacade facade;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity<FamilyDTO> addFamily(@RequestBody RelationDTO r, @RequestBody Long f_id, Authentication auth)
    throws  FamilyAccessDeniedException {
        OwnerDTO owner = this.ownerFacade.findOwnerByLogin(auth.getName());

        if(!isOwnerOf(owner, f_id)) throw new FamilyAccessDeniedException();

        FamilyDTO family = null;
        return new ResponseEntity<FamilyDTO>(family, HttpStatus.OK);
    }

        private boolean isOwnerOf(OwnerDTO owner, Long f_id) {
      return  owner.getFamilyList().stream().filter(family -> family.getId() == f_id).count() > 0;
    }
}
