package genealogytree.webapplication.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import genealogytree.ExceptionManager.config.Causes;
import genealogytree.ExceptionManager.exception.ExceptionBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class GT_AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException, ServletException {
        //Authentication failed, send error response.
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = response.getWriter();
        writer.println(mapper.writeValueAsString(new ExceptionBean(Causes.UNAUTHORIZED)));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("GenealogyTree");
        super.afterPropertiesSet();
    }
}



