package mobile.client.gentree.gentreemobile.rest.tasks;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import gentree.exception.ExceptionBean;
import gentree.server.dto.OwnerDTO;
import gentree.server.dto.OwnerExtendedDTO;
import mobile.client.gentree.gentreemobile.configuration.utilities.BundleParams;
import mobile.client.gentree.gentreemobile.configuration.utilities.ServerUrl;
import mobile.client.gentree.gentreemobile.rest.responses.ExceptionResponse;
import mobile.client.gentree.gentreemobile.rest.responses.ServerResponse;
import mobile.client.gentree.gentreemobile.rest.responses.UserResponse;
import mobile.client.gentree.gentreemobile.screen.ProjectListActivity;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.net.URI;

/**
 * Created by vanilka on 13/12/2017.
 */
public class LoginTask extends RestTask {


    private View view;

    private String login;
    private String password;

    public LoginTask(View view, String login, String password) {
        this.login = login;
        this.password = password;
        this.view = view;
        this.context = view.getContext();

    }

    @Override
    protected ServerResponse doInBackground(Void... voids) {
        try {
            doPost(generateHttpEntity(login, password));

        } catch (HttpClientErrorException e) {
            System.out.println("error message : " + e.getMessage());
            serverResponse = new ExceptionResponse();
        } catch (Exception e) {
            System.out.println(e.getClass());
            Log.e("MainActivity", e.getMessage(), e);
            serverResponse = new ExceptionResponse();
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
        System.out.println("do post");
        ResponseEntity<String> entity = restTemplate.exchange(ServerUrl.URL_LOGIN, HttpMethod.POST, httpEntity, String.class);

        System.out.println("Repsonse recieved");
        if (entity.getStatusCode() == HttpStatus.OK) {
            OwnerExtendedDTO ownerDTO = objectMapper.readValue(entity.getBody(), OwnerExtendedDTO.class);
            ownerDTO.setPassword(password);
            serverResponse = new UserResponse(ownerDTO);
        } else {
            ExceptionBean exceptionBean = objectMapper.readValue(entity.getBody(), ExceptionBean.class);
            serverResponse = new ExceptionResponse(exceptionBean);
        }
    }

}
