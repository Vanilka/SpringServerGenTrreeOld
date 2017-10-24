package gentree.server.dispatchers;

import gentree.server.dto.OwnerDTO;
import gentree.server.facade.OwnerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Martyna SZYMKOWIAK on 17/10/2017.
 */
@RestController
@RequestMapping("owner")
public class OwnerManagementMapper {

    @Autowired
    OwnerFacade ownerFacade;

    /**
     *  Authenticate into application
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<OwnerDTO> login(Authentication auth) {

        OwnerDTO user = this.ownerFacade.findOwnerByLogin(auth.getName());

        return new ResponseEntity<OwnerDTO>( user, HttpStatus.OK);
    }

}
