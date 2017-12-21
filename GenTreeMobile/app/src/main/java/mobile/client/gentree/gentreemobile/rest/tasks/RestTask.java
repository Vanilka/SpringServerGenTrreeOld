package mobile.client.gentree.gentreemobile.rest.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.fasterxml.jackson.databind.ObjectMapper;
import mobile.client.gentree.gentreemobile.configuration.GenTreeRestTemplate;
import mobile.client.gentree.gentreemobile.rest.responses.ServerResponse;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by vanilka on 21/12/2017.
 */
public abstract class RestTask extends AsyncTask<Void, Integer, ServerResponse> {

    protected Context context;
    protected ServerResponse serverResponse;
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected RestTemplate restTemplate = new GenTreeRestTemplate();
    /**
     * Function generating http entity to send via request
     *
     * @param login
     * @param password
     * @return
     */
    protected  HttpEntity generateHttpEntity(String login, String password) {
        HttpHeaders requestHeaders = generateHeaders(login, password);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(null, requestHeaders);
        return httpEntity;
    }


    /**
     * Function generating Http headers
     * @param login
     * @param password
     * @return
     */
    protected static HttpHeaders generateHeaders(String login, String password) {
        HttpAuthentication authHeader = new HttpBasicAuthentication(login, password);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAuthorization(authHeader);
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return requestHeaders;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
