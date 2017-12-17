package gentree.server.facade;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Martyna SZYMKOWIAK on 23/10/2017.
 */
@Aspect
@Component
public class ConverterValidator {

    @PostConstruct
    public void hallow() {
        System.out.println("IM WORKING !");
    }

    @Pointcut("execution(* gentree.server.facade.converter.ConverterToEntity.convert*(..))")
    private void convertionMethod() {
    }


    /**
     * Null pointer exception will be generated if Null value passed to ConverterToEntity
     *
     * @param joinPoint
     */
    @Before("convertionMethod()")
    public void checkNullParameters(JoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        if (arguments[0] == null) {
            throw new NullPointerException();
        }
    }
}
