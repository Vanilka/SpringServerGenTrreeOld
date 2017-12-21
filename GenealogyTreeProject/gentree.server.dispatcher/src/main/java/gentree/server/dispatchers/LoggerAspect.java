package gentree.server.dispatchers;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * Created by vanilka on 18/12/2017.
 */
@Aspect
@Component
@Log4j2
public class LoggerAspect {

    @PostConstruct
    public void hallow() {
        log.info("Starting logging aspect");
    }

    @Pointcut("execution(* gentree.server.dispatchers.advice..*(..))")
    private void adviceFunction() { }

    @Pointcut("execution(* gentree.server.service..*(..))")
    private void servicePointcut() {}


    @AfterReturning(pointcut = "adviceFunction()", returning = "result")
    public void loggerAdviceFunction(JoinPoint joinPoint, Object result) throws Throwable{
        log.info("LOG ADVICE : " + result);
    }

}
