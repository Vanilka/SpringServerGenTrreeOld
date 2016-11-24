package com.genealogytree.webapplication.dispatchers.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Created by vanilka on 21/11/2016.
 */

@ControllerAdvice(assignableTypes = {})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MemberAdvice {

}
