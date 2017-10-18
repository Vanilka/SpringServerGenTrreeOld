package gentree.server.dispatchers.gentree.server.configuration.security.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import gentree.exception.ExceptionBean;
import gentree.exception.configuration.ExceptionCauses;
import gentree.server.configuration.properties.SecurityPathProperties;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Martyna SZYMKOWIAK on 17/10/2017.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.addHeader(SecurityPathProperties.HEADER_NAME_REALM,
                SecurityPathProperties.HEADER_VALUE_REALM + SecurityPathProperties.REALM);
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = httpServletResponse.getWriter();
        writer.println(mapper.writeValueAsString(new ExceptionBean(ExceptionCauses.NOT_FOUND_USER)));
    }
}
