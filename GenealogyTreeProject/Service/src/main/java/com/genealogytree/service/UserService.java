package com.genealogytree.service;

import com.genealogytree.ExceptionManager.exception.NotFoundUserException;
import com.genealogytree.ExceptionManager.exception.NotUniqueUserLoginException;
import com.genealogytree.ExceptionManager.exception.UserOrPasswordIncorrectException;
import com.genealogytree.repository.entity.modules.administration.GT_User;

import java.util.List;

public interface UserService {

    public GT_User addUser(GT_User user) throws NotUniqueUserLoginException;

    public GT_User getUser(GT_User user);

    public GT_User findUserById(Long id) throws  NotFoundUserException;

    public GT_User findUserByLogin(String login);

    public List<GT_User> findAllUsersByName(String sample);

    public List<GT_User> getAllUsers();

    public boolean exist(String login);

    public GT_User getUser(String login, String pass) throws UserOrPasswordIncorrectException;

}
