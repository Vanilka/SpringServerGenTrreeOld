package com.genealogytree.webapplication.configuration;

import com.genealogytree.persist.entity.modules.administration.UserEntity;
import com.genealogytree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GT_UserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity userAuth = userService.findUser(login);

        if (userAuth == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        } else {
            return new User(userAuth.getLogin(), userAuth.getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER"));
        }

    }

}
