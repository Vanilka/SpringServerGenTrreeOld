package mobile.client.gentree.gentreemobile.configuration;

import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }

    public GenTreeRestTemplate() {
            this.getMessageConverters().add(new StringHttpMessageConverter());
            this.setErrorHandler(new GenTreeResponseErrorHandler());
            this.setRequestFactory(getClientHttpRequestFactory());

    }
}
