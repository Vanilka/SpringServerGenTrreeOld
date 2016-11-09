package com.genealogytree.webapplication.dispatchers;

import java.util.List;

import com.genealogytree.ExceptionManager.config.Causes;
import com.genealogytree.ExceptionManager.exception.NotFoundUserException;
import com.genealogytree.ExceptionManager.exception.UserInstanceWithoutIdException;
import com.genealogytree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.genealogytree.repository.entity.modules.administration.GT_User;
import com.genealogytree.repository.entity.modules.tree.GT_Family;
import com.genealogytree.service.FamilyService;

@RestController
@ComponentScan("com.genealogytree")
@RequestMapping("/project")
public class requestFamilyMapper {
	
	@Autowired
	FamilyService familyService;

	@Autowired
	UserService userService;

	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<GT_Family>> getAllUsers() {
		List<GT_Family> list = this.familyService.getAllFamilies();
		return new ResponseEntity<List<GT_Family>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<GT_Family> addFamily(@RequestBody GT_Family temp) throws NotFoundUserException, UserInstanceWithoutIdException{
		if(temp.getOwner().getId() == null) {
			throw  new UserInstanceWithoutIdException(Causes.USER_INISTANCE_WITHOUT_ID.toString());
		}

		GT_User tempUser = this.userService.findUserById(temp.getOwner().getId());
		if(tempUser != null){
			temp.setOwner(tempUser);
		} else {
			throw new NotFoundUserException(Causes.NOT_FOUND_USER.toString());
		}
		GT_Family family = this.familyService.addFamily(temp);

		return new ResponseEntity<GT_Family>(family, HttpStatus.OK);
	}
}
