package genealogytree.server.facade;

import genealogytree.ExceptionManager.exception.NotUniqueUserLoginException;
import genealogytree.ExceptionManager.exception.UserOrPasswordIncorrectException;
import genealogytree.domain.dto.UserDTO;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/03/2017.
 */
public interface UserFacade {

    /*
        GET
     */
    UserDTO findUser(String login, String password)  throws UserOrPasswordIncorrectException;

    UserDTO findUser(String login);

    List<UserDTO> getAll();

    /*
        PERSIST
     */
    UserDTO addUser(UserDTO user) throws NotUniqueUserLoginException;

}
