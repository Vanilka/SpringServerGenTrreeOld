package com.genealogytree.repository;

import com.genealogytree.repository.entity.modules.administration.GT_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<GT_User, Long> {
    public GT_User findByLogin(String login);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM GT_User u WHERE u.login = :login")
    public boolean exist(@Param("login") String login);

    @Query("Select u from GT_User u WHERE u.login= :login and u.password= :password ")
    public GT_User findByLoginAndPassword(@Param("login") String login, @Param("password") String password);

}
