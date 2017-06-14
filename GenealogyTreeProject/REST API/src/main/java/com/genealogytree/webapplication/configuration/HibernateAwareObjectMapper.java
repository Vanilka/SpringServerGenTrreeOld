package genealogytree.webapplication.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

/**
 * Created by vanilka on 22/11/2016.
 */
public class HibernateAwareObjectMapper extends ObjectMapper {
    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate5Module());
    }
}
