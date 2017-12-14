package mobile.client.gentree.gentreemobile.configuration;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;

/**
 * Created by vanilka on 14/12/2017.
 */
public class GenTreeRestTemplate extends RestTemplate {

    public GenTreeRestTemplate() {
            super();
         //   this.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            this.getMessageConverters().add(new StringHttpMessageConverter());
            this.setErrorHandler(new GenTreeResponseErrorHandler());
/*        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        //Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
        messageConverters.add(converter);
        this.setMessageConverters(messageConverters);*/
    }
}
