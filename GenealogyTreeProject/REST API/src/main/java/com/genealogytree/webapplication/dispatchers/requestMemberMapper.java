package genealogytree.webapplication.dispatchers;

import genealogytree.ExceptionManager.exception.*;
import genealogytree.domain.dto.FamilyDTO;
import genealogytree.domain.dto.MemberDTO;
import genealogytree.domain.wrappers.CreatedMemberWrapper;
import genealogytree.server.facade.ProjectFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vanilka on 23/11/2016.
 */
@RestController
@ComponentScan("genealogytree")
@RequestMapping("/member")
public class requestMemberMapper {

    @Autowired
    ProjectFacade facade;


    /*
    * ADD NEW MEMBER
    */

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CreatedMemberWrapper> addMember(@RequestBody MemberDTO temp)
            throws NotFoundFamilyException, TooManyNullFields, IncorrectSex, IntegrationViolation, IncorrectStatus, NoValidMembers {

        CreatedMemberWrapper result = this.facade.addMember(temp);

        return new ResponseEntity<CreatedMemberWrapper>(result, HttpStatus.OK);

    }


    /*
    *  GET MEMBER LIST
     */
    @RequestMapping(value = "/list/{familyID}", method = RequestMethod.GET)
    public ResponseEntity<List<MemberDTO>> getAllMembers(@PathVariable("familyID") Long id) throws NotFoundFamilyException {

        FamilyDTO family = this.facade.getFamily(id);
        List<MemberDTO> list = this.facade.getMembers(family);

        return new ResponseEntity<List<MemberDTO>>(list, HttpStatus.OK);
    }

}
