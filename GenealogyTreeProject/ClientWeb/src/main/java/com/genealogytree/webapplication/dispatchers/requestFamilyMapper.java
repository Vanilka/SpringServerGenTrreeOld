package com.genealogytree.webapplication.dispatchers;

import com.genealogytree.ExceptionManager.exception.*;
import com.genealogytree.repository.entity.modules.administration.GT_User;
import com.genealogytree.repository.entity.modules.tree.GT_Family;
import com.genealogytree.service.FamilyService;
import com.genealogytree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ComponentScan("com.genealogytree")
@RequestMapping("/project")
public class requestFamilyMapper {

    @Autowired
    FamilyService familyService;

    @Autowired
    UserService userService;




    /*
    *  GET FAMILLIES
    */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<GT_Family>> getFamily(Authentication auth) throws NotFoundUserException {

        GT_User u = this.userService.findUserByLogin(auth.getName());
        List<GT_Family> list = this.familyService.getFamillies(u);
        return new ResponseEntity<List<GT_Family>>(list, HttpStatus.OK);
    }

    /*
    *  ADD NEW FAMILY
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<GT_Family> addFamily(@RequestBody GT_Family temp, Authentication auth) throws NotFoundUserException, UserInstanceWithoutIdException {

        GT_User u = this.userService.findUserByLogin(auth.getName());
        temp.setOwner(u);
        GT_Family family = this.familyService.addFamily(temp);

        return new ResponseEntity<GT_Family>(family, HttpStatus.OK);
    }

    /*
    *  UPDATE EXISTING FAMILY
    */

    @RequestMapping(value ="update", method = RequestMethod.PUT)
    public ResponseEntity<GT_Family> updateFamily(@RequestBody  GT_Family temp, Authentication auth) throws NoVersionFieldInEntity, NotFoundFamilyException, FamilyAccessViolation {
        if(temp.getVersion() == null) {
            throw new NoVersionFieldInEntity();
        }

        GT_User u = this.userService.findUserByLogin(auth.getName());
        GT_Family family = this.familyService.getFamily(temp.getId());

        if(family.getOwner().getId() != u.getId()) {
            throw new FamilyAccessViolation();
        } else {
            temp.setOwner(u);
            temp = this.familyService.updateFamily(temp);
        }

        return new ResponseEntity<GT_Family>(temp, HttpStatus.OK);
    }

    /*
    *   GET FAMILY
     */

    @RequestMapping(value="get/{idFamily}", method = RequestMethod.GET)
    public ResponseEntity<GT_Family> getFamily(@RequestParam("idFamily") Long id, Authentication auth) throws FamilyAccessViolation, NotFoundFamilyException {
        GT_Family family = this.familyService.getFamily(id);
        GT_User user = this.userService.findUserByLogin(auth.getName());
        if(family.getOwner().getId() != user.getId()) {
            throw new FamilyAccessViolation();
        }
        return new ResponseEntity<GT_Family>(family, HttpStatus.OK);

    }


}
