package com.genealogytree.repository;

import com.genealogytree.persist.entity.modules.administration.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByLogin(String login);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.login = :login")
    boolean exist(@Param("login") String login);

    @Query("Select u from UserEntity u WHERE u.login= :login and u.password= :password ")
    UserEntity findByLoginAndPassword(@Param("login") String login, @Param("password") String password);

}
