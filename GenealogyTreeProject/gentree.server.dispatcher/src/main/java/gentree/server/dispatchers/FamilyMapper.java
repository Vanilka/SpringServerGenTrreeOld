package gentree.server.dispatchers;

import gentree.exception.FamilyAccessDeniedException;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.OwnerDTO;
import gentree.server.dto.OwnerExtendedDTO;
import gentree.server.facade.FamilyFacade;
import gentree.server.facade.OwnerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Martyna SZYMKOWIAK on 17/10/2017.
 */
@RestController
@RequestMapping("/family")
public class FamilyMapper {

    @Autowired
    FamilyFacade familyFacade;

    @Autowired
    OwnerFacade ownerFacade;

    /**
     * ADD NEW FAMILY TO USER
     *
     * @param temp
     * @param auth
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity<FamilyDTO> addFamily(@RequestBody FamilyDTO temp, Authentication auth) {
        OwnerDTO ownerDTO = ownerFacade.findOwnerByLogin(auth.getName());
        FamilyDTO family = familyFacade.addNewFamily(temp, ownerDTO);
        return new ResponseEntity<FamilyDTO>(family, HttpStatus.OK);
    }

    /**
     * RETRIEVE ALL FAMILIES OF CONNECTED USER
     *
     * @param auth
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<FamilyDTO>> findMyFamilies(Authentication auth) {

        OwnerDTO ownerDTO = ownerFacade.findOwnerByLogin(auth.getName());
        List<FamilyDTO> list = familyFacade.findAllFamiliesByOwner(ownerDTO);

        return new ResponseEntity<List<FamilyDTO>>(list, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<FamilyDTO> findFullFamily(@PathVariable Long id, Authentication auth) throws FamilyAccessDeniedException {
        OwnerExtendedDTO owner = ownerFacade.findExtendedOwnerByLogin(auth.getName());
        if (!isOwnerOf(owner, id)) throw new FamilyAccessDeniedException();

        FamilyDTO family = familyFacade.findExtendedFamilyById(id);

        return new ResponseEntity<FamilyDTO>(family, HttpStatus.OK);
    }

    /**
     * DELETE FAMILY WITH ID
     *
     * @param id
     * @param auth
     * @return
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<List<FamilyDTO>> deleteFamily(@PathVariable Long id, Authentication auth) {

        List<FamilyDTO> list = new ArrayList<>();

        return new ResponseEntity<List<FamilyDTO>>(list, HttpStatus.OK);
    }

    private boolean isOwnerOf(OwnerExtendedDTO owner, Long f_id) {
        return owner.getFamilyList().stream().filter(family -> Objects.equals(family.getId(), f_id)).count() > 0;
    }
}
