package com.genealogytree.webapplication.dispatchers;

import com.genealogytree.ExceptionManager.exception.NotFoundFamilyException;
import com.genealogytree.ExceptionManager.exception.NotFoundUserException;
import com.genealogytree.ExceptionManager.exception.UserInstanceWithoutIdException;
import com.genealogytree.repository.entity.modules.administration.GT_User;
import com.genealogytree.repository.entity.modules.tree.GT_Family;
import com.genealogytree.service.FamilyService;
import com.genealogytree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    *   GET FAMILY
     */

    @RequestMapping(value="get", method = RequestMethod.GET)
    public ResponseEntity<GT_Family> getFamily() throws NotFoundFamilyException {
        GT_Family family = null;
        return new ResponseEntity<GT_Family>(family, HttpStatus.OK);

    }
}
