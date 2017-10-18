package gentree.server.dispatchers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 */
@RestController
public class RootRequestMapper {


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<String> getStartPage() {

        return new ResponseEntity<String>("Hello World", HttpStatus.OK);
    }




}
