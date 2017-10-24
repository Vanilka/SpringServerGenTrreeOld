package gentree.server.dispatchers;

import gentree.exception.FamilyAccessDeniedException;
import gentree.server.dto.*;
import gentree.server.facade.FamilyFacade;
import gentree.server.facade.OwnerFacade;
import gentree.server.service.wrappers.NewMemberWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

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
    public ResponseEntity<NewMemberDTO> addFamily(@RequestBody MemberDTO m, Authentication auth)
            throws FamilyAccessDeniedException {
        OwnerExtendedDTO owner = this.ownerFacade.findExtendedOwnerByLogin(auth.getName());

        System.out.println("Sended DTO"+m);

        if (!isOwnerOf(owner, m.getFamily())) throw new FamilyAccessDeniedException();
      //  m.getFamily().setOwner(owner);
        NewMemberDTO dto = facade.addNewMember(m);

        return new ResponseEntity<NewMemberDTO>(dto, HttpStatus.OK);
    }

    private boolean isOwnerOf(OwnerExtendedDTO owner, FamilyDTO f) {
        return owner.getFamilyList().stream().filter(family -> Objects.equals(family.getId(), f.getId())).count() > 0;
    }
}
