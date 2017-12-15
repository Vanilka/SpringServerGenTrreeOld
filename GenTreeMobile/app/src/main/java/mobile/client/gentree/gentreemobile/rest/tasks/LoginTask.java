package mobile.client.gentree.gentreemobile.rest.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import com.fasterxml.jackson.databind.ObjectMapper;
import gentree.exception.ExceptionBean;
import gentree.server.dto.OwnerDTO;
import gentree.server.dto.OwnerExtendedDTO;
import mobile.client.gentree.gentreemobile.configuration.GenTreeRestTemplate;
import mobile.client.gentree.gentreemobile.configuration.utilities.BundleParams;
import mobile.client.gentree.gentreemobile.configuration.utilities.ServerUrl;
import mobile.client.gentree.gentreemobile.rest.responses.ExceptionResponse;
import mobile.client.gentree.gentreemobile.rest.responses.ServerResponse;
import mobile.client.gentree.gentreemobile.rest.responses.UserResponse;
import mobile.client.gentree.gentreemobile.screen.ProjectListActivity;
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
    private Context context;
    private View view;

    private String login;
    private String password;

    private ObjectMapper objectMapper = new ObjectMapper();
    private RestTemplate restTemplate = new GenTreeRestTemplate();

    public LoginTask(View view) {
        this.view = view;
        this.context = view.getContext();

    }


    @Override
    protected ServerResponse doInBackground(String... strings) {
        try {
            login = strings[0];
            password = strings[1];
            doPost(generateHttpEntity(login, password));

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


        if (serverResponse.getStatus() == ServerResponse.ResponseStatus.OK) {
            Snackbar.make(view, "Login OK", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            openProjectListActivity(((UserResponse) serverResponse).getOwner());

        } else {
            Snackbar.make(view, "Login Not possible", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void openProjectListActivity(OwnerDTO currentOwner) {
        try {
            String userJson = objectMapper.writeValueAsString(currentOwner);
            Intent intent = new Intent(context, ProjectListActivity.class);
            intent.putExtra(BundleParams.CURRENT_USER, userJson);
            context.startActivity(intent);
        } catch (Exception e) {
            Snackbar.make(view, "conversion error", Snackbar.LENGTH_LONG)
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

        if (entity.getStatusCode() == HttpStatus.OK) {
            OwnerExtendedDTO ownerDTO = objectMapper.readValue(entity.getBody(), OwnerExtendedDTO.class);
            ownerDTO.setPassword(password);
            serverResponse = new UserResponse(ownerDTO);
        } else {
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
