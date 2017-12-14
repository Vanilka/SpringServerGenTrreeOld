package mobile.client.gentree.gentreemobile.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Created by vanilka on 14/12/2017.
 */
public class GenTreeResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
    //  throw  new HttpClientErrorException( HttpStatus.valueOf(response.getRawStatusCode()));
    }
}
