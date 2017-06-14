package genealogytree.webapplication.dispatchers.advice;

import genealogytree.ExceptionManager.config.Causes;
import genealogytree.ExceptionManager.exception.ExceptionBean;
import genealogytree.ExceptionManager.exception.TooManyNullFields;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by vanilka on 21/11/2016.
 */

@ControllerAdvice(assignableTypes = {})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MemberAdvice {

    @ExceptionHandler(TooManyNullFields.class)
    public ResponseEntity<ExceptionBean> handleTooManyNullFields(TooManyNullFields e) {
        ExceptionBean exception = new ExceptionBean(Causes.TOO_MANY_NULL_FIELDS);
        return new ResponseEntity<ExceptionBean>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
