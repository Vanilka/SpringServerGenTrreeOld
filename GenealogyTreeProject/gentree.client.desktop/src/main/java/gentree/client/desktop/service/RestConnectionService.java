package gentree.client.desktop.service;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import gentree.exception.configuration.ExceptionCauses;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 22/10/2017.
 */
public class RestConnectionService {

    public static final RestConnectionService INSTANCE = new RestConnectionService();

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String AUTHORIZATION_METHOD = "Basic ";

    private static WebTarget webTarget;
    private static Client client;
    private static List<Object> providers;

    private RestConnectionService() {
        client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        client.property(
                ClientProperties.CONNECT_TIMEOUT,
                5000);
        client.property(
                ClientProperties.READ_TIMEOUT,
                5000);
       // webTarget = client.target(buildUrl());
    }


    public boolean testConnection(String URL) {
        boolean result = true;
        WebTarget testWebtarget = client.target(URL);
        try {
            Response response = testWebtarget.request(MediaType.APPLICATION_JSON).get();

            if (response.getStatus() != 200) {
                result = false;
            }
        } catch (Exception e) {
            result = false;
        }

        return  result;
    }

}
