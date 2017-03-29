package com.genealogytree.service.implementation;

import com.genealogytree.ExceptionManager.exception.NotUniqueUserLoginException;
import com.genealogytree.persist.entity.modules.administration.UserEntity;
import com.genealogytree.repository.UserRepository;
import com.genealogytree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@ComponentScan("com.genealogytree")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    /*
        PERSIST
     */
    public UserEntity addUser(UserEntity user) throws NotUniqueUserLoginException {
        if (exist(user.getLogin())) {
            throw new NotUniqueUserLoginException();
        }
        return this.repository.save(user);

    }

    /*
        GET
     */

    public UserEntity findUser(UserEntity user) {
        // TODO Auto-generated method stub
        return null;
    }

    public UserEntity findUser(String login, String pass) {
        return this.repository.findByLoginAndPassword(login, pass);

    }

    public List<UserEntity> getAllUsers() {

        return repository.findAll();
    }

    public UserEntity findUser(Long id) {

        return this.repository.findOne(id);
    }

    public List<UserEntity> findAllUsersByName(String sample) {
        // TODO Auto-generated method stub
        return null;
    }

    public UserEntity findUser(String login) {
        // TODO Auto-generated method stub
        return repository.findByLogin(login);
    }


    public boolean exist(String login) {
        return this.repository.exist(login);
    }

}
