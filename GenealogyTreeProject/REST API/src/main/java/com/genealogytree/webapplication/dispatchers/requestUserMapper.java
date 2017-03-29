package com.genealogytree.webapplication.dispatchers;

import com.genealogytree.ExceptionManager.exception.NotUniqueUserLoginException;
import com.genealogytree.ExceptionManager.exception.UserOrPasswordIncorrectException;
import com.genealogytree.domain.dto.UserDTO;
import com.genealogytree.server.facade.UserFacade;
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
    UserFacade userFacade;

    /*
     * ADD NEW USER
     */

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO newUser) throws NotUniqueUserLoginException, Exception {
        UserDTO addesUser = this.userFacade.addUser(newUser);
        return new ResponseEntity<UserDTO>(addesUser, HttpStatus.OK);

    }

    /*
     *  LOGIN TO APPLICATION
     */

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO temp) throws UserOrPasswordIncorrectException, Exception {
        UserDTO user = this.userFacade.findUser(temp.getLogin(), temp.getPassword());
        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
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
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> list = this.userFacade.getAll();
        return new ResponseEntity<List<UserDTO>>(list, HttpStatus.OK);

    }

}
