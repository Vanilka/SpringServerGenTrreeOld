package com.genealogytree.webapplication.dispatchers;

import com.genealogytree.ExceptionManager.exception.IncorrectSex;
import com.genealogytree.ExceptionManager.exception.NotFoundFamilyException;
import com.genealogytree.ExceptionManager.exception.TooManyNullFields;
import com.genealogytree.repository.entity.modules.tree.GT_Family;
import com.genealogytree.repository.entity.modules.tree.GT_Member;
import com.genealogytree.repository.entity.modules.tree.GT_Relations;
import com.genealogytree.service.FamilyService;
import com.genealogytree.service.RelationService;
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
@ComponentScan("com.genealogytree")
@RequestMapping("/relations")
public class requestRelationsMapper {

    @Autowired
    FamilyService familyService;

    @Autowired
    RelationService relationService;

    /*
    * ADD NEW Relation
    */

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<GT_Relations> addRelation(@RequestBody GT_Relations temp) throws NotFoundFamilyException, TooManyNullFields, IncorrectSex {

        GT_Family family = this.familyService.getFamily(temp.getOwnerF().getId());
        temp.setOwnerF(family);
        GT_Relations relation = this.relationService.addRelation(temp);

        return new ResponseEntity<GT_Relations>(relation, HttpStatus.OK);
    }

    @RequestMapping(value = "/list/{familyID}", method = RequestMethod.GET)
    public ResponseEntity<List<GT_Relations>> getAllRelations(@PathVariable("familyID") Long id) throws NotFoundFamilyException {

        GT_Family family = this.familyService.getFamily(id);
        List<GT_Relations> list = this.relationService.getRelations(family);
        return  new ResponseEntity<List<GT_Relations>>(list, HttpStatus.OK);
    }


}
