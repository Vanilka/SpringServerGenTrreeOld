package genealogytree.webapplication.dispatchers;

import genealogytree.ExceptionManager.exception.*;
import genealogytree.domain.dto.FamilyDTO;
import genealogytree.domain.dto.UserDTO;
import genealogytree.server.facade.ProjectFacade;
import genealogytree.server.facade.UserFacade;
import genealogytree.service.FamilyService;
import genealogytree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ComponentScan("genealogytree")
@RequestMapping("/project")
public class requestFamilyMapper {

    @Autowired
    FamilyService familyService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectFacade facade;

    @Autowired
    UserFacade userFacade;

    /*
    *  GET FAMILLIES
    */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<FamilyDTO>> getFamily(Authentication auth) throws NotFoundUserException {

        UserDTO u = this.userFacade.findUser(auth.getName());
        List<FamilyDTO> list = this.facade.getFamillies(u);
        return new ResponseEntity<List<FamilyDTO>>(list, HttpStatus.OK);
    }

    /*
    *  ADD NEW FAMILY
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<FamilyDTO> addFamily(@RequestBody FamilyDTO temp, Authentication auth) throws NotFoundUserException, UserInstanceWithoutIdException {
        UserDTO u = this.userFacade.findUser(auth.getName());
        temp.setOwner(u);
        FamilyDTO family = this.facade.addFamily(temp);
        return new ResponseEntity<FamilyDTO>(family, HttpStatus.OK);
    }

    /*
    *  UPDATE EXISTING FAMILY
    */

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResponseEntity<FamilyDTO> updateFamily(@RequestBody FamilyDTO temp, Authentication auth) throws NoVersionFieldInEntity, NotFoundFamilyException, FamilyAccessViolation {
        if (temp.getVersion() == null) {
            throw new NoVersionFieldInEntity();
        }

        UserDTO u = this.userFacade.findUser(auth.getName());
        FamilyDTO family = this.facade.getFamily(temp.getId());

        if (family.getOwner().getId() != u.getId()) {
            throw new FamilyAccessViolation();
        } else {
            temp.setOwner(u);
            temp = this.facade.updateFamily(temp);
        }

        return new ResponseEntity<FamilyDTO>(temp, HttpStatus.OK);
    }

    /*
    *   GET FAMILY
     */

    @RequestMapping(value = "get/{idFamily}", method = RequestMethod.GET)
    public ResponseEntity<FamilyDTO> getFamily(@RequestParam("idFamily") Long id, Authentication auth) throws FamilyAccessViolation, NotFoundFamilyException {
        FamilyDTO family = this.facade.getFamily(id);
        UserDTO u = this.userFacade.findUser(auth.getName());
        if (family.getOwner().getId() != u.getId()) {
            throw new FamilyAccessViolation();
        }
        return new ResponseEntity<FamilyDTO>(family, HttpStatus.OK);

    }


}
