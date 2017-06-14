package genealogytree.webapplication.dispatchers;

import genealogytree.ExceptionManager.exception.*;
import genealogytree.domain.dto.FamilyDTO;
import genealogytree.domain.dto.RelationDTO;
import genealogytree.persist.entity.modules.tree.FamilyEntity;
import genealogytree.server.facade.ProjectFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vanilka on 30/12/2016.
 */
@RestController
@ComponentScan("genealogytree")
@RequestMapping("/relations")
public class requestRelationsMapper {

    @Autowired
    ProjectFacade facade;

    /*
    * ADD NEW Relation
    */

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<List<RelationDTO>> addRelation(@RequestBody RelationDTO temp) throws NotFoundFamilyException, TooManyNullFields, IncorrectSex, IntegrationViolation, IncorrectStatus, NoValidMembers {

        List<RelationDTO> result =  this.facade.addRelation(temp);

        return new ResponseEntity<List<RelationDTO>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/list/{familyID}", method = RequestMethod.GET)
    public ResponseEntity<List<RelationDTO>> getAllRelations(@PathVariable("familyID") Long id) throws NotFoundFamilyException {

        List<RelationDTO> list = this.facade.getRelations(id);
        return new ResponseEntity<List<RelationDTO>>(list, HttpStatus.OK);
    }


}
