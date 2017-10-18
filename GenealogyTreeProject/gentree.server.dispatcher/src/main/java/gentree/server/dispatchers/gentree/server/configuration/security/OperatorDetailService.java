package gentree.server.dispatchers.gentree.server.configuration.security;

import gentree.server.dto.OwnerDTO;
import gentree.server.facade.OwnerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Martyna SZYMKOWIAK on 17/10/2017.
 */
@Service
public class OperatorDetailService implements UserDetailsService {

    private final static String OPERATOR_NOT_FOUND = "Operator not found";

    @Autowired
    OwnerFacade facade;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        OwnerDTO userAuth = facade.findOwnerByLoginToAuthProcess(s);

        if (userAuth == null) {
            throw new UsernameNotFoundException(OPERATOR_NOT_FOUND);
        } else {
            return new User(
                    userAuth.getLogin(),
                    userAuth.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    AuthorityUtils.createAuthorityList(userAuth.getRole().toString()));

        }
    }
}
