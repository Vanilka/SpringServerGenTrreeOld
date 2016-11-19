package com.genealogytree.webapplication.dispatchers;

import com.genealogytree.ExceptionManager.config.Causes;
import com.genealogytree.ExceptionManager.exception.NotUniqueUserLoginException;
import com.genealogytree.repository.entity.modules.administration.GT_User;
import com.genealogytree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ComponentScan("com.genealogytree")
@RequestMapping("/user")
public class requestUserMapper {

    @Autowired
    UserService userService;

    /*
     * ADD NEW USER
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity<GT_User> addUser(@RequestBody GT_User newUser) throws NotUniqueUserLoginException, Exception {
        if (this.userService.exist(newUser.getLogin())) {
            throw new NotUniqueUserLoginException(Causes.USER_ALREADY_EXIST.toString());
        } else {
            GT_User addesUser = this.userService.addUser(newUser);
            return new ResponseEntity<GT_User>(addesUser, HttpStatus.OK);
        }
    }

    /*
     *  LOGIN TO APPLICATION
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<GT_User> login(@RequestBody GT_User temp) throws Exception {
        GT_User user = this.userService.getUser(temp.getLogin(), temp.getPassword());
        if (user == null) {
            System.out.println("Nie udalo sie... nie ma takiego usera");
            throw new Exception("No nie... nie ma mnie");
        }
        return new ResponseEntity<GT_User>(user, HttpStatus.OK);
    }


    /*
     * UPDATE  USER
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> addUser(@RequestBody String s) {
        //TODO
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    /*
     * GET ALL USERS
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<GT_User>> getAllUsers() {
        List<GT_User> list = this.userService.getAllUsers();
        return new ResponseEntity<List<GT_User>>(list, HttpStatus.OK);

    }

}
