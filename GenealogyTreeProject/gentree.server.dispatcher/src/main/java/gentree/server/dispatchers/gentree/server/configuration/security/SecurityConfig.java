package gentree.server.dispatchers.gentree.server.configuration.security;

import gentree.server.configuration.enums.RoleEnum;
import gentree.server.configuration.properties.SecurityPathProperties;
import gentree.server.dispatchers.gentree.server.configuration.security.advice.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * Created by Martyna SZYMKOWIAK on 17/10/2017.
 * Provide a Security Configuration
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    @Autowired
    OperatorDetailService operatorDetailService;
    @Autowired
    CustomAccessDeniedHandler accessDeniedHandler;

    /**
     * Bean responsible to encoding passwords
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(operatorDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationEntryPointConfig getBasicAuthEntryPoint() {
        return new AuthenticationEntryPointConfig();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(SecurityPathProperties.PATH_PATTERN_ROOT).permitAll()
                .antMatchers(SecurityPathProperties.PATH_PATTERN_OWNER).authenticated()
                .antMatchers(SecurityPathProperties.PATH_PATTERN_FAMILY).authenticated()
                .antMatchers(SecurityPathProperties.PATH_PATTERN_MEMBER).authenticated()
                .antMatchers(SecurityPathProperties.PATH_PATTERN_RELATION).authenticated()
                .antMatchers("/login/").authenticated()
                .and().httpBasic().realmName(SecurityPathProperties.REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, SecurityPathProperties.PATH_PATTERN_ROOT);
    }

    private String getOrAuthorityExpression(RoleEnum... roles) {
        StringBuffer sb = new StringBuffer();
        sb.append("hasAuthority('");
        sb.append(roles[0].toString());
        sb.append("')");
        for (int i = 1; i < roles.length; i++) {
            sb.append("or hasAuthority('");
            sb.append(roles[i].toString());
            sb.append("')");
        }
        return sb.toString();
    }
}
