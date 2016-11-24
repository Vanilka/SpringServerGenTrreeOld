package com.genealogytree.service.implementation;

import com.genealogytree.ExceptionManager.exception.NotFoundUserException;
import com.genealogytree.ExceptionManager.exception.NotUniqueUserLoginException;
import com.genealogytree.ExceptionManager.exception.UserOrPasswordIncorrectException;
import com.genealogytree.repository.UserRepository;
import com.genealogytree.repository.entity.modules.administration.GT_User;
import com.genealogytree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@ComponentScan("com.genealogytree")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    public GT_User addUser(GT_User user) throws  NotUniqueUserLoginException {
        if ( exist(user.getLogin())) {
            throw new NotUniqueUserLoginException();
        }
        return this.repository.save(user);

    }

    public GT_User getUser(GT_User user) {
        // TODO Auto-generated method stub
        return null;
    }

    public GT_User getUser(String login, String pass) throws UserOrPasswordIncorrectException {

        GT_User user = this.repository.findByLoginAndPassword(login, pass);

        if(user == null) {
            throw new UserOrPasswordIncorrectException();
        }
        return user;

    }

    public List<GT_User> getAllUsers() {

        List<GT_User> list = repository.findAll();

        return list;
    }

    public GT_User findUserById(Long id) throws  NotFoundUserException{

        GT_User user = this.repository.findOne(id);

        if (user == null) {
            throw new NotFoundUserException();
        }

        return  user;
    }

    public List<GT_User> findAllUsersByName(String sample) {
        // TODO Auto-generated method stub
        return null;
    }

    public GT_User findUserByLogin(String login) {
        // TODO Auto-generated method stub
        GT_User user = repository.findByLogin(login);

        return user;
    }


    public boolean exist(String login) {
        return this.repository.exist(login);
    }

}
