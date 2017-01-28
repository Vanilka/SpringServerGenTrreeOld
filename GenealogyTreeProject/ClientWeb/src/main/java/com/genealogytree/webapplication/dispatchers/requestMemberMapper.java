package com.genealogytree.webapplication.dispatchers;

import com.genealogytree.ExceptionManager.exception.IncorrectSex;
import com.genealogytree.ExceptionManager.exception.NotFoundFamilyException;
import com.genealogytree.ExceptionManager.exception.TooManyNullFields;
import com.genealogytree.domain.enums.RelationType;
import com.genealogytree.repository.entity.modules.tree.GT_Family;
import com.genealogytree.repository.entity.modules.tree.GT_Member;
import com.genealogytree.repository.entity.modules.tree.GT_Relations;
import com.genealogytree.service.FamilyService;
import com.genealogytree.service.MemberService;
import com.genealogytree.service.RelationService;
import com.genealogytree.webapplication.wrappers.GT_addMemberWrapper;
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
@ComponentScan("com.genealogytree")
@RequestMapping("/member")
public class requestMemberMapper {

    @Autowired
    MemberService memberService;

    @Autowired
    FamilyService familyService;

    @Autowired
    RelationService relationsMapper;

    /*
    * ADD NEW MEMBER
    */

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<GT_addMemberWrapper> addMember(@RequestBody GT_Member temp) throws NotFoundFamilyException, TooManyNullFields, IncorrectSex {

        GT_Family family = this.familyService.getFamily(temp.getOwnerF().getId());
        temp.setOwnerF(family);
        GT_Member member = this.memberService.addMember(temp);

        System.out.print("proba relation");

        GT_Relations relation = null;
        try {
            relation = this.relationsMapper.addRelation(new GT_Relations(null, null, member, family, RelationType.NEUTRAL, true));
        } catch (Exception e) {
            throw e;
        }

        return new ResponseEntity<GT_addMemberWrapper>(new GT_addMemberWrapper(member, relation), HttpStatus.OK);

    }


    /*
    *  GET MEMBER LIST
     */
    @RequestMapping(value = "/list/{familyID}", method = RequestMethod.GET)
    public ResponseEntity<List<GT_Member>> getAllMembers(@PathVariable("familyID") Long id) throws NotFoundFamilyException {

        GT_Family family = this.familyService.getFamily(id);
        List<GT_Member> list = this.memberService.getMembers(family);

        return new ResponseEntity<List<GT_Member>>(list, HttpStatus.OK);
    }

}
