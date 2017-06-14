package genealogytree.webapplication.dispatchers.advice;

import genealogytree.ExceptionManager.exception.*;
import genealogytree.webapplication.dispatchers.requestFamilyMapper;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by vanilka on 09/11/2016.
 */

@ControllerAdvice(assignableTypes = {requestFamilyMapper.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FamilyAdvice {

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<ExceptionBean> handleError(NotFoundUserException e) {
        ExceptionBean exception = new ExceptionBean(e.getCausesInstance());
        return new ResponseEntity<ExceptionBean>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(UserInstanceWithoutIdException.class)
    public ResponseEntity<ExceptionBean> handleError(UserInstanceWithoutIdException e) {
        ExceptionBean exception = new ExceptionBean(e.getCausesInstance());
        return new ResponseEntity<ExceptionBean>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundFamilyException.class)
    public ResponseEntity<ExceptionBean> handleError(NotFoundFamilyException e) {
        ExceptionBean exception = new ExceptionBean(e.getCausesInstance());
        return new ResponseEntity<ExceptionBean>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FamilyAccessViolation.class)
    public ResponseEntity<ExceptionBean> handleError(FamilyAccessViolation e) {
        ExceptionBean exception = new ExceptionBean(e.getCausesInstance());
        return new ResponseEntity<ExceptionBean>(exception, HttpStatus.UNAUTHORIZED);
    }


}

