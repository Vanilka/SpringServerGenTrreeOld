package genealogytree.service;

import genealogytree.ExceptionManager.exception.NotUniqueUserLoginException;
import genealogytree.persist.entity.modules.administration.UserEntity;

import java.util.List;

public interface UserService {


    /*
        GET
     */
    UserEntity findUser(UserEntity user);

    UserEntity findUser(Long id);

    UserEntity findUser(String login);

    UserEntity findUser(String login, String pass);

    List<UserEntity> findAllUsersByName(String sample);

    List<UserEntity> getAllUsers();

    boolean exist(String login);

    /*
        PERSIST
     */

    UserEntity addUser(UserEntity user) throws NotUniqueUserLoginException;


}
