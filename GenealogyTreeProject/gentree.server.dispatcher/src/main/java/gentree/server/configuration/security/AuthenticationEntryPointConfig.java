package gentree.server.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import gentree.exception.ExceptionBean;
import gentree.server.configuration.properties.SecurityPathProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Martyna SZYMKOWIAK on 17/10/2017.
 */
@Configuration
@CrossOrigin(origins = "*")
public class AuthenticationEntryPointConfig extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader(SecurityPathProperties.HEADER_NAME_REALM, SecurityPathProperties.HEADER_VALUE_REALM + getRealmName());
        authException.printStackTrace();
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = response.getWriter();
        writer.println(mapper.writeValueAsString(new ExceptionBean()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName(SecurityPathProperties.REALM);
        super.afterPropertiesSet();
    }
}
