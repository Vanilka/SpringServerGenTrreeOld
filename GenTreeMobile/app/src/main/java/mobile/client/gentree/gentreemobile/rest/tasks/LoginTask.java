package mobile.client.gentree.gentreemobile.rest.tasks;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import com.fasterxml.jackson.databind.ObjectMapper;
import gentree.exception.ExceptionBean;
import gentree.server.dto.OwnerDTO;
import mobile.client.gentree.gentreemobile.configuration.GenTreeRestTemplate;
import mobile.client.gentree.gentreemobile.configuration.utilities.ServerUrl;
import mobile.client.gentree.gentreemobile.rest.responses.ExceptionResponse;
import mobile.client.gentree.gentreemobile.rest.responses.ServerResponse;
import mobile.client.gentree.gentreemobile.rest.responses.UserResponse;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

/**
 * Created by vanilka on 13/12/2017.
 */
public class LoginTask extends AsyncTask<String, Integer, ServerResponse> {

    private ServerResponse serverResponse;
    private View view;

    private ObjectMapper objectMapper = new ObjectMapper();
    private RestTemplate restTemplate = new GenTreeRestTemplate();

    public LoginTask(View view) {
        this.view = view;

    }


    @Override
    protected ServerResponse doInBackground(String... strings) {
        try {
            doPost(generateHttpEntity(strings[0], strings[1]));

        } catch (HttpClientErrorException e) {
            System.out.println("error message : " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getClass());
            Log.e("MainActivity", e.getMessage(), e);
        }
        return serverResponse;
    }


    @Override
    protected void onPostExecute(ServerResponse serverResponse) {
        super.onPostExecute(serverResponse);

        System.out.println("Server Response is : " + serverResponse);
        if (serverResponse.getStatus() == ServerResponse.ResponseStatus.OK) {
            Snackbar.make(view, "Login OK", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(view, "Login Not possible", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }


    /**
     * Post action to REST API
     *
     * @param httpEntity
     * @throws IOException
     */
    private void doPost(HttpEntity httpEntity) throws IOException {
        ResponseEntity<String> entity = restTemplate.exchange(ServerUrl.URL_LOGIN, HttpMethod.POST, httpEntity, String.class);

        System.out.println("Status code is ? " + entity.getStatusCode());
        if (entity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Will be correct ");
            OwnerDTO ownerDTO = objectMapper.readValue(entity.getBody(), OwnerDTO.class);
            serverResponse = new UserResponse(ownerDTO);
        } else {
            System.out.println("will by error ");
            ExceptionBean exceptionBean = objectMapper.readValue(entity.getBody(), ExceptionBean.class);
            serverResponse = new ExceptionResponse(exceptionBean);
        }
    }


    /**
     * Function generating http entity to send via request
     *
     * @param login
     * @param password
     * @return
     */
    private HttpEntity generateHttpEntity(String login, String password) {
        HttpAuthentication authHeader = new HttpBasicAuthentication(login, password);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAuthorization(authHeader);
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<?> httpEntity = new HttpEntity<Object>(null, requestHeaders);
        return httpEntity;
    }


}
