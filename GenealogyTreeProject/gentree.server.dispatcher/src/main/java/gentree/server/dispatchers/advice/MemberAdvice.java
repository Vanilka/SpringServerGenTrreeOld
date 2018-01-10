package gentree.server.dispatchers.advice;

import gentree.exception.ExceptionBean;
import gentree.exception.FamilyAccessDeniedException;
import gentree.server.dispatchers.MemberMapper;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Martyna SZYMKOWIAK on 24/10/2017.
 */
@ControllerAdvice(assignableTypes = {MemberMapper.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MemberAdvice {

    @ExceptionHandler(FamilyAccessDeniedException.class)
    public ResponseEntity<ExceptionBean> handleError(FamilyAccessDeniedException e) {
        ExceptionBean exception = new ExceptionBean(e.getCausesInstance());
        return new ResponseEntity<ExceptionBean>(exception, HttpStatus.UNAUTHORIZED);
    }
}
