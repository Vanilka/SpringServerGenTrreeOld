package gentree.server.dispatchers;

import gentree.exception.FamilyAccessDeniedException;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.MemberDTO;
import gentree.server.dto.NewMemberDTO;
import gentree.server.dto.OwnerExtendedDTO;
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


    /**
     * Additing Member
     * @param m
     * @param auth
     * @return
     * @throws FamilyAccessDeniedException
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity<NewMemberDTO> addFamily(@RequestBody MemberDTO m, Authentication auth)
            throws FamilyAccessDeniedException {

        if (!isOwnerOf(m, auth)) throw new FamilyAccessDeniedException();
        NewMemberDTO dto = facade.addNewMember(m);
        return new ResponseEntity<NewMemberDTO>(dto, HttpStatus.OK);
    }

    /**
     * Delete Member
     * @param m
     * @param auth
     * @return
     * @throws FamilyAccessDeniedException
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    private ResponseEntity<FamilyDTO> deleteMember(@RequestBody MemberDTO m, Authentication auth) throws FamilyAccessDeniedException {

        if (!isOwnerOf(m, auth)) throw new FamilyAccessDeniedException();
        FamilyDTO dto = facade.deleteMember(m);

        return new ResponseEntity<FamilyDTO>(dto, HttpStatus.OK);
    }


    private boolean isOwnerOf(MemberDTO m, Authentication auth) {
        OwnerExtendedDTO owner = this.ownerFacade.findExtendedOwnerByLogin(auth.getName());
        return (isOwnerOf(owner, m.getFamily()));
    }


    private boolean isOwnerOf(OwnerExtendedDTO owner, FamilyDTO f) {
        return owner.getFamilyList().stream().filter(family -> Objects.equals(family.getId(), f.getId())).count() > 0;
    }
}
